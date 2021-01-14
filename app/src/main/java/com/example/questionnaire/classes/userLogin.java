package com.example.questionnaire.classes;

import android.widget.Toast;

import com.example.questionnaire.apiInterface.httpRequests;
import com.example.questionnaire.global.global;
import com.example.questionnaire.response.responseUser;

import retrofit2.Call;
import retrofit2.Response;

public class userLogin {

    private String email, password;

    public userLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isLoggedIn() {
        httpRequests httpRequests = global.getInstanceTest().create(httpRequests.class);
        Call<responseUser> userCall = httpRequests.userLogin(email, password);
        try {
            Response<responseUser> response = userCall.execute();
            if (response.isSuccessful()) {
                global.token += response.body().getToken();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
