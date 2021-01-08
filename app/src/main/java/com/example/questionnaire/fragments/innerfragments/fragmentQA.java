package com.example.questionnaire.fragments.innerfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.questionnaire.R;
import com.example.questionnaire.adapter.FaqAdapter;
import com.example.questionnaire.adapter.QaAdapter;
import com.example.questionnaire.global.global;
import com.example.questionnaire.models.answers;
import com.example.questionnaire.models.attempt;
import com.example.questionnaire.models.objectives;
import com.example.questionnaire.models.qa;
import com.example.questionnaire.models.questions;
import com.example.questionnaire.models.result_question;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class fragmentQA extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("withObject_answers2", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                result_question = (result_question) bundle.getSerializable("answer2");
                answers result = result_question.getAnswers();
                if (result != null) {
                    ArrayList<qa> qaList = new ArrayList<>();
                    for (questions questions : result_question.getQuestions()) {
                        qa qa = new qa();
                        for (attempt atmpt : result.getAttempt()) {
                            for (objectives obj : questions.getObjectives()) {
                                if (obj.getId().equals(atmpt.getAnswer())) {
                                    qa.setAnswer(obj.getAnswer());
                                }
                                if (questions.getCorrect_answer().equals(obj.getId())) {
                                    qa.setCorrect_answer(obj.getAnswer());
                                }
                            }
                        }
                        qa.setQuestion(questions.getQuestion());
                        qaList.add(qa);
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    QaAdapter qaAdapter = new QaAdapter(getContext(), qaList);
                    recyclerView.setAdapter(qaAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }
        });
    }

    RecyclerView recyclerView;
    MaterialButton btnQaClose, btnQaGoBack;
    result_question result_question;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_a, container, false);
        int width = getResources().getDimensionPixelSize(R.dimen.dialogFragmentWidth);
        int height = getResources().getDimensionPixelSize(R.dimen.dialogFragmentHeight);
        getDialog().getWindow().setLayout(width, height);
        recyclerView = view.findViewById(R.id.recycler_view_qa);
        btnQaClose = view.findViewById(R.id.btnqaclose);
        btnQaClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStackImmediate();
                getDialog().cancel();
            }
        });
        btnQaGoBack = view.findViewById(R.id.btnqagoback);
        btnQaGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("answer", result_question);
                getParentFragmentManager().setFragmentResult("withObject_answers", bundle);
                fragmentResult fragmentResult = new fragmentResult();
                fragmentResult.setStyle(DialogFragment.STYLE_NORMAL, R.style.checkoutFragmentXY);
                getDialog().cancel();
                fragmentResult.show(getParentFragmentManager(), "fragment_Result");
            }
        });
        return view;

    }
}