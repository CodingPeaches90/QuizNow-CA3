package com.appone.jordan.quiznow;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appone.jordan.quiznow.questionManager.DatabaseHelper;
import com.appone.jordan.quiznow.questionManager.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.appone.jordan.quiznow.R;
import com.danimahardhika.cafebar.CafeBar;


public class QuestionActivity extends AppCompatActivity {

    List<Question> quesList;
    Question curQ;
    TextView txtQuestion;
    Button butNext;
    int score=0;
    int qid=0;

    Dialog d;

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;

    /* Text View for The Dialog*/

    TextView userTotalCorrect;
    TextView questionSize;
    Button resultClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        d = new Dialog(this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);

        View dView = getLayoutInflater().inflate(R.layout.result_view, null);

        userTotalCorrect = dView.findViewById(R.id.userNumberCorrect);

        questionSize = dView.findViewById(R.id.quizTotalQuestions);

        resultClose = dView.findViewById(R.id.close_results);

        d.setContentView(dView);

        DatabaseHelper db=new DatabaseHelper(this);

        quesList = db.getAllQuestions();

        txtQuestion = findViewById(R.id.quiz_question);

        curQ = quesList.get(qid);

        butNext= findViewById(R.id.nextQuestion);

        setQuestion();

        getSupportActionBar().setTitle(R.string.ai_desc_title);

        mVoiceInputTv = findViewById(R.id.textView);
        mSpeakBtn = findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(v -> startVoiceInput());

        resultClose.setOnClickListener(v -> {
            /*Close inflate View*/
            d.dismiss();

            /* Switch Activity*/
            startActivity(new Intent(this, HomeActivity.class));
            finish();

        });

        butNext.setOnClickListener(v -> {

            /*grab the text view with the answer*/

            String answer = (String) mVoiceInputTv.getText();

            String aAnswer = answer.toLowerCase();

            mVoiceInputTv.setText("");

            Log.d("ans", curQ.getAnswer()+" "+aAnswer);

            //String test = "artificial intelligence";

            Log.d("next question here", "Your score"+score);

            String curQAns = curQ.getAnswer();

            String curqanswer = curQAns.toLowerCase();

            if(curqanswer.equals(aAnswer))
            {
                score++;

                Log.d("score", "Your score "+score);

                CafeBar.builder(QuestionActivity.this)
                        .customView(R.layout.correct_toast_question)
                        .floating(true)
                        .show();

                if(qid < 4){
                    Log.d("next question here", "Your score"+score);

                    curQ=quesList.get(qid);
                    setQuestion();

                }else{
                    /* Show inflated Results View */
                    /* Set up the scoring */

                    userTotalCorrect.setText(String.valueOf(score));
                    questionSize.setText(String.valueOf(quesList.size()));

                    d.show();
                }

            }else{
                CafeBar.builder(QuestionActivity.this)
                        .customView(R.layout.wrong_toast_question)
                        .floating(true)
                        .show();

                if(qid < 4){
                    Log.d("next question here", "Your score"+score);

                    curQ=quesList.get(qid);
                    setQuestion();

                }else{

                    /* Set up the scoring */
                    userTotalCorrect.setText(String.valueOf(score));
                    questionSize.setText(String.valueOf(quesList.size()));

                    d.show();
                }
            }
        });
    }

    private void setQuestion() {
        txtQuestion.setText(curQ.getQuestion());
        qid++;
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say the answer !");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));
                }
                break;
            }

        }
    }
}
