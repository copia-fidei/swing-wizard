package com.epau.lib.swing.wizard.page;

import com.epau.lib.validation.ValidationResults;
import org.jetbrains.annotations.NonNls;

import java.util.logging.Logger;
import java.util.prefs.Preferences;

import static java.util.logging.Logger.getLogger;
import static java.util.prefs.Preferences.userNodeForPackage;

/// Stores the data for a page.
///
/// Implementations can:
/// 1. Use {@link Preferences} for persistent storage, with preference keys and
///    default values defined as class members.
/// 2. Provides getters and setters for each data item.
public abstract class PageData {

	protected final @NonNls Logger log = getLogger(getClass().getName());

    protected Preferences preferences = userNodeForPackage(this.getClass());

	/// obtain the other page data objects from here
	/// you don't have to pass it into the constructor if you do not need it
    protected PageDataPool pageDataPool;

    public PageData(PageDataPool pageDataPool) {
        this.pageDataPool = pageDataPool;
	}

	public PageData() {
		this.pageDataPool = null;
	}


    public abstract void load();

	public abstract void save();

	public abstract ValidationResults validate();

    public abstract void loadDefaults();
}
