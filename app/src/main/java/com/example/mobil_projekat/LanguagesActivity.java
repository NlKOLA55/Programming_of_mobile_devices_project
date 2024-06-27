package com.example.mobil_projekat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.Locale;
public class LanguagesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Entered LanguagesActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);
        // PRIPREMA
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButtonEnglish = findViewById(R.id.radioButtonEnglish);
        RadioButton radioButtonSerbianLatin = findViewById(R.id.radioButtonSerbianLatin);
        RadioButton radioButtonSerbianCyrillic = findViewById(R.id.radioButtonSerbianCyrillic);

        //UZMI PREFERENCU
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        //AKO NEPOSTOJI
        if (!language.isEmpty() && !language.equals(getCurrentLocale())) {
            setLocale(language, false);
        }
        //AKO POSTOJI
        switch (language) {
            case "en":
                radioButtonEnglish.setChecked(true);
                break;
            case "sr":
                radioButtonSerbianLatin.setChecked(true);
                break;
            case "srb":
                radioButtonSerbianCyrillic.setChecked(true);
                break;

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            //KADA SE PROMENI RADIO DUGME
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                System.out.println("onCheckedChanged Called");
                if (checkedId == R.id.radioButtonEnglish) {
                    setLocale("en", true);
                } else if (checkedId == R.id.radioButtonSerbianLatin) {
                    setLocale("sr", true);
                } else if (checkedId == R.id.radioButtonSerbianCyrillic) {
                    setLocale("srb", true);
                }
            }
        });
    }
    private void setLocale(String lang, boolean recreate) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // SACUVA SE PEFERENCA
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

        // REFRESUJEMO STRANU
        if (recreate) {
            recreate();
        }
    }
    private String getCurrentLocale() {
        return getResources().getConfiguration().getLocales().get(0).getLanguage();
    }


    //ALL THE MENU BUTENS
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

    public void backBtnClick(View view) {
        System.out.println("Back Clicked");
        Intent intent = new Intent(LanguagesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top);
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