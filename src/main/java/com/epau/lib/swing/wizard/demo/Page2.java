package com.epau.lib.swing.wizard.demo;

import com.epau.lib.swing.wizard.page.Page;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.LINE_START;
import static java.awt.GridBagConstraints.NONE;


class Page2 extends Page {

	private final JRadioButton      option1Button = new JRadioButton();
	private final JRadioButton      option2Button = new JRadioButton();
	private final JRadioButton      option3Button = new JRadioButton();
	private final JCheckBox         selectedBox   = new JCheckBox("Selected");
	private final JComboBox<String> optionBox     = new JComboBox<>();
	private final ButtonGroup       radioBtnGroup = new ButtonGroup();
	private final Page2Data         pageData;

	private final ActionListener listener = _ -> pageChanged();

	Page2(Page2Data pageData) {
		super(pageData);
		this.pageData = pageData;
	}

	@Override
	public void build() {
		var options = pageData.getOptions();
		option1Button.setText(options.get(0));
		option2Button.setText(options.get(1));
		option3Button.setText(options.get(2));
		options.forEach(optionBox::addItem);

		radioBtnGroup.add(option1Button);
		radioBtnGroup.add(option2Button);
		radioBtnGroup.add(option3Button);

		content.add(option1Button,  new GridBagConstraints(0, 0, 1, 1, 0.5, 0.0, LINE_START, NONE, new Insets(10, 10, 10, 10), 0, 0));
		content.add(selectedBox, 	new GridBagConstraints(1, 0, 1, 1, 0.5, 0.0, LINE_START, NONE, new Insets(10, 10, 10, 10), 0, 0));
		content.add(option2Button,  new GridBagConstraints(0, 1, 1, 1, 0.5, 0.0, LINE_START, NONE, new Insets(10, 10, 10, 10), 0, 0));
		content.add(optionBox, 		new GridBagConstraints(1, 1, 1, 1, 0.5, 0.0, LINE_START, HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		content.add(option3Button,  new GridBagConstraints(0, 2, 1, 1, 0.5, 0.0, LINE_START, NONE, new Insets(10, 10, 10, 10), 0, 0));
		content.add(new JPanel(), 	new GridBagConstraints(1, 2, 1, 1, 0.5, 0.0, LINE_START, NONE, new Insets(10, 10, 10, 10), 0, 0));
		content.add(new JPanel(), 	new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0, LINE_START, BOTH, new Insets(10, 10, 10, 10), 0, 0));
	}

	@Override
	protected void addListeners() {
		option1Button.addActionListener(listener);
		option2Button.addActionListener(listener);
		option3Button.addActionListener(listener);
		selectedBox.addActionListener(listener);
		optionBox.addActionListener(listener);
	}

	@Override
	protected void removeListeners() {
		option1Button.removeActionListener(listener);
		option2Button.removeActionListener(listener);
		option3Button.removeActionListener(listener);
		selectedBox.removeActionListener(listener);
		optionBox.removeActionListener(listener);

	}

	@Override
	public void fillGUI() {
		option1Button.setSelected(option1Button.getText().equals(pageData.getSelectedRadioOption()));
		option2Button.setSelected(option2Button.getText().equals(pageData.getSelectedRadioOption()));
		option3Button.setSelected(option3Button.getText().equals(pageData.getSelectedRadioOption()));
		selectedBox.setSelected(pageData.isButtonSelected());
		optionBox.setSelectedItem(pageData.getSelectedComboOption());
	}

	@Override
	public void updatePageData() {
		for (var buttons = radioBtnGroup.getElements(); buttons.hasMoreElements(); ) {
			var button = buttons.nextElement();
			if (button.isSelected()) {
				pageData.setSelectedRadioOption(button.getText());
			}
		}
		pageData.setButtonSelected(selectedBox.isSelected());
		pageData.setSelectedComboOption((String) optionBox.getSelectedItem());
	}

	@Override
	public void updateGUI() {
		fillGUI();
	}

	@Override
	public void updateDependantValues() {}

	@Override
	public String getTitle() {
		return "Page 2";
	}

	@Override
	public String getDescription() {
		return "Common Swing components.";
	}
}
