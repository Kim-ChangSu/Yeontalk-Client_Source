package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.changsune.changsu.yeontalk.R;

public class RequestSelectionSessionDialogFragment extends DialogFragment {

    Listener mListener;

    public static RequestSelectionSessionDialogFragment newInstance(Listener listener) {

        RequestSelectionSessionDialogFragment requestSelectionSessionDialogFragment = new RequestSelectionSessionDialogFragment();
        requestSelectionSessionDialogFragment.mListener = listener;
        return requestSelectionSessionDialogFragment;
    }

    public interface Listener {
        void onButtonSessionClicked();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage(R.string.request_selection_session);

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
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onButtonSessionClicked();
                    }
                }
        );

        return alertDialogBuilder.create();

    }
}
