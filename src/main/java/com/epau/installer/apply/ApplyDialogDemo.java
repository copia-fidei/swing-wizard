package com.epau.installer.apply;

import com.epau.installer.swing.TestFrames;
import com.epau.utilities.swing.operation.Operation;
import com.epau.utilities.swing.operation.examples.BlockingOperation;
import com.epau.utilities.swing.operation.examples.WaitOperation;
import org.jetbrains.annotations.NonNls;

import java.util.List;

@NonNls
interface ApplyDialogDemo {

	List<Operation> TEST_OPERATIONS = List.of(
			new WaitOperation("Operation 1", 3, false),
			new WaitOperation("Operation 2", 5, true),
			new WaitOperation("Operation 3", 4, false),
			new BlockingOperation("Block 1", "Block forever"),
			new BlockingOperation("Block 2", "Block forever")
	);

	static void main() {
		TestFrames.showDialog("Apply Dialog", frame -> new ApplyDialog(frame, TEST_OPERATIONS)); //NON-NLS
	}
}
