package com.epau.installer.apply;

import com.epau.utilities.swing.operation.examples.WaitOperation;
import com.epau.utilities.swing.frame.TestFrames;
import org.jetbrains.annotations.NonNls;


@NonNls
class DetailsDialogDemo {

	private final static String TITLE = "Details Dialog";

	static void main() {
		TestFrames.showDialog(TITLE, frame -> new DetailsDialog(frame, new WaitOperation(10)));
	}
}
