module com.epau.installer {
	requires java.desktop;
	requires java.prefs;
	requires java.logging;

	requires com.formdev.flatlaf.extras;
	requires com.github.weisj.jsvg;
	requires static org.jetbrains.annotations;
	requires org.apache.commons.compress;

	requires com.epau.utilities.swing.operation;
	requires com.epau.utilities.nls;

	opens com.epau.installer.apply to com.epau.utilities.nls;
	// TODO remove
	//opens com.epau.installer.operation to com.epau.utilities.nls;
	opens com.epau.installer.page to com.epau.utilities.nls;
	opens com.epau.installer.swing to com.epau.utilities.nls;
	opens com.epau.installer.validation to com.epau.utilities.nls;
	opens com.epau.installer.validation.dialog to com.epau.utilities.nls;

	opens com.epau.installer.icons.svgrepo;

}