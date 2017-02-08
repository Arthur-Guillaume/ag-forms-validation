package com.buzznative.android.bwforms;

import android.widget.CheckBox;

import java.util.List;

/**
 * Created by Arthur on 23/11/2015.
 */
public interface IButtonValidatedInput extends IValidatedInput {

    /**
     * Getter for the buttons linked into the validation group
     *
     * @return A list of the buttons linked into the group
     */
    List<CheckBox> getLinkedButtons();

    /**
     * Getter for the property indicating if it is allowed to select none of the checkboxes
     *
     * @return true if no selection is acceptable, false otherwise
     */
    boolean isNoneSelectedAllowed();

    /**
     * Getter for the property indicating if it is allowed to select more than one of the checkboxes
     *
     * @return true if multiple selection is acceptable, false otherwise
     */
    boolean isMultiSelectionAllowed();
}
