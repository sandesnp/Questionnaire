package com.example.questionnaire;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.questionnaire.apiInterface.httpRequests;
import com.example.questionnaire.global.global;
import com.example.questionnaire.models.user;
import com.example.questionnaire.response.responseImage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    MaterialButton btnRegister;
    EditText etEmail, etPassword, etRePassword;
    CircleImageView CivRImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        grantPermission();
        signInButton = findViewById(R.id.sign_in_button);
        etEmail = findViewById(R.id.etremail);
        etPassword = findViewById(R.id.etrpassword);
        etRePassword = findViewById(R.id.etrrepassword);
        CivRImage = findViewById(R.id.circleimagerimage);
        btnRegister = findViewById(R.id.btnregister);
        signInButton.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        CivRImage.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
        someActivityResultLauncher.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        handleSignInResult(task);

                    }
                }
            });

    String imagePath;

    //-------Image selection and post request and retrieving imagePath after post request.
    public void postImage(String imagePath) {
        String imageName = "";
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        httpRequests httpRequests = global.getInstance().create(httpRequests.class);
        Call<responseImage> responseImageCall = httpRequests.uploadImage(body);
        try {
            Response<responseImage> imageResponse = responseImageCall.execute();
            imageName = imageResponse.body().getFilename();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    ActivityResultLauncher<Intent> imageSelectionCallBack = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        imagePath = getImagePath(uri);
                        CivRImage.setImageURI(uri);
                    }
                }
            }
    );

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imageSelectionCallBack.launch(intent);
    }


    //-----------Google Sing in
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("E", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    public void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            user user = new user();
            user.setUserid(1);
            user.setEmail(account.getEmail());
            user.setProfile_image(account.getPhotoUrl().toString());
            global.user=user;
            startActivity(new Intent(this, DashboardActivity.class));
            Toast.makeText(this, account.getDisplayName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.circleimagerimage:
                selectImage();
                break;
            case R.id.btnregister:
                validationCheck();
                break;
        }
    }


    //----------Permission Request to Read External Storage

    int External_Storage, Storage_Permission_Code = 1;

    public void grantPermission() {
        External_Storage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (External_Storage == PackageManager.PERMISSION_GRANTED) {
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Storage_Permission_Code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Storage_Permission_Code && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "This is required for Profile Image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //---------------------Validation Check

    private boolean isValidEmailId(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    public void validationCheck() {
        if (isValidEmailId(etEmail.getText().toString().trim())) {
            etEmail.setError("Please Enter Correct form of email.");
            etEmail.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etPassword.getText())) {
            etPassword.setError("Please enter password.");
            etPassword.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etRePassword.getText())) {
            etRePassword.setError("Please Re-enter password.");
            etRePassword.requestFocus();
            return;
        } else if (!etPassword.equals(etRePassword)) {
            Toast.makeText(this, "Password doesn't match.", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Assumption: Logged In", Toast.LENGTH_SHORT).show();
    }

    //------------------------Animation for onClick
    public void onLoginClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
    }

}