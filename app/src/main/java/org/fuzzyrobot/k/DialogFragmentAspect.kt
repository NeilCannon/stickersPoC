package org.fuzzyrobot.k

import android.app.AlertDialog
import android.support.v4.app.FragmentActivity

class DialogFragmentAspect(val activity: FragmentActivity) : Aspect {

    var alertDialog: AlertDialog? = null

    override fun add() {
        alertDialog = AlertDialog.Builder(activity).create();
        alertDialog?.setTitle("Alert");
        alertDialog?.setMessage("Alert message to be shown");
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
        alertDialog?.show();
    }

    override fun remove() {
        alertDialog?.dismiss()
    }
}