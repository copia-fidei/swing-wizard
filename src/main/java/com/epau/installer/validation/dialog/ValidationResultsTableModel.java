package com.epau.installer.validation.dialog;

import com.epau.installer.validation.ValidationResult;
import com.epau.installer.validation.ValidationResults;
import com.epau.utilities.nls.Nls;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

class ValidationResultsTableModel extends AbstractTableModel {

	private final Nls nls = new Nls(this);

	private final List<ValidationResult> sortedResults = new ArrayList<>();

	public ValidationResultsTableModel(ValidationResults validationResults) {
		this.sortedResults.addAll(validationResults.list());
		this.sortedResults.sort(new ValidationResultComparator());
	}

	@Override
	public int getRowCount() {
		return sortedResults.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int column) {
		return switch (column) {
			case 0 -> nls.get("ValidationResultsTableModel.column.Description");
			case 1 -> nls.get("ValidationResultsTableModel.column.Level");
			default -> "";
		};
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < sortedResults.size()) {
			var validationResult = sortedResults.get(rowIndex);
			return switch (columnIndex) {
				case 0 -> validationResult.severity().label();
				case 1 -> validationResult.description();
				default -> "";
			};
		}
		return "";
	}
}
