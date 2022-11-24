package com.app.games.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.kaopiz.kprogresshud.KProgressHUD;


public class LoadingHelper {
    private AlertDialog.Builder builder;
    private KProgressHUD dialog;

    public LoadingHelper(Context context) {
        this.dialog = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        this.builder = new AlertDialog.Builder(context);
    }


    public void showLoading(String message){
        this.dialog.setLabel(message);
        this.dialog.show();
    }

    public void dismissLoading(){
        this.dialog.dismiss();
    }



    public void showDialog(String title,
                           String message,
                           String positiveText,
                           String negativeText,
                           DialogInterface.OnClickListener positive,
                           DialogInterface.OnClickListener negative
                           ){
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText,positive)
                .setNegativeButton(negativeText,negative);
        builder.show();
    }
}
