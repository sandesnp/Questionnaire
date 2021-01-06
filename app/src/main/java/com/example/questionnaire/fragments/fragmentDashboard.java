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
import com.example.questionnaire.adapter.RecyclerAdapter;
import com.example.questionnaire.apiInterface.httpRequests;
import com.example.questionnaire.global.global;
import com.example.questionnaire.models.data;
import com.example.questionnaire.models.set;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class fragmentDashboard extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_question);

        httpRequests httpRequests = global.getInstance().create(httpRequests.class);
        Call<set> setCall = httpRequests.getSet();
        try {
            Response<set> setResponse = setCall.execute();
            if (setResponse.isSuccessful()) {
                ArrayList<data> questiondataset= setResponse.body().getData();
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), questiondataset, getParentFragmentManager(), "questionset", null);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        return view;
    }
}