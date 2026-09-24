package com.epau.lib.swing.wizard.page;

public abstract class NoOpPage extends Page {

	public NoOpPage(PageData pageData) {
		super(pageData);
	}

	@Override
	public void build() {}

	@Override
	protected void addListeners() {}

	@Override
	protected void removeListeners() {}

	@Override
	protected void fillGUI() {}

	@Override
	protected void updatePageData() {}

	@Override
	public void updateGUI() {}

	@Override
	public void updateDependantValues() {}
}
