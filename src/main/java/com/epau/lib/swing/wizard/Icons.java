package com.epau.lib.swing.wizard;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGIcon.ColorFilter;
import org.jetbrains.annotations.NonNls;

import java.awt.Color;

// TODO
@NonNls
public interface Icons {

	String CANCEL_ICON_PATH    = "com/epau/lib/swing/wizard/icons/svgrepo/cancel-svgrepo-com.svg";

	static FlatSVGIcon cancel(int size) {
		return newIcon(CANCEL_ICON_PATH, Color.RED, size);
	}

	private static FlatSVGIcon newIcon(String location, Color color, int size) {
		return new FlatSVGIcon(location, size, size).setColorFilter(new ColorFilter(_ -> color));
	}
}
