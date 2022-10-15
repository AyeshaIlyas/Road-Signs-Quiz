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
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = binding.input.getText().toString();
                int questionCount;
                if (input.trim().length() == 0 || Integer.parseInt(input) <= 0) 
                    questionCount = Integer.parseInt(getResources().getString(R.string.defaultQuestionCount));
                else 
                    questionCount = Integer.parseInt(input);
                Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                intent.putExtra("questionCount", questionCount);
                HomeActivity.this.startActivity(intent);
            }
        });
    }
}
