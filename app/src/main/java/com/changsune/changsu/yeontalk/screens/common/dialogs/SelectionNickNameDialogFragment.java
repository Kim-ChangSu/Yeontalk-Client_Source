package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.changsune.changsu.yeontalk.R;

public class SelectionNickNameDialogFragment extends DialogFragment {

    private static final String TAG = "RegistrationNickNameDia";


    private EditText mEditTextNickname;
    private AlertDialog mDialog;
    private InputMethodManager mInputMethodManager;
    private SelectionNickNameDialogFragment.Listener mListener;
    private View mView;
    private String mNickName;


    public interface Listener {
        void onPositiveButtonClicked(String nickName);
    }

    public static SelectionNickNameDialogFragment newInstance(Listener listener) {

        SelectionNickNameDialogFragment selectionNickNameDialogFragment = new SelectionNickNameDialogFragment();
        selectionNickNameDialogFragment.mListener = listener;
        return selectionNickNameDialogFragment;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        mNickName = getArguments().getString(DialogsManager.ARGUMENT_DIALOG_ID);

        mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_registration_nickname, null);
        mEditTextNickname = mView.findViewById(R.id.edittext_registration_nickname);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(mView);
        alertDialogBuilder.setTitle(R.string.registration_nickname);
        mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        mEditTextNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mEditTextNickname.getText().toString().trim().isEmpty()) {
                    mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.lightGray));
                    mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                    mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mNickName = mEditTextNickname.getText().toString().trim();
                mListener.onPositiveButtonClicked(mNickName);
                mInputMethodManager.hideSoftInputFromWindow(mEditTextNickname.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });

        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mInputMethodManager.hideSoftInputFromWindow(mEditTextNickname.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                dismiss();

            }
        });

        return mDialog = alertDialogBuilder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        mDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));

        if (mNickName != null) {
            mEditTextNickname.setText(mNickName);
        }
        if (mEditTextNickname.getText().toString().trim().isEmpty()) {
            mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.lightGray));
            mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
        } else {
            mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
            mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
        }

        mEditTextNickname.requestFocus();
    }
}
