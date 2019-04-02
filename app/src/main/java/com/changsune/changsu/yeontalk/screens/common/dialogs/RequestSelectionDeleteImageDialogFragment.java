package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.changsune.changsu.yeontalk.R;

public class RequestSelectionDeleteImageDialogFragment extends DialogFragment {

    int mPosition;
    RequestSelectionDeleteImageDialogFragment.Listener mListener;

    public interface Listener {
        void onPositiveButtonClicked(int position);
    }

    public static RequestSelectionDeleteImageDialogFragment newInstance(Listener listener, int position) {
        RequestSelectionDeleteImageDialogFragment requestSelectionDeleteImageDialogFragment = new RequestSelectionDeleteImageDialogFragment();
        requestSelectionDeleteImageDialogFragment.mListener = listener;
        requestSelectionDeleteImageDialogFragment.mPosition = position;
        return requestSelectionDeleteImageDialogFragment;
    }

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage(R.string.request_delete_image_dialog_message);

        alertDialogBuilder.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onPositiveButtonClicked(mPosition);
                    }
                }
        );

        alertDialogBuilder.setNegativeButton(
                R.string.cancel,
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
