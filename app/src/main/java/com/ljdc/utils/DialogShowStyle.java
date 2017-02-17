package com.ljdc.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by LONG on 14-1-13.
 */
public class DialogShowStyle {
    private Context context;
    private String content;
    private ProgressDialog progressDialog;

    public DialogShowStyle(Context context, String content) {
        this.context = context;
        this.content = content;
    }

    public void dialogShow() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(content);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void dialogDismiss() {
        if (null!=progressDialog) {
            progressDialog.dismiss();
            progressDialog=null;
        }
    }
}
