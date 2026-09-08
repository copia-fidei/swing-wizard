package com.epau.installer.apply;

import com.epau.installer.Icons;
import com.epau.utilities.nls.Nls;
import com.epau.utilities.swing.operation.Operation;
import com.epau.utilities.swing.operation.OperationAdapter;
import com.epau.utilities.swing.operation.OperationListener;
import com.epau.utilities.swing.operation.OperationStatus;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;


import static com.epau.utilities.swing.operation.OperationStatusPresentation.getDescription;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.SwingUtilities.windowForComponent;
import static javax.swing.UIManager.getIcon;

public class ProgressSegment extends JPanel {

	private final Nls                 nls           = new Nls(this);
	private final Operation           operation;
	private final JButton             cancelButton  = new SVGButton(Icons.CANCEL_ICON_PATH, 20);
	private final ProgressBarWithIcon progressBar   = new ProgressBarWithIcon();
	private final JButton             detailsButton = new JButton("...");

	public ProgressSegment(Operation operation) {
		this.operation = operation;

		buildGUI();
		addListeners();
		updateGUI();
	}

	private void buildGUI() {
		setLayout(new BorderLayout(5, 0));
		cancelButton.setToolTipText(nls.get("ProgressSegment.tooltip.Cancel_{0}", operation.getTitle()));

		progressBar.setStringPainted(true);

		add(cancelButton, BorderLayout.WEST);
		add(progressBar, BorderLayout.CENTER);
		add(detailsButton, BorderLayout.EAST);
	}

	private void addListeners() {
		cancelButton.addActionListener(_ -> {
			if (showConfirmDialog(
					windowForComponent(this),
					nls.get("ProgressSegment.description.Should_this_operation_really_be_cancelled?"),
					nls.get("ProgressSegment.title.Should_this_operations_really_be_cancelled?"),
					YES_NO_OPTION) == YES_OPTION
			)
			{
				operation.cancel(true);
			}
		});
		detailsButton.addActionListener(_ -> new DetailsDialog(windowForComponent(this), operation).setVisible(true));

		operation.addListener(new OperationAdapter() {
			@Override
			public void statusChanged(OperationStatus status) {
				updateGUI();
			}

			@Override
			public void progressChanged(int progress) {
				updateGUI();
			}
		});
	}
	private void updateGUI() {
		OperationStatus status   = operation.getStatus();
		int             progress = operation.getProgress();
		progressBar.setValue(progress);
		progressBar.setIcon(getIcon(status));
		progressBar.setString(operation.getTitle() + " – " + getDescription(status) + " (" + progress + "%)");
		cancelButton.setEnabled(status == OperationStatus.RUNNING);
	}

}
