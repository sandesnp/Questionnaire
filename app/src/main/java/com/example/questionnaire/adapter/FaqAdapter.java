package com.example.questionnaire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionnaire.R;
import com.example.questionnaire.models.faqdata;

import org.jsoup.Jsoup;

import java.util.ArrayList;

import static org.jsoup.nodes.Document.OutputSettings.Syntax.html;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    ArrayList<faqdata> faqdataArrayList;
    Context mcontext;

    public FaqAdapter(ArrayList<faqdata> faqdataArrayList, Context mcontext) {
        this.faqdataArrayList = faqdataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.faq_question, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        faqdata faqdata = faqdataArrayList.get(position);
        holder.faq_question.setText(faqdata.getQuestion());
        holder.faq_answer.setText(Jsoup.parse(faqdata.getAnswer()).text());

//        String noHTMLString = htmlString.replaceAll("\\<.*?>","");
    }

    @Override
    public int getItemCount() {
        return faqdataArrayList.size();
    }

    public class FaqViewHolder extends RecyclerView.ViewHolder {
        TextView faq_question, faq_answer;

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            faq_question = itemView.findViewById(R.id.tvfaqquestion);
            faq_answer = itemView.findViewById(R.id.tvfaqanswer);
        }
    }
}
