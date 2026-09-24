package com.epau.lib.swing.wizard.page;

import com.epau.lib.validation.ValidationResults;

public interface ValidationListener {

    void validationStarted(Page page);

    void validationFinished(Page page, ValidationResults results);
}