package com.epau.installer.validation;

import javax.print.DocFlavor.STRING;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.epau.installer.validation.Severity.ERROR;
import static java.util.Comparator.comparing;

public record ValidationResults(List<ValidationResult> list) {

	public ValidationResults() {
		this(new ArrayList<>());
	}

	public void add(ValidationResult result) {
		list.add(result);
	}

	public void addError(String title, String description, int priority) {
		this.add(new ValidationResult(title, description, ERROR, priority));
	}

	public void add(String title, String description, Severity severity) {
		this.add(new ValidationResult(title, description, severity, 0));
	}

	public boolean contains(Severity severity) {
		return list.stream().anyMatch(result -> result.severity() == severity);
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public String getFirst(Severity severity) {
		return filter(severity).findFirst().get().title();
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public String getMostImportant(Severity severity) {
		return filter(severity).max(comparing(ValidationResult::priority)).get().title();
	}

	private Stream<ValidationResult> filter(Severity severity) {
		return list.stream().filter(result -> result.severity() == severity);
	}
}
