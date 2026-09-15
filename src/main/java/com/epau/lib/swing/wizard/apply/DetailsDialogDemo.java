package com.epau.lib.swing.wizard.apply;

import com.epau.util.swing.operation.examples.WaitOperation;
import com.epau.util.swing.frame.TestFrames;
import org.jetbrains.annotations.NonNls;


@NonNls
class DetailsDialogDemo {

	private final static String TITLE = "Details Dialog";

	static void main() {
		TestFrames.showDialog(TITLE, frame -> new DetailsDialog(frame, new WaitOperation(10)));
	}
}
