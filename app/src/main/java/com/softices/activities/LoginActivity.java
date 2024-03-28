package com.softices.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.softices.PrefManager;
import com.softices.Utils;
import com.softices.api.APIClient;
import com.softices.api.Response;
import com.softices.databinding.ActLoginBinding;
import com.softices.models.User;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    ActLoginBinding binding;
    PrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefManager = new PrefManager(this);

        binding.username.setText("atuny0");
        binding.password.setText("9uQFF1Lh");

        Gson gson = new Gson();

        binding.login.setOnClickListener(view -> {

            if (TextUtils.isEmpty(binding.email.getText()) || !Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()) {
                Toast.makeText(this, TextUtils.isEmpty(binding.email.getText()) +"Invalid Email "+!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches(), Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(binding.username.getText())){
                Toast.makeText(this, "Please enter Username", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(binding.password.getText())){
                Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!Utils.isPasswordValid(binding.password.getText().toString())){
                Toast.makeText(this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                return;
            }

            HashMap<String, String> body = new HashMap<>();
            body.put("username", binding.username.getText().toString());
            body.put("password", binding.password.getText().toString());

            Response response = APIClient.getClient("https://dummyjson.com/auth/").create(Response.class);

            Call<User> call = response.login(body);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            prefManager.setString("user", gson.toJson(response.body()));
                            prefManager.setString("token", response.body().getToken());
                            startActivity(new Intent(LoginActivity.this, NumberActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Login error!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Could not get user details!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("testing", "else " + t.getMessage());
                }
            });
        });

        binding.skip.setOnClickListener(view -> {
            User user = new User("", "guest", "guest@email.com", "Guest", "", "male", null, "token");
            prefManager.setString("user", gson.toJson(user));
            prefManager.setString("token", "");
            startActivity(new Intent(LoginActivity.this, NumberActivity.class));
        });
    }
}
