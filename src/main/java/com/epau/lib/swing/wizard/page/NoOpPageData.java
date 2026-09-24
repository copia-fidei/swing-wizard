package com.epau.lib.swing.wizard.page;

import com.epau.lib.validation.ValidationResults;

public class NoOpPageData extends PageData {

	public NoOpPageData(PageDataPool pageDataPool) {
		super(pageDataPool);
	}

	@Override
	public void load() {}

	@Override
	public void save() {}

	@Override
	public ValidationResults validate() {
		return new ValidationResults();
	}

	@Override
	public void loadDefaults() {}
}