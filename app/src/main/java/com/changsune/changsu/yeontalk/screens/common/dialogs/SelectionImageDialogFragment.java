package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.changsune.changsu.yeontalk.Constants;
import com.changsune.changsu.yeontalk.Constants;

public class SelectionImageDialogFragment extends DialogFragment {

    private static final String TAG = "RegistrationNickNameDia";

    private AlertDialog mDialog;
    private SelectionImageDialogFragment.Listener mListener;
    private String mKey;

    public interface Listener {
        void onButtonProfileImageClicked(int i);
        void onButtonImagesImageClicked(int i);
    }

    public static SelectionImageDialogFragment newInstance(Listener listener) {

        SelectionImageDialogFragment selectionImageDialogFragment = new SelectionImageDialogFragment();
        selectionImageDialogFragment.mListener = listener;
        return selectionImageDialogFragment;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        mKey = getArguments().getString(DialogsManager.ARGUMENT_DIALOG_ID);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        if (mKey == Constants.IMAGE) {

            String[] images = new String[]{"사진 촬영", "앨범에서 사진 선택", "기본 이미지로 변경"};

            alertDialogBuilder.setTitle("프로필");
            alertDialogBuilder.setItems(images, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onButtonProfileImageClicked(which);
                }
            });
        } else {

            String[] images = new String[]{"사진 촬영", "앨범에서 사진 선택"};

            alertDialogBuilder.setTitle("사진첩");
            alertDialogBuilder.setItems(images, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onButtonImagesImageClicked(which);
                }
            });

        }


        return mDialog = alertDialogBuilder.create();
    }
}
