package com.example.questionnaire.fragments.innerfragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.questionnaire.R;
import com.example.questionnaire.global.global;
import com.example.questionnaire.models.answers;
import com.example.questionnaire.models.attempt;
import com.example.questionnaire.models.questions;
import com.example.questionnaire.models.result_question;
import com.google.android.material.button.MaterialButton;

public class fragmentResult extends DialogFragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("withObject_answers", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                result_question = (result_question) bundle.getSerializable("answer");
                answers result = result_question.getAnswers();
                if (result != null) {
                    int totalAttempt = result.getAttempt().size(), totalWrong = 0, totalRight = 0, totalPoints = 0;

                    for (attempt atmp : result.getAttempt()) {
                        if (atmp.isStatus()) {
                            totalRight += 1;
                        } else {
                            totalWrong += 1;
                        }
                    }
                    for (questions q : global.questions) {
                        totalPoints += 5;
                    }
                    tvAttempt.setText(totalAttempt + "");
                    tvTotalRight.setText(totalRight + "");
                    tvTotalWrong.setText(totalWrong + "");
                    tvPoint.setText(totalPoints + "");
                    tvFinalPoint.setText(result.getTotal() + "");
                }
            }
        });
    }

    TextView tvPoint, tvAttempt, tvTotalWrong, tvTotalRight, tvFinalPoint;
    MaterialButton btnGoDash, btnGoQa;
    result_question result_question;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        //width: 1103 | height: 1138
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//        int width = getResources().getDimensionPixelSize(R.dimen.dialogFragmentWidth);
//        int height = getResources().getDimensionPixelSize(R.dimen.dialogFragmentHeight);
//        getDialog().getWindow().setLayout(width, height);
        tvPoint = view.findViewById(R.id.tvtotalpointans);
        tvAttempt = view.findViewById(R.id.tvtotalattemptans);
        tvTotalWrong = view.findViewById(R.id.tvtotalwrongans);
        tvTotalRight = view.findViewById(R.id.tvtotalrightans);
        tvFinalPoint = view.findViewById(R.id.tvtotalfinalpointans);
        btnGoDash = view.findViewById(R.id.btngotodashboard);
        btnGoQa = view.findViewById(R.id.btngotoQA);
        btnGoDash.setOnClickListener(this);
        btnGoQa.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btngotodashboard:
                getParentFragmentManager().popBackStackImmediate();
                getDialog().cancel();
                break;
            case R.id.btngotoQA:

                Bundle bundle = new Bundle();
                bundle.putSerializable("answer2", result_question);
                getParentFragmentManager().setFragmentResult("withObject_answers2", bundle);
                fragmentQA fragmentQA = new fragmentQA();
                fragmentQA.setStyle(DialogFragment.STYLE_NORMAL, R.style.checkoutFragmentXY);
                getDialog().cancel();
                fragmentQA.show(getParentFragmentManager(), "fragment_QA");
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}