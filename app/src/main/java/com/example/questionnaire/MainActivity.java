package com.example.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etusername, etpassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etpassword);
        btnLogin = findViewById(R.id.btnlogin);
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
            startActivity(new Intent(this, DashboardActivity.class));
            Toast.makeText(this, "Succeeded", Toast.LENGTH_SHORT).show();
        }
    }
}