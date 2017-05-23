# Forms Validation
[![Release](https://jitpack.io/v/Arthur-Guillaume/ag-forms-validation.svg)](https://jitpack.io/#Arthur-Guillaume/ag-forms-validation)

This android library is intended to simplify form validation.  

An example is provided in this GitHub repo under app module.  

## Installation
Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and:

```gradle
dependencies {
    compile 'com.github.Arthur-Guillaume:ag-forms-validation:0.1.1'
}
```

## How to use
First, add a Form element to your layout. It should wrap all the fields you want to validate.  

` <com.arthurguillaume.android.agformsvalidation.Form
       android:id="@+id/form"
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:orientation="vertical">

       <com.arthurguillaume.android.agformsvalidation.ValidatedTextInput
           android:id="@+id/username_edit_text"
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:hint="@string/username_hint"
           app:errorMessage="@string/username_error_message"
           app:input_type="not_empty_text" />
</com.arthurguillaume.android.agformsvalidation.Form>`

*Note that the form element extends LinearLayout*
You can nest as many ViewGroups as you like inside the Form tag.

Each field you want to validate must be a validateTextInput or an element extending it.

Two properties are available for ValidatedTextInput :  
  - errorMessage : Which is defining a message to show if the input is not correct.
  - input_type : The type of input expected in this field.  

You must, in your activity, init the form by calling the init method on it.

`((Form)this.findViewById(R.id.form)).init();`

To check for errors, use the following statement :
`form.isFormValid(getContext(), true, View.NO_ID, View.NO_ID, View.NO_ID)`

Where the first parameter is a context, if you are using it inside of an activity, simply pass `this`.
The second parameter is a boolean, if false, no error message will be displayed, if true, the first field failing validation will stop the process and display an error message. The message displayed is the one defined earlier in the xml file.
The three next parameters are, a layout resource and two text view resources used for displaying errors, the first text view is for the title, the second for the message. If you wish to let the system handle the alert box for you, just pass View.NO_ID.

## Available widgets
* ValidatedTextInput
  EditText with the following possible input_type
  * plain_text : no validation
  * not_empty_text : fails only if the input is empty
  * phone_number : validates french phone numbers (starting with 0 and containing 10 figures)
  * zip_code : validates french postal codes
  * email :
  * date : a date complying with the format `dd/MM/yyyy`
* PasswordConfirmationInput
  * confirm_password : a reference to a field. Checks they are identical
* CheckboxGroup
  * isMultipleSelectionAllowed : unchecks previously checked checkboxes in the group
  * isEmptySelectionAllowed : fails validation if nothing is selected
* ValidatedCheckBox
  * required : fails validation if checkbox  is not checked.

  
