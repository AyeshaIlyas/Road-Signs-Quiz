package edu.sunyulster.roadsigns;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.sunyulster.roadsigns.databinding.FragmentQuestionBinding;

public class QuestionFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    private FragmentQuestionBinding binding;

    private static final int NUMBER_OF_SIGNS = 3;
    private static final int NUMBER_OF_CHOICES = 4;
    private static final String PACKAGE_NAME = "edu.sunyulster.roadsigns";

    private int signId;
    private int correctAnswerId;
    private QuestionListener parentActivity;

    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    public interface QuestionListener {
        void onSelection();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            parentActivity = (QuestionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.getClass().getName() + " must implement QuestionListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("QuestionFragment.java", "starting onCreateView");
        // Inflate the layout for this fragment
        binding =  FragmentQuestionBinding.inflate(inflater, container, false);

        // pick a random number
        Random randomNumGenerator = new Random();
        int randomNumber = randomNumGenerator.nextInt(NUMBER_OF_SIGNS) + 1;
        // get random sign and set in image view
        signId = getResources().getIdentifier("sign" + randomNumber, "drawable", PACKAGE_NAME);
        binding.imageView.setImageResource(signId);
        // get corresponding answer
        correctAnswerId = getResources().getIdentifier("answer" + randomNumber, "string", PACKAGE_NAME);

        Log.i("QuestionFragment.java", "correctAnswer: " + getResources().getString(correctAnswerId));
        
        // get 3 other random answers that are different from each other
        ArrayList<Integer> answerChoicesIds = new ArrayList<>();
        answerChoicesIds.add(correctAnswerId);
        for (int i = 1; i < NUMBER_OF_CHOICES; i++) {
            Log.i("QuestionFragment.java", i + " iteration of the loop");
            randomNumber = randomNumGenerator.nextInt(NUMBER_OF_SIGNS) + 1;
            int id = getResources().getIdentifier("answer" + randomNumber, "string", PACKAGE_NAME);
            Log.i("QuestionFragment.java", "randomNumber generated: " + randomNumber + " AND " + "id: " + id);
            while (answerChoicesIds.contains(id)) {
                randomNumber = randomNumGenerator.nextInt(NUMBER_OF_SIGNS) + 1;
                id = getResources().getIdentifier("answer" + randomNumber, "string", PACKAGE_NAME);
                Log.i("QuestionFragment.java", "randomNumber generated: " + randomNumber + " AND " + "id: " + id);
            }

            Log.i("QuestionFragment.java", "Final ID" i + ": " + String.valueOf(id));

            // at this point we have resource id for a unique answer choice
            answerChoicesIds.add(id);
        }

        // shuffle choices
        Collections.shuffle(answerChoicesIds);

        Log.i("QuestionFragment.java", answerChoicesIds.toString());

        // assign an answer to each radio button
        for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
            ((RadioButton) binding.radioGroup.getChildAt(i)).setText(answerChoicesIds.get(i));
        }

        // assign an answer to each radio button
        for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
            Log.i("QuestionFragment.java", ((RadioButton) binding.radioGroup.getChildAt(i)).getText().toString());
        }

        // set listener on radioGroup
        binding.radioGroup.setOnCheckedChangeListener(this);

        return binding.getRoot();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        // invoke callback
        parentActivity.onSelection();
    }


    public boolean isSelectionCorrect() {
        // if a RadioButton is checked
        int checkedId = binding.radioGroup.getCheckedRadioButtonId();
        if (checkedId != -1) {
            // get the RadioButton object that is checked
            for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
                RadioButton button = (RadioButton) binding.radioGroup.getChildAt(i);
                if (button.getId() == checkedId) {
                    // check if the RadioButton's text is the same as the answerText and return true
                    return button.getText().toString().equals(getResources().getString(correctAnswerId));
                }
            }
        }
        // if no RadioButton is checked, return false
        return false;
    }

    public int getSignId() {
        // image id, correct answer id, chosen answer id
        return signId;
    }

    public String getCorrectAnswer() {
        return getResources().getString(correctAnswerId);
    }

    public String getChosenAnswer() {
        int checkedId = binding.radioGroup.getCheckedRadioButtonId();
        if (checkedId != -1)
            return ((RadioButton) getActivity().findViewById(checkedId)).getText().toString();
        return null;
    }



}
