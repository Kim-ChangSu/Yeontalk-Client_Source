package com.changsune.changsu.yeontalk.screens.common.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.changsune.changsu.yeontalk.R;

import java.util.ArrayList;

public class SelectionUserDataDialogFragment extends DialogFragment {

    private static final String TAG = "RegistrationDialogFragm";

    String mKey;
    String mValue;
    String mValue2;
    int mItemNum;

    String mGender;
    String mBirthYear;
    String mNation;
    String mRegion;
    ArrayList<String> mStrings;

    AlertDialog mDialog;
    Listener mListener;

    public interface Listener {
        void onPositiveButtonClicked(String key, String value);
    }

    public static SelectionUserDataDialogFragment newInstance(Listener listener, String string) {

        SelectionUserDataDialogFragment selectionUserDataDialogFragment = new SelectionUserDataDialogFragment();
        selectionUserDataDialogFragment.mListener = listener;
        selectionUserDataDialogFragment.mValue = string;
        return selectionUserDataDialogFragment;

    }

    public static SelectionUserDataDialogFragment newInstance(Listener listener, String string, String string2) {

        SelectionUserDataDialogFragment selectionUserDataDialogFragment = new SelectionUserDataDialogFragment();
        selectionUserDataDialogFragment.mListener = listener;
        selectionUserDataDialogFragment.mValue = string;
        selectionUserDataDialogFragment.mValue2 = string2;
        return selectionUserDataDialogFragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mKey = getArguments().getString(DialogsManager.ARGUMENT_DIALOG_ID);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        if (mKey.equals("성별")) {
            mStrings = new ArrayList<>();
            mStrings.add("남자");
            mStrings.add("여자");

            createDialog(alertDialogBuilder, mStrings, mKey, mValue);

        } else if (mKey.equals("생년")) {
            mStrings = new ArrayList<>();
            for (int i = 2000; i >= 1900; i--) {
                mStrings.add(Integer.toString(i));
            }
            
            createDialog(alertDialogBuilder, mStrings, mKey, mValue);
            
        } else if (mKey.equals("국가")) {
            mStrings = new ArrayList<>();
            mStrings.add("대한민국");
            mStrings.add("일본");
            mStrings.add("미국");
            mStrings.add("중국");
            mStrings.add("기타국가");

            createDialog(alertDialogBuilder, mStrings, mKey, mValue);
            
        } else if (mKey.equals("지역")) {
            mStrings = new ArrayList<>();
            
            if (mValue2.equals("대한민국")) {
                mStrings.add("서울");
                mStrings.add("부산");
                mStrings.add("대구");
                mStrings.add("인천");
                mStrings.add("광주");
                mStrings.add("대전");
                mStrings.add("울산");
                mStrings.add("경기");
                mStrings.add("강원");
                mStrings.add("충북");
                mStrings.add("전북");
                mStrings.add("전남");
                mStrings.add("경북");
                mStrings.add("경남");
                mStrings.add("제주");
                mStrings.add("세종");
            } else if (mValue2.equals("일본")) {
                mStrings.add("훗카이도");
                mStrings.add("아오모리 현");
                mStrings.add("이와테 현");
                mStrings.add("미야기 현");
                mStrings.add("아키타 현");
                mStrings.add("야마가타 현");
                mStrings.add("후쿠시마 현");
                mStrings.add("이바라키 현");
                mStrings.add("도치기 현");
                mStrings.add("군마 현");
                mStrings.add("사이타마 현");
                mStrings.add("지바 현");
                mStrings.add("도쿄 도");
                mStrings.add("가나가와 현");
                mStrings.add("니가타 현");
                mStrings.add("도야마 현");
                mStrings.add("기후 현");
                mStrings.add("시즈오카 현");
                mStrings.add("아이치 현");
                mStrings.add("마에 현");
                mStrings.add("시가 현");
                mStrings.add("교토 부");
                mStrings.add("오사카 부");
            } else if (mValue2.equals("미국")) {
                mStrings.add("앨리배마");
                mStrings.add("알래스카");
                mStrings.add("애리조나");
                mStrings.add("아칸소");
                mStrings.add("캘리포니아");
                mStrings.add("콜로라도");
                mStrings.add("코네티컷");
                mStrings.add("델라웨어");
                mStrings.add("플로리다");
                mStrings.add("조지아");
                mStrings.add("하와이");
                mStrings.add("아이다호");
                mStrings.add("일리노이");
                mStrings.add("인디애나");
                mStrings.add("캔자스");
                mStrings.add("켄터키");
            } else if (mValue2.equals("중국")) {
                mStrings.add("안후이 성");
                mStrings.add("베이징");
                mStrings.add("충칭");
                mStrings.add("푸젠 성");
                mStrings.add("간쑤 성");
                mStrings.add("광둥 성");
                mStrings.add("광시 좡족 자치구");
                mStrings.add("구이저우 성");
                mStrings.add("하이난 성");
                mStrings.add("허베이 성");
                mStrings.add("헤이룽장 성");
                mStrings.add("허난 성");
                mStrings.add("후베이 성");
                mStrings.add("후난 성");
                mStrings.add("내몽골 자치구");
            } else {
                mStrings.add("기타지역");
            }
            createDialog(alertDialogBuilder, mStrings, mKey, mValue);
        }


        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return mDialog = alertDialogBuilder.create();

    }

    private AlertDialog.Builder createDialog(AlertDialog.Builder alertDialogBuilder,
                                                   final ArrayList<String> arrayList,
                                                   final String key,
                                                   final String value) {


        mItemNum = -1;

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(value)) {
                mItemNum = i;
            }
        }
        alertDialogBuilder.setTitle(key);

        alertDialogBuilder.setSingleChoiceItems(arrayList.toArray(new String[arrayList.size()]), mItemNum, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
                mValue = arrayList.get(which);
            }
        });

        alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onPositiveButtonClicked(key, mValue);
                    }
                }
        );

        return alertDialogBuilder;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mValue == null) {
            mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.lightGray));
            mDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
        }
    }
}
