package com.arthurguillaume.android.agformsvalidation;

/**
 * Any input that can be validated.
 * Created by Arthur on 23/11/2015.
 */
public interface IValidatedInput {

    /**
     * Checks if the field is passing its own validation.
     *
     * @return a boolean representing the validation state of the field.
     */
    boolean isValid();

    /**
     * The error message to display if the field is not valid
     *
     * @return a string containing the error message for this field.
     */
    String getErrorMessage();
}
