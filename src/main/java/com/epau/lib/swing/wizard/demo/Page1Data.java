package com.epau.lib.swing.wizard.demo;

import com.epau.lib.swing.wizard.page.PageData;
import com.epau.lib.swing.wizard.page.PageDataPool;
import com.epau.lib.validation.Severity;
import com.epau.lib.validation.ValidationResults;

class Page1Data extends PageData {

	private static final String KEY_VALUE_1     = "demo.value.one";
	private static final String KEY_VALUE_2     = "demo.value.two";
	private static final String DEFAULT_VALUE_1 = "one";
	private static final String DEFAULT_VALUE_2 = "two";

	private String value1 = DEFAULT_VALUE_1;
	private String value2 = DEFAULT_VALUE_2;

	Page1Data(PageDataPool pageDataPool) {
		super(pageDataPool);
	}

	@Override
	public void load() {
		value1 = preferences.get(KEY_VALUE_1, DEFAULT_VALUE_1);
		value2 = preferences.get(KEY_VALUE_2, DEFAULT_VALUE_2);
	}

	@Override
	public void save() {
		preferences.put(KEY_VALUE_1, value1);
		preferences.put(KEY_VALUE_2, value2);
	}

	@Override
	public ValidationResults validate() {
		var results = new ValidationResults();
		if (value2.equals(DEFAULT_VALUE_2)) {
			results.add("There is an error", "Errors prevent the wizard from proceeding. Change value 2 to something else.", Severity.ERROR);
		}
		return results;
	}

	@Override
	public void loadDefaults() {
		value1 = DEFAULT_VALUE_1;
		value2 = DEFAULT_VALUE_2;
		preferences.remove(KEY_VALUE_1);
		preferences.remove(KEY_VALUE_2);
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
}
