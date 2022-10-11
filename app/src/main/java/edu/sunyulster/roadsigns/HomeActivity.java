package edu.sunyulster.roadsigns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.sunyulster.roadsigns.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = binding.input.getText().toString();
                int questionCount = input.length() == 0 ?
                        Integer.parseInt(getResources().getString(R.string.defaultQuestionCount))
                        : Integer.parseInt(input);
                Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                intent.putExtra("questionCount", questionCount);
                HomeActivity.this.startActivity(intent);
            }
        });
        setContentView(binding.getRoot());
    }
}