package com.example.questionnaire.apiInterface;

import com.example.questionnaire.models.answers;
import com.example.questionnaire.models.faq;
import com.example.questionnaire.models.result;
import com.example.questionnaire.models.set;
import com.example.questionnaire.models.user;
import com.example.questionnaire.response.responseImage;
import com.example.questionnaire.response.responseUser;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface httpRequests {
    @GET("/api/set")
    Call<set> getSet();

    @GET("/api/faq")
    Call<faq> getFaq();

    @POST("/api/test")
    Call<Void> postAnswer(@Body result result);

    @Multipart
    @POST("upload")
    Call<responseImage> uploadImage(@Part MultipartBody.Part img);

    @POST("api/register")
    Call<responseUser> userRegister(@Body user user);

    @FormUrlEncoded
    @POST("api/login")
    Call<responseUser> userLogin(@Field("email") String email, @Field("password") String password);
}
