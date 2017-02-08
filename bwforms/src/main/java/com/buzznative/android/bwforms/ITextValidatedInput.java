package com.buzznative.android.bwforms;

/**
 * Used to represent a input text which can be validated using a regex
 * Created by Arthur on 23/11/2015.
 */
public interface ITextValidatedInput extends IValidatedInput {
    /**
     * Getter for the validation regex
     *
     * @return a regex that can be used to validate the field.
     */
    String getValidationRegEx();
}
