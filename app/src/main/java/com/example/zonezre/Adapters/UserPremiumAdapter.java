package com.example.zonezre.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zonezre.Fragments.UserFreeFragment;
import com.example.zonezre.Models.DoctorsItem;
import com.example.zonezre.Models.DoctorsModel;
import com.example.zonezre.R;
import com.example.zonezre.Utils.ChangeFragments;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserPremiumAdapter extends RecyclerView.Adapter<UserPremiumAdapter.ViewHolder> {
    private static final String TAG = "UserPremiumAdapter";

    DoctorsModel list;


    Context context;
    Activity activity;
    ChangeFragments changeFragments;

    public UserPremiumAdapter(DoctorsModel list , Context context, Activity activity)  {
        this.list = list;
        this.context = context;
        this.activity = activity;
        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.userpremiumitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = sharedPreferences.getString("Name", "");

        DoctorsItem doctor = getDoctor(name);

        holder.userPremiumName.setText(doctor.getFullName());
        Picasso.get().load(doctor
                .getImageUrl())
                .into(holder.premiumImage);
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userPremiumName;
        CircleImageView premiumImage;



        public ViewHolder(View itemView) {
            super(itemView);

            userPremiumName = itemView.findViewById(R.id.premiumUserName);
            premiumImage = itemView.findViewById(R.id.premiumImage);



        }
    }

    public DoctorsItem getDoctor(String name){
        DoctorsItem doctor = null;

        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("database.db", Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM data WHERE name = '" + name + "';", new String[]{});
        if (cursor.moveToFirst()){
            Log.d(TAG, "getDoctor: ");
            do {
                String name1 = cursor.getString(0);
                String imageUrl1 = cursor.getString(1);
                String status1 = cursor.getString(2);
                String gender1 = cursor.getString(3);
                doctor = new DoctorsItem(name1, imageUrl1, status1, gender1, context);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return doctor;
    }

}
