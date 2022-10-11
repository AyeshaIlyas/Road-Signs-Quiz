package edu.sunyulster.roadsigns;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.sunyulster.roadsigns.databinding.ActivityMainBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends FragmentActivity implements QuestionFragment.QuestionListener {
    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;

    private final int QUESTION_COUNT = 3;
    private int currentQuestion;
    private int correct;
    private int wrong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // initialize instance variables
        currentQuestion = 1;
        correct = 0;
        wrong = 0;

        // add fragment
        QuestionFragment questionFragment = QuestionFragment.newInstance();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.add(R.id.fragmentContainer, questionFragment).commit();

        // update GUI with default values
        binding.questionsCount.setText(getResources().getString(R.string.questionsCount) + " " + currentQuestion + "/" + QUESTION_COUNT);
        binding.correctCount.setText(getResources().getString(R.string.correctCount) + " " + correct);
        binding.wrongCount.setText(getResources().getString(R.string.wrongCount) + " " + wrong);

        // set listener on submit btn
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.nextBtn.setEnabled(true);
                binding.submitBtn.setEnabled(false);
                QuestionFragment currentFragment = (QuestionFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
                if (currentFragment != null) {
                    if (currentFragment.isSelectionCorrect()) {
                        correct++;
                        binding.correctCount.setText(getResources().getString(R.string.correctCount) + " " + correct);
                        binding.feedback.setText(R.string.correctFeedback);
                    } else {
                        wrong++;
                        binding.wrongCount.setText(getResources().getString(R.string.wrongCount) + " " + wrong);
                        binding.feedback.setText(R.string.wrongFeedback);
                    }
                }
            }
        });

        // set listener on next btn
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestion == QUESTION_COUNT) {
                    Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                    intent.putExtra("correct", correct);
                    MainActivity.this.startActivity(intent);
                } else {
                    if (currentQuestion == QUESTION_COUNT - 1) {
                        binding.nextBtn.setText(R.string.finish);
                    }
                    // increment question number
                    currentQuestion++;
                    binding.questionsCount.setText(getResources().getString(R.string.questionsCount) + " " + currentQuestion + "/" + QUESTION_COUNT);
                    // clear feedback text
                    binding.feedback.setText("");
                    // disable buttons
                    binding.submitBtn.setEnabled(false);
                    binding.nextBtn.setEnabled(false);

                    // replace fragment
                    QuestionFragment newQuestionFragment = QuestionFragment.newInstance();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setReorderingAllowed(true);
                    transaction.replace(R.id.fragmentContainer, newQuestionFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        // disable button
        binding.submitBtn.setEnabled(false);
        binding.nextBtn.setEnabled(false);

        setContentView(binding.getRoot());
    }

    @Override
    public void onSelection() {
        binding.submitBtn.setEnabled(true);
    }
}
