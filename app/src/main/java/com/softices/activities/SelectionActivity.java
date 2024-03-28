package com.softices.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.softices.PrefManager;
import com.softices.databinding.ActSelectionBinding;
import com.softices.models.User;

import java.util.Random;

public class SelectionActivity extends AppCompatActivity {
    ActSelectionBinding binding;
    PrefManager prefManager;

    TextView[][] matrix;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefManager = new PrefManager(this);
        User user = new Gson().fromJson(prefManager.getString("user"), User.class);
        binding.userDetails.username.setText("Username: " + user.getUsername());

        Intent intent = getIntent();

        int number = intent.getIntExtra("number", 4);

        matrix = new TextView[number][number];

        LinearLayout mainContainer = new LinearLayout(this);
        mainContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mainContainer.setOrientation(LinearLayout.VERTICAL);

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
        horizontalScrollView.addView(mainContainer);

        for (int i = 0; i < number; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < number; j++) {
                matrix[i][j] = new TextView(this);
                matrix[i][j].setPadding(5, 5, 5, 5);
                matrix[i][j].setLayoutParams(new ViewGroup.LayoutParams(100, 50));
                matrix[i][j].setGravity(1);
                matrix[i][j].setBackgroundColor(Color.GRAY);
                matrix[i][j].setTextColor(Color.WHITE);

                matrix[i][j].setOnClickListener(view -> {
                    recoverRed();
                    view.setBackgroundColor(Color.BLUE);
                    view.setClickable(false);
                    ((TextView) view).setText("B");
                    setRed(number);
                });
                matrix[i][j].setText("A");
                row.addView(matrix[i][j]);
            }
            mainContainer.addView(row);
        }

        binding.selectLayoutContainer.addView(horizontalScrollView);
    }

    int prevRow = -1;
    int prevCol = -1;

    private void setRed(int number) {
        int row = new Random().nextInt(number);
        int col = new Random().nextInt(number);

        if (matrix[row][col].getText().toString().equalsIgnoreCase("R") ||
                matrix[row][col].getText().toString().equalsIgnoreCase("B"))
            setRed(number);
        else {
            prevRow = row;
            prevCol = col;
            matrix[row][col].setText("R");
            matrix[row][col].setBackgroundColor(Color.RED);
            matrix[row][col].setClickable(false);
        }
    }

    private void recoverRed() {
        if (prevCol == -1 || prevRow == -1)
            return;
        matrix[prevRow][prevCol].setBackgroundColor(Color.GRAY);
        matrix[prevRow][prevCol].setClickable(true);
        matrix[prevRow][prevCol].setText("A");
    }
}
