package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.index.IndexActivity;

public class RequestSelectionSendChatDialogFragment extends DialogFragment {

    Listener mListener;

    public static RequestSelectionSendChatDialogFragment newInstance(Listener listener) {

        RequestSelectionSendChatDialogFragment requestSelectionSendChatDialogFragment = new RequestSelectionSendChatDialogFragment();
        requestSelectionSendChatDialogFragment.mListener = listener;
        return requestSelectionSendChatDialogFragment;
    }

    public interface Listener {
        void onButtonSendInDialogClicked();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage(R.string.request_selection_send_chat);

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
                R.string.send,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onButtonSendInDialogClicked();
                    }
                }
        );

        return alertDialogBuilder.create();

    }
}
