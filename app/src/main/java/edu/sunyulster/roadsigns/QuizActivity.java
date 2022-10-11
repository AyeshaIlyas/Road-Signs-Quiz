package edu.sunyulster.roadsigns;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.sunyulster.roadsigns.databinding.ActivityQuizBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import java.util.LinkedList;

public class QuizActivity extends FragmentActivity implements QuestionFragment.QuestionListener {
    private ActivityQuizBinding binding;
    private FragmentManager fragmentManager;

    private int questionCount;
    private int currentQuestion;
    private int correct;
    private int wrong;

    private LinkedList<Integer> wrongSignsIds;
    private LinkedList<String> correctAnswers;
    private LinkedList<String> chosenAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("QuizActivity.java", "onCreate just started executing");
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        // initialize instance variables
        questionCount = getIntent().getIntExtra("questionCount", 5);
        Log.i("QuizActivity.java", "Getting extras...Extra: " + questionCount);
        currentQuestion = 1;
        correct = 0;
        wrong = 0;

        wrongSignsIds = new LinkedList<>();
        correctAnswers = new LinkedList<>();
        chosenAnswers = new LinkedList<>();

        // add fragment
        QuestionFragment questionFragment = QuestionFragment.newInstance();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.add(R.id.fragmentContainer, questionFragment).commit();

        // update GUI with default values
        binding.questionsCount.setText(getResources().getString(R.string.questionsCount) + " " + currentQuestion + "/" + questionCount);
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
                        // store question information
                        Log.i("QuizActivity.java", "Before adding to outer class's LinkedLists");
                        QuizActivity.this.wrongSignsIds.add(currentFragment.getSignId());
                        QuizActivity.this.correctAnswers.add(currentFragment.getCorrectAnswer());
                        // chosen answer cannot be null at this point bc we know an answer has to have been chosen
                        // to activate the submit button
                        QuizActivity.this.chosenAnswers.add(currentFragment.getChosenAnswer());
                        Log.i("QuizActivity.java", "After adding to outer class's LinkedLists");
                    }
                }
            }
        });

        // set listener on next btn
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestion == questionCount) {
                    Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
                    intent.putExtra("correct", correct);
                    intent.putExtra("wrong", wrong);
                    QuizActivity.this.startActivity(intent);
                } else {
                    if (currentQuestion == questionCount - 1) {
                        binding.nextBtn.setText(R.string.finish);
                    }
                    // increment question number
                    currentQuestion++;
                    binding.questionsCount.setText(getResources().getString(R.string.questionsCount) + " " + currentQuestion + "/" + questionCount);
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
        Log.i("QuizActivity.java", "End of onCreate");
    }

    @Override
    public void onSelection() {
        binding.submitBtn.setEnabled(true);
    }
}
