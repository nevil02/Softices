package com.softices.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.softices.PrefManager;
import com.softices.databinding.ActNumberInputBinding;
import com.softices.models.User;

public class NumberActivity extends AppCompatActivity {

    ActNumberInputBinding binding;
    PrefManager prefManager;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActNumberInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefManager = new PrefManager(this);

        User user = new Gson().fromJson(prefManager.getString("user"), User.class);
        binding.userDetails.username.setText("Username: " + user.getUsername());

        binding.next.setOnClickListener(view -> {
            Intent intent = new Intent(this, SelectionActivity.class);
            try {
                intent.putExtra("number", Integer.parseInt(binding.number.getText().toString()));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Please put a valid decimal (Integer) number!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
