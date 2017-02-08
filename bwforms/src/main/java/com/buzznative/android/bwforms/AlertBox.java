package com.buzznative.android.bwforms;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * An Alert Box is a conveniant class overloading Alert Dialog.
 * The most usefull method is auto dismiss the dialog after a given time.
 * Created by Arthur Guillaume on 30/11/15.
 */
public class AlertBox {

    private Context context;
    private String title;
    private String message;
    private boolean isCancellable = true;
    private Runnable callback = null;
    private int duration = -1;
    private int layoutResId = -1;
    private int titleResId;
    private int messageResId;

    private AlertDialog dialog;

    /**
     * A constructor which will allow timeout and custom layout
     *
     * @param context       the context to create the dialog
     * @param layoutResId   resource id of the xml custom layout
     * @param titleResId    the id of a textview in the layout which will be used to display the title. Pass -1 if no title.
     * @param title         the title of the alert. Pass null if you don't need a title.
     * @param messageResId  the id of a textview in the layout which will be used to display the message
     * @param message       the message to display
     * @param isCancellable pass true to enable dismissing the pop up. If false given, the user cannot dismiss the pop up
     * @param callback      the code that will be executed after the pop up is dismissed
     * @param duration      time, in milliseconds, while the popup is shown. Once elapsed, pop up is dismissed automatically.
     */
    public AlertBox(Context context, int layoutResId, int titleResId, String title, int messageResId, String message, boolean isCancellable, Runnable callback, int duration) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.titleResId = titleResId;
        this.title = title;
        this.messageResId = messageResId;
        this.message = message;
        this.isCancellable = isCancellable;
        this.callback = callback;
        this.duration = duration;
        build();
    }

    /**
     * A constructor which will allow custom layout
     *
     * @param context      the context to create the dialog
     * @param layoutResId  resource id of the xml custom layout
     * @param titleResId   the id of a textview in the layout which will be used to display the title. Pass -1 if no title.
     * @param title        the title of the alert. Pass null if you don't need a title.
     * @param messageResId the id of a textview in the layout which will be used to display the message
     * @param message      the message to display
     */
    public AlertBox(Context context, int layoutResId, int titleResId, String title, int messageResId, String message) {
        this.context = context;
        this.layoutResId = layoutResId;
        this.titleResId = titleResId;
        this.title = title;
        this.messageResId = messageResId;
        this.message = message;
        build();
    }

    /**
     * Basic constructor.
     *
     * @param context the context to create the dialog
     * @param title   the title of the alert. Pass null if you don't need a title.
     * @param message the message to display
     */
    public AlertBox(Context context, String title, String message) {
        this.context = context;
        this.title = title;
        this.message = message;
        build();
    }

    /**
     * A constructor which will allow timeout
     *
     * @param context       the context to create the dialog
     * @param title         the title of the alert. Pass null if you don't need a title.
     * @param message       the message to display
     * @param isCancellable pass true to enable dismissing the pop up. If false given, the user cannot dismiss the pop up
     * @param callback      the code that will be executed after the pop up is dismissed
     * @param duration      time, in milliseconds, while the popup is shown. Once elapsed, pop up is dismissed automatically.
     */
    public AlertBox(Context context, String title, String message, boolean isCancellable, Runnable callback, int duration) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.isCancellable = isCancellable;
        this.callback = callback;
        this.duration = duration;
        build();
    }

    private void build() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (layoutResId != -1) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(layoutResId, null, false);
            builder.setView(v);
            TextView tv = (TextView) v.findViewById(titleResId);
            if (!"".equals(title) && tv != null) {
                tv.setText(title);
            }
            ((TextView) v.findViewById(messageResId)).setText(message);
        } else {
            builder.setTitle(title);
            builder.setMessage(message);
        }
        builder.setCancelable(isCancellable);
        if (duration <= 0) {
            builder.setPositiveButton("OK", null);
        }
        dialog = builder.create();
    }

    /**
     * Display the popup on screen.
     * If a duration > 0 has been set, the pop up will be dismissed automatically and
     * the callback called if it is not null.
     */
    public void show() {
        if (dialog == null) {
            throw new NullPointerException("Cannot display null AlertBox");
        }
        dialog.show();
        if (duration > 0) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    if (callback != null) {
                        callback.run();
                    }
                }
            }, duration);
        }
    }
}
