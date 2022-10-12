package edu.sunyulster.roadsigns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

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
                Log.i("HomeActivity.java", "Start button clicked");
                String input = binding.input.getText().toString();
                int questionCount;
                if (input.strip().length() == 0 || Integer.parseInt(input) <= 0) 
                    questionCount = Integer.parseInt(getResources().getString(R.string.defaultQuestionCount));
                else 
                    questionCount = Integer.parseInt(input);
                Log.i("HomeActivity.java", "questionCount: " + questionCount);
                Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
                intent.putExtra("questionCount", questionCount);
                Log.i("HomeActivity.java", "Intent created...Starting new activity");
                HomeActivity.this.startActivity(intent);
            }
        });
        setContentView(binding.getRoot());
        Log.i("HomeActivity.java", "Layout Inflated");  
    }
}
