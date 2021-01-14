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
                result_question result_question2 = result_question;
                answers result = result_question.getAnswers();
                if (result != null) {
                    ArrayList<qa> qaList = new ArrayList<>();

                    for (int i1 = 0; i1 < result.getAttempt().size(); i1++) {
                        qa qa = new qa();
                        qa.setAnswer(result.getAttempt().get(i1).getJustanswer());
                        qa.setQuestion(result.getAttempt().get(i1).getJustquestion());
                        for (int i2 = 0; i2 < result_question.getQuestions().size(); i2++) {
                            if (result_question.getQuestions().get(i2).getId() == result.getAttempt().get(i1).getQuestionid()) {
                                for (int i3 = 0; i3 < result_question.getQuestions().get(i2).getObjectives().size(); i3++) {
                                    if (result_question.getQuestions().get(i2).getCorrect_answer().
                                            equals(result_question.getQuestions().get(i2).getObjectives().get(i3).getId())) {
                                        qa.setCorrect_answer(result_question.getQuestions().get(i2).getObjectives().get(i3).getAnswer());
                                        break;
                                    }
                                }
                            }
                        }
                        qaList.add(qa);
                    }
                    for (int i2 = 0; i2 < result.getAttempt().size(); i2++) {
                        for (int i1 = 0; i1 < result_question.getQuestions().size(); i1++) {
                            int a = result.getAttempt().get(i2).getQuestionid(), b = result_question.getQuestions().get(i1).getId();
                            if (a == b) {
                                result_question.getQuestions().remove(i1);
                                break;
                            }
                        }
                    }
                    for (int i1 = 0; i1 < result_question2.getQuestions().size(); i1++) {
                        qa qa = new qa();
                        qa.setQuestion(result_question2.getQuestions().get(i1).getQuestion());
                        qa.setAnswer("");
                        String correct_Answer = result_question2.getQuestions().get(i1).getCorrect_answer();
                        for (int i2 = 0; i2 < result_question2.getQuestions().get(i1).getObjectives().size(); i2++) {
                            if (correct_Answer.equals(result_question2.getQuestions().get(i1).getObjectives().get(i2).getId())) {
                                qa.setCorrect_answer(result_question2.getQuestions().get(i1).getObjectives().get(i2).getAnswer());
                                break;
                            }
                        }
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