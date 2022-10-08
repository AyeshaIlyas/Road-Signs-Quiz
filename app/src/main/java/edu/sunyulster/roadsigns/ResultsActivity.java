package edu.sunyulster.roadsigns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.sunyulster.roadsigns.databinding.ActivityResultsBinding;

public class ResultsActivity extends AppCompatActivity {

    ActivityResultsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultsBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        int correct = intent.getIntExtra("correct" , 0);
        binding.results.setText(getString(R.string.correctCount) + correct);
        setContentView(binding.getRoot());
    }
}