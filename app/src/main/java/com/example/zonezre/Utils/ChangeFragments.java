package com.example.zonezre.Utils;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.zonezre.R;


public class ChangeFragments {


    private Context context;

    public ChangeFragments(Context context) {
        this.context = context;
    }
    public void change(Fragment fragment){

        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }
    public void changeParametre(Fragment fragment, String petId)
    {
        Bundle bundle = new Bundle();
        bundle.putString("petid",petId);
        fragment.setArguments(bundle);

        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }

    public void changeParametre2(Fragment fragment, String petId)
    {
        Bundle bundle = new Bundle();
        bundle.putString("userid",petId);
        fragment.setArguments(bundle);

        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }
}
