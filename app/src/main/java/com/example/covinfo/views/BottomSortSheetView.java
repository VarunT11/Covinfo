package com.example.covinfo.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.covinfo.R;
import com.example.covinfo.enums.CaseType;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.util.ArrayList;

public class BottomSortSheetView extends BottomSheetDialogFragment {

    public interface SortSheetInterface {
        void onItemSelectListener(boolean byName, CaseType caseType, boolean reverse);
    }

    private final String TAG;
    private final SortSheetInterface sortSheetInterface;

    private final ArrayList<CaseType> caseTypeList;

    private CaseType currentCaseType;
    private boolean byName, sortReverse;

    private MaterialRadioButton rbName, rbConfirmed, rbRecovered, rbDeceased, rbActive, rbIncreasing, rbDecreasing;

    public BottomSortSheetView(String tag, ArrayList<CaseType> CaseTypeList, SortSheetInterface sheetInterface) {
        TAG = tag;
        sortSheetInterface = sheetInterface;
        caseTypeList = CaseTypeList;
    }

    public String getTAG() {
        return TAG;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sort_sheet_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup rgSort = view.findViewById(R.id.rgSort);
        RadioGroup rgOrder = view.findViewById(R.id.rgOrder);

        rbName = view.findViewById(R.id.mrbName);
        rbConfirmed = view.findViewById(R.id.mrbConfirmed);
        rbRecovered = view.findViewById(R.id.mrbRecovered);
        rbDeceased = view.findViewById(R.id.mrbDeceased);
        rbActive = view.findViewById(R.id.mrbActive);
        rbIncreasing = view.findViewById(R.id.mrbIncreasing);
        rbDecreasing = view.findViewById(R.id.mrbDecreasing);

        rbConfirmed.setVisibility(View.GONE);
        rbRecovered.setVisibility(View.GONE);
        rbDeceased.setVisibility(View.GONE);
        rbActive.setVisibility(View.GONE);

        for (CaseType caseType : caseTypeList) {
            switch (caseType) {
                case TOTAL_CONFIRMED:
                case DAILY_CONFIRMED: {
                    rbConfirmed.setVisibility(View.VISIBLE);
                    break;
                }
                case TOTAL_RECOVERED:
                case DAILY_RECOVERED: {
                    rbRecovered.setVisibility(View.VISIBLE);
                    break;
                }
                case TOTAL_DECEASED:
                case DAILY_DECEASED: {
                    rbDeceased.setVisibility(View.VISIBLE);
                    break;
                }
                case TOTAL_ACTIVE:
                case DAILY_ACTIVE:{
                    rbActive.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }

        byName = true;
        sortReverse = false;
        currentCaseType = null;

        rgOrder.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == rbIncreasing.getId()){
                sortReverse = false;
            } else if(checkedId == rbDecreasing.getId()){
                sortReverse = true;
            }
            sortSheetInterface.onItemSelectListener(byName, currentCaseType, sortReverse);
        });

        rgSort.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == rbName.getId()){
                byName = true;
                currentCaseType = null;
            } else if (checkedId == rbConfirmed.getId()){
                byName = false;
                currentCaseType = CaseType.TOTAL_CONFIRMED;
            } else if (checkedId == rbRecovered.getId()){
                byName = false;
                currentCaseType = CaseType.TOTAL_RECOVERED;
            } else if (checkedId == rbDeceased.getId()){
                byName = false;
                currentCaseType = CaseType.TOTAL_DECEASED;
            } else if (checkedId == rbActive.getId()){
                byName = false;
                currentCaseType = CaseType.TOTAL_ACTIVE;
            }
            sortSheetInterface.onItemSelectListener(byName, currentCaseType, sortReverse);
        });

        rbName.setChecked(true);
        rbIncreasing.setChecked(true);
    }

}
