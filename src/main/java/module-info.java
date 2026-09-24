module com.epau.lib.swing.wizard {
	requires java.desktop;
	requires java.prefs;
	requires java.logging;

	requires com.formdev.flatlaf.extras;
	requires com.github.weisj.jsvg;
	requires static org.jetbrains.annotations;
	requires org.apache.commons.compress;

	requires transitive com.epau.util.swing.operation;
	requires transitive com.epau.lib.validation;
	requires com.epau.util.swing;
	requires com.epau.util.nls;

	opens com.epau.lib.swing.wizard.apply to com.epau.util.nls;
	opens com.epau.lib.swing.wizard.page to com.epau.util.nls;

	opens com.epau.lib.swing.wizard.icons.svgrepo;

	exports com.epau.lib.swing.wizard.apply;
	exports com.epau.lib.swing.wizard.page;
	exports com.epau.lib.swing.wizard;
	exports com.epau.lib.swing.wizard.demo;

}