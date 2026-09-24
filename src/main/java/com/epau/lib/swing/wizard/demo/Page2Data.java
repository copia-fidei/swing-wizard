package com.epau.lib.swing.wizard.demo;

import com.epau.lib.swing.wizard.page.PageData;
import com.epau.lib.swing.wizard.page.PageDataPool;
import com.epau.lib.validation.ValidationResults;

import java.util.List;

class Page2Data extends PageData {

	private static final String KEY_RADIO_BUTTON_SELECTED_OPTION = "demo.radio.button.selected.option";
	private static final String KEY_COMBOBOX_SELECTED_OPTION     = "demo.combobox.selected.option";
	private static final String KEY_BUTTON_SELECTED_STATE        = "demo.button.selected.state";

	public static final String OPTION_1 = "Option 1";
	public static final String OPTION_2 = "Option 2";

	private static final String       DEFAULT_RADIO_BUTTON_SELECTED_OPTION = OPTION_1;
	private static final String       DEFAULT_COMBOBOX_SELECTED_OPTION     = OPTION_2;
	private static final boolean      DEFAULT_BUTTON_SELECTED_STATE        = true;
	private static final List<String> OPTIONS                              = List.of(OPTION_1, OPTION_2, "Option 3");

	private String  selectedRadioOption = DEFAULT_RADIO_BUTTON_SELECTED_OPTION;
	private String  selectedComboOption = DEFAULT_COMBOBOX_SELECTED_OPTION;
	private boolean buttonSelected      = DEFAULT_BUTTON_SELECTED_STATE;

	Page2Data(PageDataPool pageDataPool) {
		super(pageDataPool);
	}

	@Override
	public void load() {
		selectedRadioOption = preferences.get(KEY_RADIO_BUTTON_SELECTED_OPTION, DEFAULT_RADIO_BUTTON_SELECTED_OPTION);
		selectedComboOption = preferences.get(KEY_COMBOBOX_SELECTED_OPTION, DEFAULT_COMBOBOX_SELECTED_OPTION);
		buttonSelected      = preferences.getBoolean(KEY_BUTTON_SELECTED_STATE, DEFAULT_BUTTON_SELECTED_STATE);
	}

	@Override
	public void save() {
		preferences.put(KEY_RADIO_BUTTON_SELECTED_OPTION, selectedRadioOption);
		preferences.put(KEY_COMBOBOX_SELECTED_OPTION, selectedComboOption);
		preferences.putBoolean(KEY_BUTTON_SELECTED_STATE, buttonSelected);
	}

	@Override
	public ValidationResults validate() {
		return new ValidationResults();
	}

	@Override
	public void loadDefaults() {
		selectedRadioOption = DEFAULT_RADIO_BUTTON_SELECTED_OPTION;
		selectedComboOption = DEFAULT_COMBOBOX_SELECTED_OPTION;
		buttonSelected      = DEFAULT_BUTTON_SELECTED_STATE;

		preferences.remove(KEY_RADIO_BUTTON_SELECTED_OPTION);
		preferences.remove(KEY_COMBOBOX_SELECTED_OPTION);
		preferences.remove(KEY_BUTTON_SELECTED_STATE);
	}

	public List<String> getOptions() {
		return OPTIONS;
	}

	public String getSelectedRadioOption() {
		return selectedRadioOption;
	}

	public void setSelectedRadioOption(String selectedRadioOption) {
		this.selectedRadioOption = selectedRadioOption;
	}

	public String getSelectedComboOption() {
		return selectedComboOption;
	}

	public void setSelectedComboOption(String selectedComboOption) {
		this.selectedComboOption = selectedComboOption;
	}

	public boolean isButtonSelected() {
		return buttonSelected;
	}

	public void setButtonSelected(boolean buttonSelected) {
		this.buttonSelected = buttonSelected;
	}
}
