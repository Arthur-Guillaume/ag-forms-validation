package com.buzznative.android.androidformvalidationlibrary;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.buzznative.android.bwforms.Form;

public class ExampleActivity extends AppCompatActivity {

    protected Form form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        form = (Form) this.findViewById(R.id.form);
        form.init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void submit(View view) {
        if (form.isFormValid(this, true, View.NO_ID, View.NO_ID, View.NO_ID)) {
            Snackbar.make(view, "Form is correct", Snackbar.LENGTH_LONG).show();
        }
    }
}
