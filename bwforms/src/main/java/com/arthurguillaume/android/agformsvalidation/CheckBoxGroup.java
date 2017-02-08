package com.arthurguillaume.android.agformsvalidation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * A group of checkboxes that can be validated.
 * Created by Arthur Guillaume on 30/11/15.
 */
public class CheckBoxGroup extends LinearLayout implements IButtonValidatedInput {

    private List<CheckBox> linkedButtons;
    private String errorMessage;
    private boolean noneSelectedAllowed;
    private boolean multipleSelectionAllowed;

    /**
     * Constructor called automatically when tag CheckBoxGroup is used in xml layout file.
     *
     * @param context the context of the activity linked to the view
     * @param attrs   attributes passed along with the xml tag
     */
    public CheckBoxGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CheckBoxGroup);
        for (int i = 0; i < attributes.getIndexCount(); i++) {
            multipleSelectionAllowed = attributes.getBoolean(R.styleable.CheckBoxGroup_isMultipleSelectionAllowed, false);
            noneSelectedAllowed = attributes.getBoolean(R.styleable.CheckBoxGroup_isEmptySelectionAllowed, true);
            String userDefinedErrorMessage = attributes.getString(R.styleable.CheckBoxGroup_errorMessage);
            if (userDefinedErrorMessage != null) {
                errorMessage = userDefinedErrorMessage;
            } else {
                errorMessage = "Sélection invalide.";
            }
            linkedButtons = new LinkedList<>();
        }
        attributes.recycle();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Retrieves all children of a view group element that are checkboxes.
     * Note that this method is recursive and will analyse nested view groups.
     *
     * @param vg the root tag to analyse.
     * @return
     */
    public List<CheckBox> getChildren(ViewGroup vg) {
        List<CheckBox> list = new LinkedList<>();
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof ViewGroup && ((ViewGroup) v).getChildCount() != 0) {
                list.addAll(getChildren((ViewGroup) v));
            } else if (v instanceof CheckBox) {
                list.add((CheckBox) v);
            }
        }
        return list;
    }

    /**
     * Search for check boxes to validate.
     */
    public void init() {
        linkedButtons = getChildren(this);
    }

    /**
     * returns the list of checkboxes managed by this group
     *
     * @return the list of all the checkboxes currently managed by this group
     */
    public List<CheckBox> getLinkedButtons() {
        if (linkedButtons == null) {
            linkedButtons = new LinkedList<>();
        }
        return linkedButtons;
    }

    /**
     * Getter for the property indicating if it is allowed to select none of the checkboxes
     *
     * @return true if no selection is acceptable, false otherwise
     */
    public boolean isNoneSelectedAllowed() {
        return noneSelectedAllowed;
    }

    /**
     * Getter for the property indicating if it is allowed to select more than one of the checkboxes
     *
     * @return true if multiple selection is acceptable, false otherwise
     */
    public boolean isMultiSelectionAllowed() {
        return multipleSelectionAllowed;
    }

    /**
     * Validates the group of checkboxes using mutipleSelection and noneSelected parameters
     *
     * @return true if checkboxes respects the constraints, false otherwise
     */
    public boolean isValid() {
        if (isNoneSelectedAllowed() && isMultiSelectionAllowed()) {
            return true;
        } else if (isNoneSelectedAllowed() && !isMultiSelectionAllowed()) {
            boolean hasOneButtonSelected = false;
            for (CheckBox button : getLinkedButtons()) {
                if (button.isChecked()) {
                    if (hasOneButtonSelected) {
                        return false;
                    }
                    hasOneButtonSelected = true;
                }
            }
            return true;
        } else if (!isNoneSelectedAllowed() && isMultiSelectionAllowed()) {
            boolean hasOneSelected = false;
            for (CheckBox button : getLinkedButtons()) {
                if (button.isChecked()) {
                    hasOneSelected = true;
                }
            }
            return hasOneSelected;
        } else {
            boolean hasOneSelected = false;
            for (CheckBox button : getLinkedButtons()) {
                if (button.isChecked() && !hasOneSelected) {
                    hasOneSelected = true;
                } else if (hasOneSelected && button.isChecked()) {
                    return false;
                }
            }
            return hasOneSelected;
        }
    }
}
