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
                    int currentRun = 0;
                    for (int i = 0; i < result_question.getQuestions().size(); i++) {
                        String correctAnswer = result_question.getQuestions().get(i).getCorrect_answer();
                        int reLoop = 0;
                        qa qa = new qa();
                        for (int i2 = 0; i2 < result.getAttempt().size(); i2++) {
                            if (reLoop == 2) {
                                break;
                            }
                            String attemptAnswerId = result.getAttempt().get(currentRun).getAnswer();
                            currentRun += 1;
                            //comparing each attemptAnswerID to all 4 answers of a each question until its right then inserting it on a object.
                            //Same for correctAnswer
                            for (int i3 = 0; i3 < result_question.getQuestions().get(i).getObjectives().size(); i3++) {
                                if (reLoop == 2) {
                                    break;
                                }
                                String objectiveID = result_question.getQuestions().get(i).getObjectives().get(i3).getId();
                                String objectiveIDAns = result_question.getQuestions().get(i).getObjectives().get(i3).getAnswer();
                                if (objectiveID.equals(attemptAnswerId)) {
                                    qa.setAnswer(objectiveIDAns);
                                    reLoop += 1;
                                }
                                if (correctAnswer.equals(objectiveID)) {
                                    qa.setCorrect_answer(objectiveIDAns);
                                    reLoop += 1;
                                }
                            }
                        }
                        qa.setQuestion(result_question.getQuestions().get(i).getQuestion());
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

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

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