package com.epau.installer.page;

import com.epau.installer.toast.StatusBar;
import com.epau.installer.toast.Toast;
import com.epau.library.validation.ValidationResults;
import com.epau.library.validation.dialog.ValidationResultsDialog;
import org.jetbrains.annotations.NonNls;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.plaf.LayerUI;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.epau.library.validation.Severity.ERROR;
import static com.epau.library.validation.Severity.INFO;
import static com.epau.library.validation.Severity.WARNING;
import static java.awt.AWTEvent.MOUSE_EVENT_MASK;
import static java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK;
import static java.awt.Cursor.HAND_CURSOR;
import static java.awt.Cursor.getDefaultCursor;
import static java.awt.event.MouseEvent.MOUSE_CLICKED;
import static java.util.logging.Logger.getLogger;
import static javax.swing.SwingUtilities.windowForComponent;

@NonNls
public abstract class Page {

	protected final Logger log = getLogger(getClass().getName());

	protected final PageData pageData;
	protected final JPanel   content = new JPanel(new GridBagLayout());

	protected boolean isValid;

	private final StatusBar      statusBar      = new StatusBar();
	private final JLayer<JPanel> statusBarLayer = new JLayer<>(content, new ToastLayerUI());

	private ValidationResults                    latestValidationResults = new ValidationResults(List.of());
	private SwingWorker<ValidationResults, Void> validator               = new Validator();

	protected final Runnable onValidationChanged;

	public Page(PageData pageData, Runnable onValidationChanged) {
		this.pageData = pageData;
		this.onValidationChanged = onValidationChanged;

		statusBar.toast().setVisible(false);
	}

	public boolean isValid() {
		return isValid;
	}

	protected final void pageChanged() {
		removeListeners();
		updatePageData();
		validate();
		SwingUtilities.invokeLater(() -> {
			updateGUI();
			updateDependantValues();
			addListeners();
		});
	}

	private void validate() {
		isValid = false;
		if (!validator.isDone()) {
			validator.cancel(true);
		}
		validator = new Validator();
		validator.execute();
	}

	public abstract void build();

	protected abstract void addListeners();

	protected abstract void removeListeners();

	public void willBecomeVisible() {
		pageData.load();
		fillGUI();
		addListeners();
		validate();
	}

	public void willBecomeInvisible() {
		removeListeners();
		pageData.save();
	}

	public void restoreDefaults() {
		removeListeners();
		pageData.loadDefaults();
		fillGUI();
		validate();
		addListeners();
	}

	protected abstract void fillGUI();

	protected abstract void updatePageData();

	public abstract void updateGUI();

	public abstract void updateDependantValues();

	public abstract String getTitle();

	public abstract String getDescription();

	/// @return the page content
	public JComponent getContent() {
		return statusBarLayer;
	}

	@SuppressWarnings("rawtypes")
	class ToastLayerUI extends LayerUI<JPanel> {

		private static final int GAP = 20;

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
			Rectangle bounds          = statusBar.toast().getBounds();
			boolean   currentlyInside = bounds.contains(e.getPoint());
			if (currentlyInside != mouseIsInsideToast) {
				mouseIsInsideToast = currentlyInside;
				layer.setCursor(currentlyInside ? new Cursor(HAND_CURSOR) : getDefaultCursor());
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
			int y    = c.getHeight() - size.height - GAP;

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

				isValid = !latestValidationResults.contains(ERROR);
				onValidationChanged.run();
			} catch (InterruptedException e) {
				log.log(Level.WARNING, "Validation interrupted", e);
			} catch (ExecutionException e) {
				log.log(Level.SEVERE, "Validation failed", e);
			}
		}
	}
}
