package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView question, qCount, timer;
    private Button option1, option2, option3, option4;
    private List<Question> questionList;
    private int quesNum;
    private CountDownTimer countDown;
    private int score;
    private FirebaseFirestore firestore;
    private int setNo;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question = findViewById(R.id.question);
        qCount = findViewById(R.id.ques_num);
        timer = findViewById(R.id.countdown);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
        getQuestionsList();

    }

    private void getQuestionsList() {

        questionList = new ArrayList<>();

        questionList.add(new Question("What does SDK stands for?", "Software Development Kit", "System Development Kit", "Structure Development Kit", "Simple Development Kit", 1));
        questionList.add(new Question("What does API stands for?", "Application Programming Interface", "Application Progress Interface", "Application Planning  Interface", "Apple Programming Infrastructure", 1));
        questionList.add(new Question("What does postmanTool used for?", "To Deliver Mail", "To Test API", "To Make Pictures", "To Do online Chat", 2));
        questionList.add(new Question("If a method does not return any value, what type would it take?", "Integer", "String", "void", "Intents", 3));
        questionList.add(new Question("What Android feature allows the user to play back music, take pictures with the camera, or use the microphone for audio note-taking?", "Storage", "Network", "Multimedia", "GPS", 3));
        setQuestion();
    }


    private void setQuestion() {

        timer.setText(String.valueOf(10));
        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());

        qCount.setText(String.valueOf(1) + "/" + String.valueOf(questionList.size()));

        startTimer();

        quesNum = 0;


    }

    private void startTimer() {
       countDown = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished < 10000)
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        countDown.start();

    }

    @Override
    public void onClick(View v) {
        int selectedOption = 0;

        switch (v.getId()) {
            case R.id.option1:
                selectedOption = 1;
                break;

            case R.id.option2:
                selectedOption = 2;
                break;

            case R.id.option3:
                selectedOption = 3;
                break;

            case R.id.option4:
                selectedOption = 4;
                break;

            default:
        }


        if(countDown != null) {
            countDown.cancel();
        }

        //countDown.cancel();  // to stop the timer
        checkAnswer(selectedOption, v);

    }

    private void checkAnswer(int selectedOption, View view) {

        if (selectedOption == questionList.get(quesNum).getCorrectAns()) {

            // if the user chooses the right answer then the selected options color changes to green
            //Right Answer


            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN)); //to change the background color of the selected option
            score++;

        } else {
            //if the user chooses the wrong answer then the selected options color changes to red
            //Wrong Answer
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch (questionList.get(quesNum).getCorrectAns()) {
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;

            }

        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        }, 2000); //delay before the next question shows after  you select an option for a question


    }

    private void changeQuestion() {


        if (quesNum < questionList.size() - 1) {
            quesNum++; //this will help in going to next question   (change question)

            playAnim(question, 0, 0); //call the animation of the options like make them appear and disappear
            playAnim(option1, 0, 1);
            playAnim(option2, 0, 2);
            playAnim(option3, 0, 3);
            playAnim(option4, 0, 4);

            // to show on which question we are out of total questions
            qCount.setText(String.valueOf(quesNum + 1) + "/" + String.valueOf(questionList.size()));

            timer.setText(String.valueOf(10));
            startTimer(); //helps starts the timer again for new question

        } else {
            // Go to Score Activity
            Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
            intent.putExtra("SCORE", String.valueOf(score) + "/" + String.valueOf(questionList.size()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //QuestionActivity.this.finish();
        }


    }

            private void playAnim(View view, final int value, final int viewNum){

                view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                        .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if(value == 0)
                                {
                                    switch (viewNum)
                                    {
                                        case 0:
                                            ((TextView)view).setText(questionList.get(quesNum).getQuestion());
                                            break;
                                        case 1:
                                            ((Button)view).setText(questionList.get(quesNum).getOptionA());
                                            break;
                                        case 2:
                                            ((Button)view).setText(questionList.get(quesNum).getOptionB());
                                            break;
                                        case 3:
                                            ((Button)view).setText(questionList.get(quesNum).getOptionC());
                                            break;
                                        case 4:
                                            ((Button)view).setText(questionList.get(quesNum).getOptionD());
                                            break;

                                    }


                                    if(viewNum != 0)
                                        ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E99C03")));//to reset the background of the options orange color back


                                    playAnim(view,1,viewNum);  // call the animation and changing its values to one so that the options button can re-appear

                                }

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });



            }
}









