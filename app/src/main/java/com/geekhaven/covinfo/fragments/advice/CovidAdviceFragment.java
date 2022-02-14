package com.geekhaven.covinfo.fragments.advice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.geekhaven.covinfo.R;

public class CovidAdviceFragment extends Fragment {

    private static final String KEY_ADVICE_NUM = "adviceNum";

    public CovidAdviceFragment() {
    }

    public static CovidAdviceFragment newInstance(int adviceNum){
        CovidAdviceFragment adviceFragment = new CovidAdviceFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ADVICE_NUM, adviceNum);
        adviceFragment.setArguments(args);
        return adviceFragment;
    }

    private int adviceNum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
            adviceNum = getArguments().getInt(KEY_ADVICE_NUM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_covid_advice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvTitle = view.findViewById(R.id.tvAdviceLabel);
        String[] titleArray = requireActivity().getResources().getStringArray(R.array.advice_title);
        tvTitle.setText(titleArray[adviceNum]);

        TextView tvMessage = view.findViewById(R.id.tvAdviceMessage);
        String[] messageArray = requireActivity().getResources().getStringArray(R.array.advice_message);
        tvMessage.setText(messageArray[adviceNum]);

        @DrawableRes int drawableId;

        switch (adviceNum) {
            case 1:
                drawableId = R.drawable.ic_advice_02;
                break;
            case 2:
                drawableId = R.drawable.ic_advice_03;
                break;
            case 3:
                drawableId = R.drawable.ic_advice_04;
                break;
            case 4:
                drawableId = R.drawable.ic_advice_05;
                break;
            case 5:
                drawableId = R.drawable.ic_advice_06;
                break;
            case 6:
                drawableId = R.drawable.ic_advice_07;
                break;
            case 7:
                drawableId = R.drawable.ic_advice_08;
                break;
            case 8:
                drawableId = R.drawable.ic_advice_09;
                break;
            case 9:
                drawableId = R.drawable.ic_advice_10;
                break;
            default:
                drawableId = R.drawable.ic_advice_01;
                break;
        }

        ImageView imgAdvice = view.findViewById(R.id.imgAdvice);
        imgAdvice.setImageDrawable(AppCompatResources.getDrawable(requireActivity(), drawableId));
    }
}