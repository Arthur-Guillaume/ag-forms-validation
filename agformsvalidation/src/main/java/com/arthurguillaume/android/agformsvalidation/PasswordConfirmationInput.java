package com.arthurguillaume.android.agformsvalidation;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;

/**
 * Created by Arthur Guillaume on 09/02/2017.
 */

public class PasswordConfirmationInput extends AppCompatEditText implements IValidatedInput {

    private String errorMessage;
    private int referencedField;

    /**
     * Constructor called when instantiating from xml
     *
     * @param context the context of the activity linked to the view
     * @param attrs   the attributes defined in xml
     */
    public PasswordConfirmationInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.PasswordConfirmationInput);
        referencedField = attributes.getResourceId(R.styleable.PasswordConfirmationInput_confirm_password, NO_ID);
        String userDefinedErrorMessage = attributes.getString(R.styleable.PasswordConfirmationInput_errorMessage);
        if (userDefinedErrorMessage != null) {
            errorMessage = userDefinedErrorMessage;
        } else {
            errorMessage = null;
        }
        attributes.recycle();
    }

    public boolean validateWithView(EditText v) {
        return v.getText().toString().equals(this.getText().toString());
    }

    @Override
    public boolean isValid() {
        if (this.referencedField != NO_ID) {
            ViewParent vp;
            View v = this;
            while ((vp = v.getParent()) != null) {
                if (vp instanceof ViewGroup) {
                    View target = ((ViewGroup) vp).findViewById(referencedField);
                    if (target != null && target instanceof EditText) {
                        return this.validateWithView((EditText) target);
                    } else {
                        v = (View) vp;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getErrorMessage() {
        if (this.errorMessage != null) {
            return this.errorMessage;
        } else {
            return "Les mots de passe ne correspondent pas";
        }
    }
}
