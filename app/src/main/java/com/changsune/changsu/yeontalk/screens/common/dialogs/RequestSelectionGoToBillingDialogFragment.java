package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.changsune.changsu.yeontalk.R;

public class RequestSelectionGoToBillingDialogFragment extends DialogFragment {

    Listener mListener;

    public static RequestSelectionGoToBillingDialogFragment newInstance(Listener listener) {

        RequestSelectionGoToBillingDialogFragment requestSelectionGoToBillingDialogFragment = new RequestSelectionGoToBillingDialogFragment();
        requestSelectionGoToBillingDialogFragment.mListener = listener;
        return requestSelectionGoToBillingDialogFragment;
    }

    public interface Listener {
        void onButtonGoToBillingClicked();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage(R.string.request_selection_go_to_billing);

        alertDialogBuilder.setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dismiss();
                    }
                }
        );

        alertDialogBuilder.setPositiveButton(
                R.string.go,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onButtonGoToBillingClicked();
                    }
                }
        );

        return alertDialogBuilder.create();

    }
}
