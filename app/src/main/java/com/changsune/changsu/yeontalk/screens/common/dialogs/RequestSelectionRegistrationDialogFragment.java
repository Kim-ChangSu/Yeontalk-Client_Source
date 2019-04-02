package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.changsune.changsu.yeontalk.R;

public class RequestSelectionRegistrationDialogFragment extends DialogFragment {

    public static RequestSelectionRegistrationDialogFragment newInstance() {
        return new RequestSelectionRegistrationDialogFragment();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String value = getArguments().getString(DialogsManager.ARGUMENT_DIALOG_ID);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        if (value.equals("성별")){
            alertDialogBuilder.setMessage(R.string.request_selection_gender);
        } else if (value.equals("닉네임")){
            alertDialogBuilder.setMessage(R.string.request_selection_nickname);
        } else if (value.equals("생년")){
            alertDialogBuilder.setMessage(R.string.request_selection_birthyear);
        } else if (value.equals("국가")){
            alertDialogBuilder.setMessage(R.string.request_selection_nation);
        } else if (value.equals("지역")){
            alertDialogBuilder.setMessage(R.string.request_selection_region);
        } else if (value.equals("국가먼저")){
            alertDialogBuilder.setMessage(R.string.request_selection_nation_first);
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
