package com.example.questionnaire.global;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class global {

    public static int point=0;
    public static final String Base_URL = "http://license.techcoderznepal.com/";
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
