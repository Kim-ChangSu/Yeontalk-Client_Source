package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.changsune.changsu.yeontalk.R;

public class RequestSelectionCheckBoxDialogFragment extends DialogFragment {


    public static RequestSelectionCheckBoxDialogFragment newInstance() {
        return new RequestSelectionCheckBoxDialogFragment();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String value = getArguments().getString(DialogsManager.ARGUMENT_DIALOG_ID);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        if (value.equals("checkBox1")) {
            alertDialogBuilder.setMessage(R.string.request_selection_access_term);
        } else {
            alertDialogBuilder.setMessage(R.string.request_selection_privacy_policy);
        }

        alertDialogBuilder.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }
        );

        return alertDialogBuilder.create();

    }

}
