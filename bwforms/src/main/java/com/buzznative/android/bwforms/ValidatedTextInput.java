package com.buzznative.android.bwforms;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;


/**
 * Text input which can manage a validation scheme
 * Created by Arthur on 23/11/2015.
 */
public class ValidatedTextInput extends EditText implements ITextValidatedInput {
    /**
     * No validation
     */
    public static final int PLAIN_TEXT = 0;
    /**
     * Validation will fail if field is empty
     */
    public static final int NOT_EMPTY_TEXT = 1;
    /**
     * a french phone number (10 digits starting 0 and followed by non-zero figure)
     */
    public static final int PHONE_NUMBER = 2;
    /**
     * a french zip code (5 digits with non zero figure in second place)
     */
    public static final int ZIP_CODE = 3;
    /**
     * a valid email address
     */
    public static final int EMAIL = 4;

    private int validationType;
    private String errorMessage;

    /**
     * Constructor called when instanciating from xml
     *
     * @param context the context of the activity linked to the view
     * @param attrs   the attributes defined in xml
     */
    public ValidatedTextInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ValidatedTextInput);
        for (int i = 0; i < attributes.getIndexCount(); i++) {
            validationType = attributes.getInteger(R.styleable.ValidatedTextInput_input_type, PLAIN_TEXT);
            String userDefinedErrorMessage = attributes.getString(R.styleable.CheckBoxGroup_errorMessage);
            if (userDefinedErrorMessage != null) {
                errorMessage = userDefinedErrorMessage;
            } else {
                errorMessage = getDefaultErrorMessageForType(validationType);
            }
        }
        attributes.recycle();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Get generic error message depending on validation type
     *
     * @param type a type defined in constants of this class
     * @return a string containing the auto-generated error message
     */
    public String getDefaultErrorMessageForType(int type) {
        String msg;
        switch (type) {
            case PLAIN_TEXT:
                msg = "Pas de validation sur ce champ !";
                break;
            case NOT_EMPTY_TEXT:
                msg = "Le champ ne peut être vide.";
                break;
            case PHONE_NUMBER:
                msg = "Le numéro de téléphone n'est pas valide.";
                break;
            case ZIP_CODE:
                msg = "Le code postal n'est pas valide.";
                break;
            case EMAIL:
                msg = "L'e-mail n'est pas valide";
                break;
            default:
                msg = "Un champ n'est pas valide.";
                break;
        }
        return msg;
    }

    @Override
    public boolean isValid() {
        return getText().toString().matches(getValidationRegEx());
    }

    @Override
    public String getValidationRegEx() {
        String regex;
        switch (validationType) {
            case NOT_EMPTY_TEXT:
                regex = "^.+";
                break;
            case PHONE_NUMBER:
                regex = "(^0)([1-9])([0-9]{8})";
                break;
            case ZIP_CODE:
                regex = "(^(0[1-9])|([1-9][0-9]))([0-9]{3})";
                break;
            case EMAIL:
                regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";
                break;
            case PLAIN_TEXT:
            default:
                regex = "^.*";
                break;
        }
        return regex;
    }
}
