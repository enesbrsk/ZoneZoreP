package com.example.zonezre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.zonezre.Fragments.UsersFragment;
import com.example.zonezre.Utils.ChangeFragments;
import com.example.zonezre.Utils.GetSharedPreferences;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences getSharedPreferences;
    private GetSharedPreferences preferences;
    private ChangeFragments changeFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragment();
        tanimla();

    }
    private void getFragment() {
        changeFragments  = new ChangeFragments(MainActivity.this);
        changeFragments.change(new UsersFragment());
    }

    public void tanimla(){
        preferences = new GetSharedPreferences(MainActivity.this);
        getSharedPreferences= preferences.getSession();


    }
}
