package com.example.mobil_projekat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    List<Quiz> quizList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Entered MainActivity");
        // PRIPREMA PROMENJIVE//
        recyclerView = findViewById(R.id.QuestionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String jsonString = "{\"json2\":[{\"a\":42,\"b\":11,\"op\":\"-\"}," +
                                        "{\"a\":38,\"b\":2,\"op\":\"/\"},"  +
                                        "{\"a\":33,\"b\":3,\"op\":\"/\"},"  +
                                        "{\"a\":16,\"b\":4,\"op\":\"/\"},"  +
                                        "{\"a\":46,\"b\":32,\"op\":\"-\"}," +
                                        "{\"a\":15,\"b\":32,\"op\":\"+\"}," +
                                        "{\"a\":21,\"b\":18,\"op\":\"+\"}," +
                                        "{\"a\":9,\"b\":3,\"op\":\"*\"},"   +
                                        "{\"a\":5,\"b\":9,\"op\":\"*\"},"   +
                                        "{\"a\":15,\"b\":3,\"op\":\"*\"}]}" ;
        try {
            // POPUNITI LISTU //
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("json2");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int a = obj.getInt("a");
                int b = obj.getInt("b");
                String op = obj.getString("op");
                Quiz quiz = new Quiz(a, b, op);
                quizList.add(quiz);
                System.out.println("Added:|"+ quiz.getQuiz()+"|to \"quizList\"");
            }
            // ISPISUJE LISTU
            QuizAdapter quizAdapter = new QuizAdapter(quizList);
            recyclerView.setAdapter(quizAdapter);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    //SELECT DUGMAD
    String text;
    public void selectQuestionClick(View view) {
        // UZIMA PITANJE IZ LISTE
        TextView textView = (TextView) view;
        text = textView.getText().toString();
        System.out.println("Selected: "+text);

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            System.out.println("Enterd loop: "+ i);

            View childView = recyclerView.getChildAt(i);
            System.out.println("aaa");
            ConstraintLayout constraintLayout = (ConstraintLayout) childView;
            TextView temp = (TextView) constraintLayout.getChildAt(0);
            System.out.println("bbb");
            temp.setBackgroundResource(R.drawable.list_element);
            System.out.println("ccc");
        }
        textView.setBackgroundResource(R.drawable.list_element_selected);
    }
    public void selectBtnClick(View view) {
        System.out.println("Select Clicked:");
        //AKO JE PITANJE IZABRANO IDE NA DRUGU STRANICU
        if (text != null && !text.isEmpty()) {
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra("Question", text);
            startActivity(intent);
            System.out.println("Passing: "+text);
            overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
            finish();
        }else{
            System.out.println("No question selected");
            //NERADI NAZNAM ZATO
            Toast.makeText(MainActivity.this, "choose a question", Toast.LENGTH_LONG).show();
        }
    }

    public void randomBtnClick(View view) {
        System.out.println("Ransom Clicked");

        //UZAMA SE RANDOM ZADATAK
        int listSize = quizList.size();
        int randomSelect = (int) (Math.random() * listSize);
        Quiz quiz = quizList.get(randomSelect);
        System.out.println("Randomly Selected: "+ quiz.getQuiz());
        //SALJE SE NA DRUGU STRANU
        Intent intent = new Intent(this,QuestionActivity.class);
        intent.putExtra("Question",quiz.getQuiz());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_right,R.anim.slide_in_left);
        finish();
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
        System.out.println("Close Clicked");
        //NAPRAVI MENU NEVIDNJIVO I POKAZI DUGME
        menuBtn = findViewById(R.id.menuBtn);
        menuBtn.setVisibility(View.VISIBLE);

        menuLayout =findViewById(R.id.menuLayout);
        menuLayout.setVisibility(View.GONE);
    }

    public void languagesBtnClick(View view) {
        System.out.println("languages Clicked");
        //ODLAZI SE NA DRUGU STRANU
        Intent intent = new Intent(this,LanguagesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
        finish();
    }

    public void creditsBtnClick(View view) {
        System.out.println("Credits Clicked");
        //ODLAZI SE NA HTML STRANU
        Intent intent = new Intent(this, CreditsActivity.class);
        startActivity(intent);
    }

    public void exitBtnClick(View view) {
        System.out.println("Exit Clicked");
        //IZBACI ALERT ZA IZLAZENJE APLIKACIJE
        new AlertDialog.Builder(this)
            .setTitle(R.string.exit_title)
            .setMessage(R.string.exit_message)
            .setPositiveButton(R.string.exit_positive, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setNegativeButton(R.string.exit_negative, null)
            .setIcon(android.R.drawable.ic_menu_help)
            .show();
    }
}

