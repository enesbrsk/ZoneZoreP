package com.example.zonezre.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zonezre.Adapters.UserAdapter;
import com.example.zonezre.Models.DoctorsItem;
import com.example.zonezre.Models.DoctorsModel;
import com.example.zonezre.R;
import com.example.zonezre.RestApi.ManagerAll;
import com.example.zonezre.Utils.ChangeFragments;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class UsersFragment extends Fragment {

    private View view;
    private ChangeFragments changeFragments;

    private RecyclerView kullaniciRecylerView;
    private DoctorsModel list;
    private UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_users, container, false);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("first_time?", "yes");
        editor.putString("Gender", "");
        editor.apply();
        final SearchView searchView = view.findViewById(R.id.search_view);
        final CheckBox checkBoxFemale = view.findViewById(R.id.checkBoxFemale);
        final CheckBox checkBoxMale = view.findViewById(R.id.checkBoxMale);
        final TextView textView = view.findViewById(R.id.textView2);
        final ImageView imageView = view.findViewById(R.id.imageView2);

        checkBoxFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: ");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                if(isChecked){
                    checkBoxMale.setChecked(false);
                    editor.putString("Gender", "female");
                    editor.putString("first_time?", "nope");
                    editor.apply();
                    userAdapter.changeList(searchView.getQuery().toString());
                }else{
                    if (!checkBoxMale.isChecked()){
                        editor.putString("Gender", "");
                        editor.apply();
                        userAdapter.changeList(searchView.getQuery().toString());
                    }
                }
                if (userAdapter.getItemCount() == 0){
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });

        checkBoxMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                if(isChecked){
                    Log.d(TAG, "onCheckedChanged: ");
                    checkBoxFemale.setChecked(false);
                    editor.putString("Gender", "male");
                    editor.putString("first_time?", "nope");
                    editor.apply();
                    userAdapter.changeList(searchView.getQuery().toString());
                }else{
                    if (!checkBoxMale.isChecked()){
                        editor.putString("Gender", "");
                        editor.apply();
                        userAdapter.changeList(searchView.getQuery().toString());
                    }
                }
                if (userAdapter.getItemCount() == 0){
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("first_time?", "nope");
                editor.apply();
                userAdapter.changeList(query);

                if (userAdapter.getItemCount() == 0){
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    userAdapter.changeList(newText);
                }
                if (userAdapter.getItemCount() == 0){
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        tanimla();
        getKullaniciler();
        return view;
    }

    public void tanimla() {

        changeFragments = new ChangeFragments(getContext());
        kullaniciRecylerView = view.findViewById(R.id.userRecylerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        kullaniciRecylerView.setLayoutManager(layoutManager);
        list = new DoctorsModel();

    }

    public void getKullaniciler()
    {
        Call<DoctorsModel> req = ManagerAll.getInstance().getUser();
        req.enqueue(new Callback<DoctorsModel>() {
            @Override
            public void onResponse(Call<DoctorsModel> call, Response<DoctorsModel> response) {

             //  list = (DoctorsModel) response.body().getDoctors();
                list = response.body();
                userAdapter = new UserAdapter(list,getContext(),getActivity());
                Log.i("kullaniciler",response.body().toString());
                kullaniciRecylerView.setAdapter(userAdapter);
            }
            @Override
            public void onFailure(Call<DoctorsModel> call, Throwable t) {
                Toast.makeText(getContext(), "BEKLENMEYEN HATA!", Toast.LENGTH_LONG).show();
            }
        });
    }
}

