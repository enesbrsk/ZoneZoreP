package com.example.zonezre.Fragments;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zonezre.Adapters.UserAdapter;
import com.example.zonezre.Adapters.UserFreeAdapter;
import com.example.zonezre.Adapters.UserPremiumAdapter;
import com.example.zonezre.Models.DoctorsItem;
import com.example.zonezre.Models.DoctorsModel;
import com.example.zonezre.R;
import com.example.zonezre.RandevuActivity;
import com.example.zonezre.RestApi.ManagerAll;
import com.example.zonezre.Utils.ChangeFragments;
import com.example.zonezre.Utils.GetSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserPremiumFragment extends Fragment {
    private String mus_id;

    private View view;
    private ChangeFragments changeFragments;
    private RecyclerView userPremiumRecylerView;
    private DoctorsModel list;
    private UserPremiumAdapter userPremiumAdapter;
    private GetSharedPreferences getSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_premium, container, false);
        LinearLayout linearLayoutFree = view.findViewById(R.id.randevu_premium_layout);
        linearLayoutFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("key", "premium");
                editor.apply();

                Intent intent = new Intent(getActivity(), RandevuActivity.class);
                startActivity(intent);
            }
        });
        tanimla();
        getDetay();
        return view;    }

    public void tanimla()
    {

        changeFragments = new ChangeFragments(getContext());
        userPremiumRecylerView = view.findViewById(R.id.userPremiumRecylerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        userPremiumRecylerView.setLayoutManager(layoutManager);
        list = new DoctorsModel();


    }

    public void getDetay()
    {
        Call<DoctorsModel> req = ManagerAll.getInstance().getUser();
        req.enqueue(new Callback<DoctorsModel>() {
            @Override
            public void onResponse(Call<DoctorsModel> call, Response<DoctorsModel> response) {

                //  list = (DoctorsModel) response.body().getDoctors();
                list = response.body();
                userPremiumAdapter = new UserPremiumAdapter(list,getContext(),getActivity());
                Log.i("kullaniciler",response.body().toString());
                userPremiumRecylerView.setAdapter(userPremiumAdapter);
            }
            @Override
            public void onFailure(Call<DoctorsModel> call, Throwable t) {

                Toast.makeText(getContext(), "HATAA:...!!", Toast.LENGTH_LONG).show();

            }
        });
    }
}

