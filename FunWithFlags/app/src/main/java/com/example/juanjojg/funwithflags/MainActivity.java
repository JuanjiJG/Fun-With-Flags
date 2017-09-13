package com.example.juanjojg.funwithflags;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.juanjojg.funwithflags.adapters.GridViewAnswerAdapter;
import com.example.juanjojg.funwithflags.adapters.GridViewSuggestAdapter;
import com.example.juanjojg.funwithflags.common.Common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public List<String> mSuggestSource = new ArrayList<>();

    public GridViewAnswerAdapter mAnswerAdapter;
    public GridViewSuggestAdapter mSuggestAdapter;

    public Button mBtnSubmit;

    public GridView mGridViewAnswer, mGridViewSuggest;

    public ImageView mImgViewQuestion;

    // List of images to use in the game
    int[] imageList = {
            R.drawable.spain,
            R.drawable.germany,
            R.drawable.france,
            R.drawable.italy,
            R.drawable.greece,
            R.drawable.japan,
            R.drawable.jamaica,
            R.drawable.norway,
            R.drawable.israel,
            R.drawable.australia,
            R.drawable.mexico,
            R.drawable.brazil,
            R.drawable.india,
            R.drawable.denmark,
            R.drawable.canada,
    };

    public char[] answer;

    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init the View
        initView();
    }

    private void initView() {
        mGridViewAnswer = (GridView) findViewById(R.id.grid_view_answer);
        mGridViewSuggest = (GridView) findViewById(R.id.grid_view_suggest);

        mImgViewQuestion = (ImageView) findViewById(R.id.img_country);

        // Add SetupList
        setupList();

        // Set listener for submit button
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                for (int i = 0; i < Common.user_submit_answer.length; i++) {
                    result += String.valueOf(Common.user_submit_answer[i]);
                }
                if (result.equals(correct_answer)) {
                    Toast.makeText(getApplicationContext(), "Well done! =)", Toast.LENGTH_SHORT).show();

                    // reset
                    Common.count = 0;
                    Common.user_submit_answer = new char[correct_answer.length()];

                    // Set Adapter
                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupNullList(), getApplicationContext());
                    mGridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(mSuggestSource, getApplicationContext(), MainActivity.this);
                    mGridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setupList();
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect! =(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupList() {
        // Random Country Flag
        Random random = new Random();
        int imageSelected = imageList[random.nextInt(imageList.length)];
        mImgViewQuestion.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/") + 1);

        answer = correct_answer.toCharArray();

        Common.user_submit_answer = new char[answer.length];

        // Add answer charater to list
        mSuggestSource.clear();
        for (char item : answer) {
            // Add country flag name to list
            mSuggestSource.add(String.valueOf(item));
        }

        // Randomly add some character to list
        for (int i = answer.length; i < answer.length * 2; i++) {
            mSuggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);
        }

        // Sort random
        Collections.shuffle(mSuggestSource);

        // Set for GridView
        mAnswerAdapter = new GridViewAnswerAdapter(setupNullList(), this);
        mSuggestAdapter = new GridViewSuggestAdapter(mSuggestSource, this, this);

        mAnswerAdapter.notifyDataSetChanged();
        mSuggestAdapter.notifyDataSetChanged();

        mGridViewSuggest.setAdapter(mSuggestAdapter);
        mGridViewAnswer.setAdapter(mAnswerAdapter);
    }

    private char[] setupNullList() {
        char result[] = new char[answer.length];
        for (int i = 0; i < answer.length; i++) {
            result[i] = ' ';
        }
        return result;
    }
}
