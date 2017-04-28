package com.arthurguillaume.android.agformsvalidation;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

/**
 * Created by Arthur Guillaume on 28/04/2017.
 */

public class ValidatedCheckBox extends AppCompatCheckBox implements IValidatedInput {

    private String errorMessage;
    private boolean required;

    public ValidatedCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ValidatedCheckBox);
        for (int i = 0; i < attributes.getIndexCount(); i++) {
            required = attributes.getBoolean(R.styleable.ValidatedCheckBox_required, false);
            String userDefinedErrorMessage = attributes.getString(R.styleable.ValidatedCheckBox_errorMessage);
            if (userDefinedErrorMessage != null) {
                errorMessage = userDefinedErrorMessage;
            } else {
                errorMessage = "La case à cocher est obligatoire";
            }
        }
        attributes.recycle();
    }

    @Override
    public boolean isValid() {
        return required && isChecked();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
