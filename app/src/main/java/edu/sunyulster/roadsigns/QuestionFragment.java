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

    private String answerText;
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
        // Inflate the layout for this fragment
        binding =  FragmentQuestionBinding.inflate(inflater, container, false);

        // pick a random number
        Random randomNumGenerator = new Random();
        int randomNumber = randomNumGenerator.nextInt(NUMBER_OF_SIGNS) + 1;
        // get random sign and set in image view
        int signID = getResources().getIdentifier("sign" + randomNumber, "drawable", PACKAGE_NAME);
        binding.imageView.setImageResource(signID);
        // get corresponding answer
        int correctAnswerId = getResources().getIdentifier("answer" + randomNumber, "string", PACKAGE_NAME);
        answerText = getResources().getString(correctAnswerId);

        // get 3 other random answers that are different from each other
        ArrayList<Integer> answerChoicesIds = new ArrayList<>();
        answerChoicesIds.add(correctAnswerId);
        for (int i = 1; i < NUMBER_OF_CHOICES; i++) {
            randomNumber = randomNumGenerator.nextInt(NUMBER_OF_SIGNS) + 1;
            while (answerChoicesIds.contains(randomNumber)) {
                randomNumber = randomNumGenerator.nextInt(NUMBER_OF_SIGNS) + 1;
            }

            Log.i("QuestionFragment.java", String.valueOf(randomNumber));

            // at this point we have a unique random number and we can get resource id for the answer
            answerChoicesIds.add(getResources().getIdentifier("answer" + randomNumber, "string", PACKAGE_NAME));
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
                    return button.getText().toString().equals(answerText);
                }
            }
        }
        // if no RadioButton is checked, return false
        return false;
    }
}