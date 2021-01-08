package com.example.questionnaire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionnaire.R;
import com.example.questionnaire.models.qa;

import org.jsoup.Jsoup;

import java.util.ArrayList;

public class QaAdapter extends RecyclerView.Adapter<QaAdapter.QaViewHolder> {

    Context mContext;
    ArrayList<qa> qaList;

    public QaAdapter(Context mContext, ArrayList<qa> qaList) {
        this.mContext = mContext;
        this.qaList = qaList;
    }

    @NonNull
    @Override
    public QaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.qa_answer, parent, false);
        return new QaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QaViewHolder holder, int position) {
        qa qa = qaList.get(position);
        holder.tvQuestion.setText(Jsoup.parse(qa.getQuestion()).text());
        holder.tvAnswer.setText(qa.getAnswer());
        holder.tvCorrectAnswer.setText(qa.getCorrect_answer());
    }

    @Override
    public int getItemCount() {
        return qaList.size();
    }

    public class QaViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion, tvAnswer, tvCorrectAnswer;

        public QaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvqaquestion);
            tvAnswer = itemView.findViewById(R.id.tvqaanswerans);
            tvCorrectAnswer = itemView.findViewById(R.id.tvqacorrectanswerans);
        }
    }

}
