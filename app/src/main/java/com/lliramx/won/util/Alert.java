package com.lliramx.won.util;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Alert {

    private final Context context;

    public Alert(Context context) {
        this.context = context;
    }

    public void show(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Accept", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
