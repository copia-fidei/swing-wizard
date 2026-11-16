package com.epau.lib.swing.wizard.page;

import com.epau.util.nls.Nls;
import com.epau.util.swing.frame.TestFrames;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.Box.createHorizontalStrut;
import static javax.swing.BoxLayout.LINE_AXIS;

public class ButtonBar extends JPanel {

	/// names to identify components
	public interface Name {
		String CANCEL = "button.cancel";
		String DEFAULTS = "button.defaults";
		String BACK     = "button.back";
		String NEXT = "button.next";
		String APPLY = "button.apply";
	}

	private static final Nls nls = new Nls(ButtonBar.class);

	/// button titles
	public interface Title {
		String CANCEL   = nls.get("ButtonBar.Cancel");
		String DEFAULTS = nls.get("ButtonBar.Defaults");
		String BACK     = "←";
		String NEXT     = "→";
		String APPLY    = nls.get("ButtonBar.Apply");
	}


	private final JButton cancelButton   = new JButton(Title.CANCEL);
	private final JButton defaultsButton = new JButton(Title.DEFAULTS);
	private final JButton backButton     = new JButton(Title.BACK);
	private final JButton nextButton     = new JButton(Title.NEXT);
	private final JButton applyButton    = new JButton(Title.APPLY);

	public ButtonBar() {
		setLayout(new BoxLayout(this, LINE_AXIS));

		cancelButton.setName(Name.CANCEL);
		defaultsButton.setName(Name.DEFAULTS);
		backButton.setName(Name.BACK);
		nextButton.setName(Name.NEXT);
		applyButton.setName(Name.APPLY);

		add(cancelButton);
		add(createHorizontalStrut(10));
		add(defaultsButton);
		add(createHorizontalGlue());
		add(backButton);
		add(createHorizontalStrut(10));
		add(nextButton);
		add(createHorizontalStrut(10));
		add(applyButton);
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getDefaultsButton() {
		return defaultsButton;
	}

	public JButton getBackButton() {
		return backButton;
	}

	public JButton getNextButton() {
		return nextButton;
	}

	public JButton getApplyButton() {
		return applyButton;
	}

	// Demo
	void main() {
		TestFrames.showComponent("Button Bar", new ButtonBar()); //$NON-NLS
	}
}
