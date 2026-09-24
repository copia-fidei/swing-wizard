package com.epau.lib.swing.wizard.page;

import com.epau.util.swing.evt.Listeners;
import com.epau.util.swing.toast.StatusBar;
import com.epau.util.swing.toast.Toast;
import com.epau.lib.validation.ValidationResults;
import com.epau.lib.validation.dialog.ValidationResultsDialog;
import org.jetbrains.annotations.NonNls;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.plaf.LayerUI;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.epau.lib.validation.Severity.ERROR;
import static com.epau.lib.validation.Severity.INFO;
import static com.epau.lib.validation.Severity.WARNING;
import static java.awt.AWTEvent.MOUSE_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK;
import static java.awt.Cursor.HAND_CURSOR;
import static java.awt.Cursor.getDefaultCursor;
import static java.awt.event.MouseEvent.MOUSE_CLICKED;
import static java.util.logging.Logger.getLogger;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.SwingUtilities.windowForComponent;


/// Displays the data from PageData and writes changes inside the GUI back to it.
///
/// Correct implementation (otherwise bugs appear)
/// 1. Implement all methods as documented.
/// 2. Do not add listeners to components that update other components.
/// Instead, do this:
/// Add listeners to components that only call pageChanged(), by implementing addListeners() and removeListeners().
/// Query the state of each components in updateGUI() and updateDependantValues() and update the GUI accordingly.
@NonNls
public abstract class Page {

	protected final Logger log = getLogger(getClass().getName());

	protected final PageData pageData;
	/// the panel to add all the GUI components to
	protected final JPanel   content = new JPanel(new GridBagLayout());

	private final StatusBar      statusBar      = new StatusBar();
	private final JLayer<JPanel> statusBarLayer = new JLayer<>(content, new ToastLayerUI());

	private ValidationResults                    latestValidationResults = new ValidationResults();
	private SwingWorker<ValidationResults, Void> validator               = new Validator();

	public Page(PageData pageData) {
		this.pageData = pageData;
		statusBar.toast().setVisible(false);
	}

	/// Called when the state of a GUI component changes.
	/// How to use:
	/// Add to each component a listener that calls this method (use addListeners and removeListeners)
	protected final void pageChanged() {
		removeListeners();
		updatePageData();
		validate();
		invokeLater(() -> {
			updateGUI();
			updateDependantValues();
			addListeners();
		});
	}

	private void validate() {
		fireValidationStarted();
		if (!validator.isDone()) {
			validator.cancel(true);
		}
		validator = new Validator();
		validator.execute();
	}

	protected void fireValidationStarted() {
		validationListeners.fire(listener -> listener.validationStarted(this));
	}

	private void fireValidationFinished() {
		validationListeners.fire(listener -> listener.validationFinished(Page.this, latestValidationResults));
	}


	/// Called when this page is about to become visible
	public void willBecomeVisible() {
		pageData.load();
		fillGUI();
		addListeners();
		validate();
	}

	/// Called when this page is about to become invisible
	public void willBecomeInvisible() {
		removeListeners();
		pageData.save();
	}

	/// Each GUI component gets restored to its default value
	public void restoreDefaults() {
		removeListeners();
		pageData.loadDefaults();
		fillGUI();
		validate();
		addListeners();
	}

	/// Build the GUI, but do not fill it with data from PageData.
	/// Components should be added to the *content* panel.
	public abstract void build();

	/// Fill the GUI components with values from PageData.
	protected abstract void fillGUI();

	/// Write the current data inside the GUI to PageData.
	protected abstract void updatePageData();

	/// Update the GUI.
	public abstract void updateGUI();

	/// Update the values inside GUI components that depend on the values of other components.
	public abstract void updateDependantValues();

	/// Add listeners for all components on this page.
	protected abstract void addListeners();

	/// Remove listeners from all components on this page.
	/// Not implementing this correctly will lead to endless event loops.
	protected abstract void removeListeners();

