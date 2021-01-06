package com.example.questionnaire.apiInterface;

import com.example.questionnaire.models.faq;
import com.example.questionnaire.models.set;

import retrofit2.Call;
import retrofit2.http.GET;

public interface httpRequests {
    @GET("/api/set")
    Call<set> getSet();

    @GET("/api/faq")
    Call<faq> getFaq();
}
