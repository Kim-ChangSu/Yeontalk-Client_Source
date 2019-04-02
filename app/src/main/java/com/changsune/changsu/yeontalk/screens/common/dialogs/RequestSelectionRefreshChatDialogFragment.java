package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.chat.Chat;

public class RequestSelectionRefreshChatDialogFragment extends DialogFragment {

    Chat mChat;
    RequestSelectionRefreshChatDialogFragment.Listener mListener;

    public interface Listener {
        void onRefreshButtonClicked(Chat chat);
        void onDeleteButtonClicked(Chat chat);
    }

    public static RequestSelectionRefreshChatDialogFragment newInstance(Listener listener, Chat chat) {
        RequestSelectionRefreshChatDialogFragment requestSelectionRefreshChatDialogFragment = new RequestSelectionRefreshChatDialogFragment();
        requestSelectionRefreshChatDialogFragment.mListener = listener;
        requestSelectionRefreshChatDialogFragment.mChat = chat;
        return requestSelectionRefreshChatDialogFragment;
    }

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage(R.string.request_refresh_chat_dialog_message);

        alertDialogBuilder.setPositiveButton(
                R.string.refresh_chat,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onRefreshButtonClicked(mChat);
                    }
                }
        );

        alertDialogBuilder.setNegativeButton(
                R.string.delete,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDeleteButtonClicked(mChat);
                    }
                }
        );

        return alertDialogBuilder.create();

    }
}
