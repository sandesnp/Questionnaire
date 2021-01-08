package com.example.questionnaire.apiInterface;

import com.example.questionnaire.models.answers;
import com.example.questionnaire.models.faq;
import com.example.questionnaire.models.set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface httpRequests {
    @GET("/api/set")
    Call<set> getSet();

    @GET("/api/faq")
    Call<faq> getFaq();

    @POST("/api/test")
    Call<Void> postAnswer(@Body answers answers);
}
