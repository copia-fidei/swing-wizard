package com.epau.lib.swing.wizard.demo;

import com.epau.lib.swing.wizard.page.NoOpPageData;
import com.epau.lib.swing.wizard.page.PageDataPool;

import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
class ReviewPageData extends NoOpPageData {

	ReviewPageData(PageDataPool pageDataPool) {
		super(pageDataPool);
	}

	public String getValue1() {
		return getPage1Data().get().getValue1();
	}

	public String getValue2() {
		return getPage1Data().get().getValue2();
	}

	public String getSelectedRadioOption() {
		return getPage2Data().get().getSelectedRadioOption();
	}

	public String getSelectedComboOption() {
		return getPage2Data().get().getSelectedComboOption();
	}

	public boolean isButtonSelected() {
		return getPage2Data().get().isButtonSelected();
	}

	private Optional<Page1Data> getPage1Data() {
		return pageDataPool.getPageData(Page1Data.class);
	}

	private Optional<Page2Data> getPage2Data() {
		return pageDataPool.getPageData(Page2Data.class);
	}
}
