package com.epau.installer.utilities;

import static java.lang.System.lineSeparator;

/// Similar to a string builder, but each appended string is placed on a new line.
public record TextBuilder(StringBuilder builder) {

	public TextBuilder() {
		this(new StringBuilder());
	}

	public TextBuilder line(String s) {
		builder.append(s);
		builder.append(lineSeparator());
		return this;
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
