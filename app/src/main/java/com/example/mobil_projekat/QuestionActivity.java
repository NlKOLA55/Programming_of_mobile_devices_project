package com.example.mobil_projekat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionActivity extends BaseActivity {
    private Quiz quiz;
    private EditText answerView;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        answerView = findViewById(R.id.answerText);
        RadioButton hint;

        // UZIMANJE TEXTA OD PROSLE STRANE
        TextView questionText = findViewById(R.id.questionText);
        String text = getIntent().getStringExtra("Question");
        questionText.setText(text);
        //NAPRAVI OBJEKAT QUIZ
        String[] data = text.split(" ");
        quiz = new Quiz(Integer.parseInt(data[0]), Integer.parseInt(data[2]), data[1]);


        //POPUNI HINTOVE
        ArrayList<Integer> hintIds = new ArrayList<>();
        hintIds.add(R.id.hint1);
        hintIds.add(R.id.hint2);
        hintIds.add(R.id.hint3);
        //JEDAN OD HINTOVA JE TACAN ODGOVOR
        int answerAt = (int) (Math.random() * 3);
        hint = findViewById(hintIds.get(answerAt));
        hint.setText(String.valueOf(quiz.getAnswer()));
        System.out.println("Hint" + (answerAt + 1) +" is the correct answer:"+ quiz.getAnswer());
        System.out.println("Removing Hint: " + hintIds.get(answerAt) );
        hintIds.remove(answerAt);
        //OSTALI SU RANDOM BROJEVI +-3 OD ORIGINALNOG
        int hintHolder = -1;
        for (Integer hintId : hintIds) {
            System.out.println("HintId: " + hintId);
            hint = findViewById(hintId);

            int randomNum;
            do {
                randomNum = randomHint(quiz.getAnswer());
                //AKO JE ISTI BROJ KAO TACAN ODGOVOR PROBAJ PONOVO
            }while(randomNum == quiz.getAnswer() || randomNum == hintHolder);

            System.out.println("Hint is set to " + randomNum);
            hintHolder = randomNum;
            hint.setText(String.valueOf(randomNum));
        }


    }
    public int randomHint(int Answer){
        int randomNum = (int) ((Math.random() * 6) + Answer-3);
        return randomNum;
    }
    //DAVANJE ODGOVORA
    public void submitBtn(View view) {
        System.out.println("Submit Clicked");
        //UZMI UNET BROJ
        String answerText = answerView.getText().toString();
        //DALI SMO DOBRO UNELI BROJ
        if (!answerText.isEmpty()) {
            //PRETVORI BROJ U DOUBLE
            double answer = Double.parseDouble(answerText);
            System.out.println("Inputted Answer = " + answer);
            System.out.println("Answer = " + quiz.getAnswer());

            //DALI JE TACNO ILI NETACNO
            if (answer == quiz.getAnswer()) {
                System.out.println("Correct");
                //VIDLJIVOS SLIKE ZA POTVRDU
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageResource(R.drawable.correct);
                imageView.setVisibility(View.VISIBLE);
                //VIDLJIVOST DUGMADI
                Button submitBtn = findViewById(R.id.submitBtn);
                submitBtn.setVisibility(View.GONE);
                Button backBtn2 = findViewById(R.id.backBtn2);
                backBtn2.setVisibility(View.VISIBLE);
            } else{
                System.out.println("InCorrect");
                //VIDLJIVOS SLIKE ZA POTVRDU
                ImageView imageView = findViewById(R.id.imageView);
                imageView.setImageResource(R.drawable.incorrect);
                imageView.setVisibility(View.VISIBLE);
            }
        } else {
            System.out.println("Enter Answer");
            //NERADI
            Toast.makeText(QuestionActivity.this,"Enter Answer",Toast.LENGTH_LONG).show();
        }
    }


    // POKAZI HINTOVE
    public void hintBtnClick(View view) {
        System.out.println("Hint Clicked");
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        //IZBACI ALERT
        new AlertDialog.Builder(this)
                .setTitle(R.string.hint_title)
                .setMessage(R.string.hint_message)
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Yes Clicked");
                        System.out.println("Showing Hint");
                        //POKAZUJA HINT
                        radioGroup.setVisibility(View.VISIBLE);
                    }
                })
                .setNegativeButton(R.string.negative, null)
                .setIcon(android.R.drawable.ic_menu_help)
                .show();
    }
    public void radioGroupClick(View view) {
        System.out.println("radioGroup Clicked");
        //KOJI JE IZABRAN
        radioGroup = findViewById(R.id.radioGroup);
        int checkedId = radioGroup.getCheckedRadioButtonId();
        RadioButton checkedBtn = findViewById(checkedId);
        //UZMI TEXT IZ IZABRANOG I STAVI GA U ODGOVOR
        String checkedText = checkedBtn.getText().toString();
        answerView.setText(checkedText);
    }
    public void answerBtnClick(View view) {
        System.out.println("Answer Clicked");
        //IZBACI ALERT
        new AlertDialog.Builder(this)
                .setTitle(R.string.answer_title)
                .setMessage(R.string.answer_message)
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Yes Clicked");
                        System.out.println("Showing Answer: " + quiz.getAnswer());
                        //ISPISUJE ODGOVOR
                        answerView.setText(String.valueOf(quiz.getAnswer()));
                    }
                })
                .setNegativeButton(R.string.negative, null)
                .setIcon(android.R.drawable.ic_menu_help)
                .show();
    }
    //MENU DUGMAD
    private Button menuBtn;
    private LinearLayout menuLayout;
    public void menuBtnClick(View view) {
        System.out.println("Menu Clicked");
        //NAPRAVI DUGME NEVIDNJIVO I POKAZI MENU
        menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setVisibility(View.GONE);

        menuLayout =findViewById(R.id.menuLayout);
        menuLayout.setVisibility(View.VISIBLE);
    }

    public void closeBtnClick(View view) {
        System.out.println("Close Menu Clicked");
        //NAPRAVI MENU NEVIDNJIVO I POKAZI DUGME
        menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setVisibility(View.VISIBLE);

        menuLayout =findViewById(R.id.menuLayout);
        menuLayout.setVisibility(View.GONE);
    }

    //NAZAD NA PROSLU STRANU
    public void backBtnClick(View view) {
        System.out.println("Back Clicked");
        Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
    }

}