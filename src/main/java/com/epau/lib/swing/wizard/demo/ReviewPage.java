package com.epau.lib.swing.wizard.demo;

import com.epau.lib.swing.wizard.page.NoOpPage;
import com.epau.lib.swing.wizard.page.PageData;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.LINE_START;

class ReviewPage extends NoOpPage {

	private final JTextArea      summary = new JTextArea();
	private final ReviewPageData pageData;

	ReviewPage(PageData pageData) {
		super(pageData);
		this.pageData = (ReviewPageData) pageData;
	}

	@Override
	public void build() {
		summary.setEditable(false);
		summary.setLineWrap(true);
		summary.setWrapStyleWord(true);
		content.add(new JScrollPane(summary), new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, LINE_START, BOTH, new Insets(10, 10, 10, 10), 0, 0));
	}

	@Override
	public void fillGUI() {
		summary.setText("""
        Value 1: %s
        Value 2: %s
        Selected radio button option: %s
        Selected combo box option: %s
        Selected button state: %s
        """.formatted(
				pageData.getValue1(),
				pageData.getValue2(),
				pageData.getSelectedRadioOption(),
				pageData.getSelectedComboOption(),
				pageData.isButtonSelected()
		));	}

	@Override
	public void updateGUI() {
		fillGUI();
	}

	@Override
	public String getTitle() {
		return "Review";
	}

	@Override
	public String getDescription() {
		return "Review your choices.";
	}
}