	public abstract String getTitle();

	public abstract String getDescription();

	/// @return the panel with all GUI components
	public JComponent getContent() {
		return statusBarLayer;
	}

	private final Listeners<ValidationListener> validationListeners = Listeners.of();

	public void addValidationListener(ValidationListener listener) {
		validationListeners.add(listener);
	}

	public void removeValidationListener(ValidationListener listener) {
		validationListeners.remove(listener);
	}

	@SuppressWarnings("rawtypes")
	class ToastLayerUI extends LayerUI<JPanel> {

		private static final int GAP_BETWEEN_TOAST_AND_BOTTOM = 20;

		private boolean mouseIsInsideToast = false;

		@Override
		protected void processMouseEvent(MouseEvent e, JLayer<? extends JPanel> l) {
			if (!mouseIsInsideToast) {
				return;
			}
			if (e.getID() == MOUSE_CLICKED) {
				new ValidationResultsDialog(windowForComponent(statusBarLayer), latestValidationResults).setVisible(true);
			}
		}

		@Override
		protected void processMouseMotionEvent(MouseEvent e, JLayer<? extends JPanel> layer) {
			if (latestValidationResults.list().isEmpty()) {
				mouseIsInsideToast = false;
				return;
			}
			boolean mouseIsCurrentlyInsideToast = statusBar.toast().getBounds().contains(e.getPoint());
			if (mouseIsCurrentlyInsideToast != mouseIsInsideToast) {
				mouseIsInsideToast = mouseIsCurrentlyInsideToast;
				layer.setCursor(mouseIsCurrentlyInsideToast ? new Cursor(HAND_CURSOR) : getDefaultCursor());
			}
		}

		@Override
		public void paint(Graphics g, JComponent c) {
			super.paint(g, c);

			Toast toast = statusBar.toast();
			if (!toast.isVisible()) {
				return;
			}

			var g2   = (Graphics2D) g.create();
			var size = toast.getPreferredSize();
			int x    = (c.getWidth() - size.width) / 2;
			int y    = c.getHeight() - size.height - GAP_BETWEEN_TOAST_AND_BOTTOM;

			toast.setBounds(x, y, size.width, size.height);
			g2.translate(x, y);
			toast.doLayout();
			toast.print(g2);

			g2.dispose();
		}

		@Override
		public void installUI(JComponent c) {
			super.installUI(c);
			var layer = (JLayer) c;
			layer.setLayerEventMask(MOUSE_EVENT_MASK | MOUSE_MOTION_EVENT_MASK);
		}

		@Override
		public void uninstallUI(JComponent c) {
			var layer = (JLayer) c;
			layer.setLayerEventMask(0);
			super.uninstallUI(c);
		}
	}

	@Override
	public String toString() {
		return getTitle();
	}

	@NonNls
	private class Validator extends SwingWorker<ValidationResults, Void> {

		@Override
		protected ValidationResults doInBackground() {
			return pageData.validate();
		}

		@Override
		protected void done() {
			if (isCancelled()) {
				return;
			}
			try {
				latestValidationResults = get();
				if (latestValidationResults.contains(ERROR)) {
					statusBar.displayError(latestValidationResults.getMostImportant(ERROR));
				} else if (latestValidationResults.contains(WARNING)) {
					statusBar.displayWarning(latestValidationResults.getFirst(WARNING));
				} else if (latestValidationResults.contains(INFO)) {
					statusBar.displayInfo(latestValidationResults.getFirst(INFO));
				} else {
					statusBar.toast().setVisible(false);
				}
				statusBarLayer.repaint();

				fireValidationFinished();
			} catch (InterruptedException e) {
				log.log(Level.INFO, "Validation interrupted", e);
			} catch (ExecutionException e) {
				log.log(Level.SEVERE, "Validation failed", e);
			}
		}
	}
}
