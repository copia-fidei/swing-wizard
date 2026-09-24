package com.epau.lib.swing.wizard.demo;

import com.epau.lib.swing.wizard.page.PageFrame;
import com.epau.lib.swing.wizard.page.PagePool;
import com.epau.util.swing.operation.Operation;
import com.epau.util.swing.operation.examples.WaitOperation;
import org.jetbrains.annotations.NonNls;

import java.awt.Dimension;
import java.util.List;
import java.util.function.Supplier;

import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

@NonNls
class DemoWizard {

	static void main() {
		invokeLater(() -> {
			var frame = new PageFrame("Demo Wizard");
			var pool  = new PagePool(frame) {

				@Override
				protected void addPages() {
					var page1Data      = new Page1Data(pageDataPool);
					var page2Data      = new Page2Data(pageDataPool);
					var reviewPageData = new ReviewPageData(pageDataPool);

					pageDataPool.add(page1Data);
					pageDataPool.add(page2Data);
					pageDataPool.add(reviewPageData);

					pages.add(new Page1(page1Data));
					pages.add(new Page2(page2Data));
					pages.add(new ReviewPage(reviewPageData));
				}

				@Override
				protected List<Supplier<Operation>> getOperations() {
					return List.of(
							() -> new WaitOperation("Short running task 1", 1, false),
							() -> new WaitOperation("Long running task 2 (will fail)", "Run 2 seconds, then throw an exception", 4, true),
							() -> new WaitOperation("Long running task 3", 4, false));
				}
			};
			pool.init();
			pool.showFirstPage();
			frame.pack();
			frame.setMinimumSize(new Dimension(720, 460));
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}
