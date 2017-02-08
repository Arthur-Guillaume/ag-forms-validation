package com.arthurguillaume.android.agformsvalidation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is representing a form. It is handy to manage form validation.
 * Created by Arthur on 23/11/2015.
 */
public class Form extends LinearLayout {

    private List<IValidatedInput> inputs;

    /**
     * Constructor called by xml parser when a form is declared into laayout file.
     *
     * @param context the context of the activity linked to the view
     * @param attrs   attributes passed with the tag in xml file
     */
    public Form(Context context, AttributeSet attrs) {
        super(context, attrs);
        inputs = new LinkedList<>();
    }

    /**
     * Gets children of a view group. Returns all IValidatedInput elements nested in view group.
     *
     * @param vg the ViewGroup to analyse recursively.
     * @return a list containing all the validatedInput elements
     */
    public List<IValidatedInput> getChildren(ViewGroup vg) {
        List<IValidatedInput> list = new LinkedList<>();
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof IValidatedInput) {
                list.add((IValidatedInput) v);
            } else if (v instanceof ViewGroup) {
                list.addAll(getChildren((ViewGroup) v));
            }
            if (v instanceof CheckBoxGroup) {
                ((CheckBoxGroup) v).init();
            }
        }
        return list;
    }

    /**
     * Call this method to launch the analyse of the nested tags ans register for validation.
     */
    public void init() {
        inputs = getChildren(this);
    }

    /**
     * Getter for the registered inputs
     *
     * @return a list of all the validated elements inside the form.
     */
    public List<IValidatedInput> getInputs() {
        return inputs;
    }

    /**
     * Get the input at index.
     *
     * @param index the index of the input.
     * @return The input element at the given index in the form.
     */
    public IValidatedInput getInput(int index) {
        return inputs.get(index);
    }

    /**
     * Validates a single input field of the form
     *
     * @param index the index of the input to validate
     * @return a boolean indicating if the input is valid or not
     */
    public boolean isInputValid(int index) {
        return getInput(index).isValid();
    }

    /**
     * Validates the complete form.
     *
     * @param context          The current context
     * @param showErrorMessage pass yes to display a popup with an error message when validation fails
     * @param layoutResId      pass a layout resource id to use custom layout
     * @param titleResId       a resource id to a textview used to display the title of the alert
     * @param msgResId         a resource id to a textview used to display the message of the alert
     * @return the validation state of the form. True if validation succeeded and false otherwise.
     */
    public boolean isFormValid(Context context, boolean showErrorMessage, int layoutResId, int titleResId, int msgResId) {
        for (IValidatedInput input : inputs) {
            if (!input.isValid()) {
                if (showErrorMessage) {
                    if (layoutResId != NO_ID) {
                        new AlertBox(context, layoutResId, titleResId, "Erreur", msgResId, input.getErrorMessage()).show();
                    } else {
                        new AlertBox(context, "Erreur", input.getErrorMessage()).show();
                    }
                }
                return false;
            }
        }
        return true;
    }
}