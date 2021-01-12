package com.example.questionnaire.global;

import com.example.questionnaire.models.answers;
import com.example.questionnaire.models.attempt;
import com.example.questionnaire.models.data;
import com.example.questionnaire.models.questions;
import com.example.questionnaire.models.user;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class global {

    public static int point=0;
    public static final String Base_URL = "http://license.techcoderznepal.com/";
    public static String token = "Bearer ";
    public static user user;
    public static ArrayList<questions> questions=new ArrayList<>();
    public static ArrayList<attempt> attempt= new ArrayList<>();
    public static Retrofit getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy =
                new android.os.StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }
}
