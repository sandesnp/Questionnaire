package com.example.questionnaire;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.questionnaire.global.global;
import com.example.questionnaire.models.user;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;

public class MainActivity extends AppCompatActivity {

    private EditText etusername, etpassword;
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etpassword);

        btnLogin = findViewById(R.id.btnlogin);

        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(new CornerTreatment()).setAllCornerSizes(50) //bottom left edge is made 90 degree |||Note: This is executed first before cutCorner
                .build();
        btnLogin.setShapeAppearanceModel(shapeAppearanceModel);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

    }

    public void Login() {
        String username, password;
        username = etusername.getText().toString();
        password = etpassword.getText().toString();
        if (username.equals("admin") && password.equals("admin")) {
            user user = new user();
            user.setUserid(1);
            user.setEmail("admin@yahoo.com");
            user.setPassword("admin");
            global.user = user;
            startActivity(new Intent(this, DashboardActivity.class));
        }
    }

    public void onRegisterClick(View View) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}