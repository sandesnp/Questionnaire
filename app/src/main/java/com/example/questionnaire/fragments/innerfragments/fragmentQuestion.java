package com.example.questionnaire.fragments.innerfragments;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.questionnaire.R;
import com.example.questionnaire.adapter.RecyclerAdapter;
import com.example.questionnaire.models.data;
import com.example.questionnaire.models.questions;
import java.util.ArrayList;


public class fragmentQuestion extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("withObject_question", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                final data result = (data) bundle.getSerializable("question");
                questionlist = result.getQuestions();
                if (result != null) {
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
    public static TextView point;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_thequestions);
        point = view.findViewById(R.id.tvglobalquestion);
        return view;
    }

}