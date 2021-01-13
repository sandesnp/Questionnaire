package com.example.questionnaire.classes;

import android.widget.Toast;

import com.example.questionnaire.apiInterface.httpRequests;
import com.example.questionnaire.global.global;
import com.example.questionnaire.models.user;
import com.example.questionnaire.response.responseUser;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.questionnaire.RegisterActivity.contextMainActivity;

public class userRegister {
    com.example.questionnaire.models.user user;

    public userRegister(com.example.questionnaire.models.user user) {
        this.user = user;
    }

    public boolean isSingedUp(){
        httpRequests httpRequests = global.getInstanceTest().create(httpRequests.class);
        Call<responseUser> userCall = httpRequests.userRegister(user);
        try {
            Response<responseUser> userResponse = userCall.execute();
            if (userResponse.isSuccessful()) {
                //check if it works
                global.token += userResponse.body().getToken();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(contextMainActivity, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
