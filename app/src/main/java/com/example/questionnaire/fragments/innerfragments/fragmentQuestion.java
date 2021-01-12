package com.example.questionnaire.fragments.innerfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.questionnaire.R;
import com.example.questionnaire.adapter.RecyclerAdapter;
import com.example.questionnaire.apiInterface.httpRequests;
import com.example.questionnaire.global.global;
import com.example.questionnaire.models.answers;
import com.example.questionnaire.models.data;
import com.example.questionnaire.models.questions;
import com.example.questionnaire.models.result_question;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class fragmentQuestion extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("withObject_question", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                final data result = (data) bundle.getSerializable("question");
                if (result != null) {
                    questionlist = result.getQuestions();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), null, getParentFragmentManager(), "thequestion", questionlist);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }
        });
    }

    private ArrayList<questions> questionlist;
    private RecyclerView recyclerView;
    MaterialButton btnSubmitAnswer;
    public static TextView point;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        global.point = 0;
        recyclerView = view.findViewById(R.id.recycler_view_thequestions);
        btnSubmitAnswer = view.findViewById(R.id.btnsubmitanswer);
        point = view.findViewById(R.id.tvglobalquestion);
        btnSubmitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestQuestion();
            }
        });
        return view;
    }


    public void requestQuestion() {
        if (global.questions.size() != global.attempt.size()) {
            Toast.makeText(getContext(), "Please select answer for all the question.", Toast.LENGTH_SHORT).show();
            return;
        }
        answers answers = new answers();
        answers.setAttempt(global.attempt);
        answers.setTotal(global.point);
        result_question result_question = new result_question();
        result_question.setAnswers(answers);
        result_question.setQuestions(global.questions);
        httpRequests httpRequests = global.getInstance().create(httpRequests.class);

        Call<Void> call = httpRequests.postAnswer(answers);
        try {
            Response<Void> response = call.execute();
            if (!response.isSuccessful()) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("answer", result_question);
                getParentFragmentManager().setFragmentResult("withObject_answers", bundle);
                fragmentResult fragmentResult = new fragmentResult();
                fragmentResult.setStyle(DialogFragment.STYLE_NORMAL, R.style.checkoutFragmentXY);
                fragmentResult.show(getParentFragmentManager(), "fragment_Result");

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}