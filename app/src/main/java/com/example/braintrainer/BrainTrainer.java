package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class BrainTrainer extends AppCompatActivity {
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button playAgainButton;
    int locationOfCorrectAnswer;
    TextView currectTextView;
    TextView resultTextView;
    TextView timeTextView;
    TextView messageTextView;
    int score = 0;
    int numberOfQuestions = 0;
    MediaPlayer endingAudio;
    boolean doubleBackToExitPressedOnce = false;

    public void generateContent() {
        TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        Random random = new Random();
        int a = random.nextInt(21);
        int b = random.nextInt(21);

        questionTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        ArrayList<Integer> answers = new ArrayList<Integer>();
        locationOfCorrectAnswer = random.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (locationOfCorrectAnswer == i) {
                answers.add(a + b);
            } else {
                int wrongAnswer = random.nextInt(42);
                while (wrongAnswer == a + b) {
                    wrongAnswer = random.nextInt(42);
                }
                answers.add(wrongAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
        resultTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brain_trainer);

        generateContent();
        startCountDown();
    }

    public void checkCurrectAnswer(View view) {
        currectTextView = (TextView) findViewById(R.id.currectTextView);
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag())) {
            currectTextView.setVisibility(View.VISIBLE);
            currectTextView.setText("Correct");
            currectTextView.setTextColor(getResources().getColor(R.color.colorGreen));
            generateContent();
            score ++;
        } else {
            currectTextView.setVisibility(View.VISIBLE);
            currectTextView.setText("Wrong");
            currectTextView.setTextColor(getResources().getColor(R.color.colorRed));
            generateContent();
        }
        numberOfQuestions ++;
        resultTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
    }

    public void startCountDown() {
        new CountDownTimer(31000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeTextView = (TextView) findViewById(R.id.timeTextView);
                timeTextView.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                playAgainButton = (Button) findViewById(R.id.playAgainButton);
                endingAudio = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                endingAudio.start();
                currectTextView.setText("Times Up!");
                currectTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                playAgainButton.setVisibility(View.VISIBLE);
                messageTextView = findViewById(R.id.messageTextView);
                messageTextView.setVisibility(View.VISIBLE);
                if (score < 15) {
                    messageTextView.setText("Not Bad! You scored " + score + " out of " + numberOfQuestions +" questions.");
                } else if (score == 15) {
                    messageTextView.setText("Excellent! You scored " + score + " out of " + numberOfQuestions +" questions.");
                } else {
                    messageTextView.setText("Very Good! You scored " + score + " out of " + numberOfQuestions +" questions.");
                }
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
            }
        }.start();
    }

    public void playAgain(View view) {
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        currectTextView.setText("");
        messageTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        score = 0;
        numberOfQuestions = 0;
        generateContent();
        startCountDown();
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            endingAudio.stop();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
