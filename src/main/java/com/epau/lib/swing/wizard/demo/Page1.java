package com.epau.lib.swing.wizard.demo;

import com.epau.lib.swing.wizard.page.Page;
import com.epau.lib.swing.wizard.page.PageData;
import com.epau.util.swing.text.DocumentChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.LINE_START;
import static java.awt.GridBagConstraints.NONE;

class Page1 extends Page {

	private final JTextField value1Field = new JTextField(24);
	private final JTextField value2Field = new JTextField(24);
	private final Page1Data  pageData;

	private final DocumentListener documentListener = new DocumentChangeListener(this::pageChanged);

	Page1(PageData pageData) {
		super(pageData);
		this.pageData = (Page1Data) pageData;
	}

	@Override
	public void build() {
		var config1Label = new JLabel("Config value 1:");
		var config2Label = new JLabel("Config value 2:");

		content.add(config1Label, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, LINE_START, NONE, new Insets(10, 10, 10, 10), 0, 0));
		content.add(value1Field,  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, LINE_START, HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		content.add(config2Label, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, LINE_START, NONE, new Insets(10, 10, 10, 10), 0, 0));
		content.add(value2Field,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, LINE_START, HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		content.add(new JPanel(), new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, LINE_START, BOTH, new Insets(10, 10, 10, 10), 0, 0));
	}

	@Override
	protected void addListeners() {
		value1Field.getDocument().addDocumentListener(documentListener);
		value2Field.getDocument().addDocumentListener(documentListener);
	}

	@Override
	protected void removeListeners() {
		value1Field.getDocument().removeDocumentListener(documentListener);
		value2Field.getDocument().removeDocumentListener(documentListener);
	}

	@Override
	public void fillGUI() {
		value1Field.setText(pageData.getValue1());
		value2Field.setText(pageData.getValue2());
	}

	@Override
	public void updatePageData() {
		pageData.setValue1(value1Field.getText().trim());
		pageData.setValue2(value2Field.getText().trim());
	}

	@Override
	public void updateGUI() {
		fillGUI();
	}

	@Override
	public void updateDependantValues() {
		// no-op
	}

	@Override
	public String getTitle() {
		return "Config page 1";
	}

	@Override
	public String getDescription() {
		return "On each page you can submit data.";
	}
}
