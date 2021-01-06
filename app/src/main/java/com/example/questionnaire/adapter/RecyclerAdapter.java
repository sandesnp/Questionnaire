package com.example.questionnaire.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionnaire.R;
import com.example.questionnaire.fragments.innerfragments.fragmentQuestion;
import com.example.questionnaire.global.global;

import com.example.questionnaire.models.data;
import com.example.questionnaire.models.objectives;
import com.example.questionnaire.models.questions;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    Context mContext;
    ArrayList<data> listQuestion;
    FragmentManager fm;
    String inflateType;
    ArrayList<questions> listTheQuestion;
    private Map<String, String> Question_Set;

    public RecyclerAdapter(Context mContext, ArrayList<data> listQuestion, FragmentManager fm, String inflateType, ArrayList<questions> listTheQuestion) {
        this.mContext = mContext;
        this.listQuestion = listQuestion;
        this.fm = fm;
        this.inflateType = inflateType;
        this.listTheQuestion = listTheQuestion;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (inflateType) {
            case "questionset":
                view = LayoutInflater.from(mContext).inflate(R.layout.question_set, parent, false);
                break;
            case "thequestion":
                view = LayoutInflater.from(mContext).inflate(R.layout.thequestion, parent, false);
                break;
        }

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        switch (inflateType) {
            case "questionset":
                data questiondataset = listQuestion.get(position);
                holder.questions.setText(questiondataset.getName());
                holder.layout_question.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragmentQuestion fragmentQuestion = new fragmentQuestion();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("question", questiondataset);
                        fm.setFragmentResult("withObject_question", bundle);
                        fm.beginTransaction().replace(R.id.fragment_container, fragmentQuestion).
                                addToBackStack(null).detach(fragmentQuestion).attach(fragmentQuestion).
                                commit();
                    }
                });
                break;
            case "thequestion":
                questions question = listTheQuestion.get(position);
                Question_Set = new HashMap<>();
                for (objectives obj : question.getObjectives()) {
                    Question_Set.put(obj.getAnswer(), obj.getId());
                }
                holder.R_answerA.setText(Question_Set.keySet().toArray()[0].toString());
                holder.R_answerB.setText(Question_Set.keySet().toArray()[1].toString());
                holder.R_answerC.setText(Question_Set.keySet().toArray()[2].toString());
                holder.R_answerD.setText(Question_Set.keySet().toArray()[3].toString());
                holder.tvthequestion.setText(Jsoup.parse(question.getQuestion()).text());

                final int[] isCorrect = {0};
                holder.rganswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton RadioButton = (RadioButton) radioGroup.findViewById(i);
                        if (Question_Set.get(RadioButton.getText().toString()).equals(question.getCorrect_answer())) {
                            global.point = global.point + Integer.parseInt(question.getPoints());
                            isCorrect[0] = 1;
                        } else {
                            if (isCorrect[0] == 0) {
                                return;
                            }
                            global.point = global.point - Integer.parseInt(question.getPoints());
                            isCorrect[0] = 0;
                        }
                        fragmentQuestion.point.setText(global.point + "");
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (inflateType.equals("questionset")) {
            return listQuestion.size();
        }
        return listTheQuestion.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView questions;
        public LinearLayout layout_question;
        public RadioButton R_answerA, R_answerB, R_answerC, R_answerD;
        public TextView tvthequestion;
        public RadioGroup rganswer;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            questions = itemView.findViewById(R.id.tv_questions);
            layout_question = itemView.findViewById(R.id.layout_question);
            R_answerA = itemView.findViewById(R.id.rbanswerA);
            R_answerB = itemView.findViewById(R.id.rbanswerB);
            R_answerC = itemView.findViewById(R.id.rbanswerC);
            R_answerD = itemView.findViewById(R.id.rbanswerD);
            rganswer = itemView.findViewById(R.id.rganswer);
            tvthequestion = itemView.findViewById(R.id.tvthequestion);
        }
    }
}