package com.example.covinfo.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.example.covinfo.R;

public class CovidAdviceFragment extends Fragment {

    public CovidAdviceFragment() {
        // Required empty public constructor
    }

    public static CovidAdviceFragment newInstance(int adviceNum){
        CovidAdviceFragment adviceFragment = new CovidAdviceFragment();
        Bundle args = new Bundle();
        args.putInt("adviceNum", adviceNum);
        adviceFragment.setArguments(args);
        return adviceFragment;
    }

    private int adviceNum;

    private ImageView imgAdvice;
    private TextView tvTitle, tvMessage;

    private String[] titleArray, messageArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            adviceNum = getArguments().getInt("adviceNum");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_covid_advice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgAdvice = view.findViewById(R.id.imgAdvice);
        tvTitle = view.findViewById(R.id.tvAdviceLabel);
        tvMessage = view.findViewById(R.id.tvAdviceMessage);

        titleArray = requireActivity().getResources().getStringArray(R.array.advice_title);
        messageArray = requireActivity().getResources().getStringArray(R.array.advice_message);

        setAdvice();
    }

    public void setAdvice() {
        String title = titleArray[adviceNum];
        String message = messageArray[adviceNum];

        tvTitle.setText(title);
        tvMessage.setText(message);

        Drawable img;
        switch (adviceNum) {
            case 1:
                img = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_advice_02);
                break;
            case 2:
                img = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_advice_03);
                break;
            case 3:
                img = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_advice_04);
                break;
            case 4:
                img = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_advice_05);
                break;
            case 5:
                img = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_advice_06);
                break;
            case 6:
                img = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_advice_07);
                break;
            case 7:
                img = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_advice_08);
                break;
            case 8:
                img = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_advice_09);
                break;
            case 9:
                img = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_advice_10);
                break;
            default:
                img = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_advice_01);
                break;
        }

        imgAdvice.setImageDrawable(img);
    }
}