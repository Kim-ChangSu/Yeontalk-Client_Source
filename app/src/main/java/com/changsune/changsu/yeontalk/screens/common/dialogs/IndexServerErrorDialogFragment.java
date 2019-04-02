package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.changsune.changsu.yeontalk.R;
import com.changsune.changsu.yeontalk.screens.index.IndexActivity;

public class IndexServerErrorDialogFragment extends DialogFragment {

    public static IndexServerErrorDialogFragment newInstance() {

        IndexServerErrorDialogFragment indexServerErrorDialogFragment = new IndexServerErrorDialogFragment();
        indexServerErrorDialogFragment.setCancelable(false);
        return indexServerErrorDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage(R.string.index_server_error_dialog_message);

        alertDialogBuilder.setNegativeButton(
                R.string.index_server_error_dialog_negative_button_caption,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), IndexActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );

        alertDialogBuilder.setPositiveButton(
                R.string.index_server_error_dialog_positive_button_caption,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                }
        );


        return alertDialogBuilder.create();

    }
}
