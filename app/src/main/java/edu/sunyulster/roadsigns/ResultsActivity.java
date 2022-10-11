package edu.sunyulster.roadsigns;

import androidx.appcompat.app.AppCompatActivity;
import edu.sunyulster.roadsigns.databinding.ActivityResultsBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ResultsActivity extends AppCompatActivity {

    ActivityResultsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultsBinding.inflate(getLayoutInflater());

        Intent intent = getIntent();
        int correct = intent.getIntExtra("correct" , 0);
        int wrong = intent.getIntExtra("wrong", 0);
        int total = correct + wrong;
        binding.score.setText("Score: " + (correct / total) + "%");
        binding.correct.setText(String.format("Correct: %d/%d", correct, total));
        binding.wrong.setText(String.format("Wrong: %d/%d", wrong, total));

        // listener on home btn to go back to home page
        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultsActivity.this, HomeActivity.class);
                ResultsActivity.this.startActivity(intent);
            }
        });

        setContentView(binding.getRoot());
    }
}