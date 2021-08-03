package com.example.zonezre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class RandevuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu);

        TextView textView = findViewById(R.id.textView);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getString("key", "free").equals("free")){
            textView.setText("Ödeme Ekranı");
        }else {
            textView.setText("Randevu Ekranı");
        }
    }
}