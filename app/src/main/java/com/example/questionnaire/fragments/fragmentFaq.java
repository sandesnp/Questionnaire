package com.example.questionnaire.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.questionnaire.R;
import com.example.questionnaire.adapter.FaqAdapter;
import com.example.questionnaire.adapter.RecyclerAdapter;
import com.example.questionnaire.apiInterface.httpRequests;
import com.example.questionnaire.global.global;
import com.example.questionnaire.models.faq;
import com.example.questionnaire.models.faqdata;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class fragmentFaq extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_faq);
        httpRequests httpRequests = global.getInstance().create(com.example.questionnaire.apiInterface.httpRequests.class);
        Call<faq> faqCall = httpRequests.getFaq();
        try {
            Response<faq> faqResponse = faqCall.execute();
            if (faqResponse.isSuccessful()) {
                ArrayList<faqdata> faqlist = faqResponse.body().getData();
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                FaqAdapter faqAdapter = new FaqAdapter(faqlist, getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(faqAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}