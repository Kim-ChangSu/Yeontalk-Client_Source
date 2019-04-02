package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.changsune.changsu.yeontalk.R;

public class RequestSelectionExitChatRoomDialogFragment extends DialogFragment {

    Listener mListener;

    public static RequestSelectionExitChatRoomDialogFragment newInstance(Listener listener) {

        RequestSelectionExitChatRoomDialogFragment requestSelectionExitChatRoomDialogFragment = new RequestSelectionExitChatRoomDialogFragment();
        requestSelectionExitChatRoomDialogFragment.mListener = listener;
        return requestSelectionExitChatRoomDialogFragment;
    }

    public interface Listener {
        void onButtonExitClicked();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage(R.string.request_selection_exit_chat_room);

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
                R.string.exit,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onButtonExitClicked();
                    }
                }
        );

        return alertDialogBuilder.create();

    }
}
