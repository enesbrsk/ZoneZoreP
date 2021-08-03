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


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zonezre.Fragments.UserFreeFragment;
import com.example.zonezre.Fragments.UserPremiumFragment;
import com.example.zonezre.Models.DoctorsItem;
import com.example.zonezre.Models.DoctorsModel;
import com.example.zonezre.R;
import com.example.zonezre.Utils.ChangeFragments;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private static final String TAG = "UserAdapter";

    DoctorsModel list,contactListFiltered;

    SQLiteDatabase mSQLiteDatabase;

    Context context;
    Activity activity;
    ChangeFragments changeFragments;

    public UserAdapter(DoctorsModel list ,Context context, Activity activity)  {
        this.list = list;
        this.context = context;
        this.activity = activity;
        changeFragments = new ChangeFragments(context);

        mSQLiteDatabase = context.openOrCreateDatabase("database.db", Context.MODE_PRIVATE, null);
        mSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS data (name TEXT, image TEXT, status TEXT, gender TEXT); ");
        mSQLiteDatabase.execSQL("DELETE FROM data;");
        for (DoctorsItem doctorsItem: this.list.getDoctors()){
            mSQLiteDatabase.execSQL("INSERT INTO data VALUES('"
                    + doctorsItem.getFullName() + "','"
                    + doctorsItem.getImage().getUrl() +"','"
                    + doctorsItem.getUserStatus() +"','"
                    + doctorsItem.getGender() +"');");
        }
        mSQLiteDatabase.close();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.usersitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.userName.setText(list.getDoctors().get(position).getFullName());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String first_time = preferences.getString("first_time?", "");

        if (!first_time.equals("yes")) {
            Picasso.get().load(
                    list.getDoctors().get(position)
                            .getImageUrl()).into(holder.userImage);
        }else {
            Picasso.get().load(
                    list.getDoctors().get(position)
                            .getImage().getUrl()).into(holder.userImage);
        }

        holder.LinearUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferencesName = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferencesName.edit();
                if (list.getDoctors().get(position).getUserStatus().equals("free"))
                {
                  //  changeFragments.change(new UserFreeFragment());
                    changeFragments.changeParametre2(new UserFreeFragment(),list.getDoctors().get(position).getFullName());
                    editor.putString("Name", list.getDoctors().get(position).getFullName());
                    Log.d(TAG, "onClick: " + list.getDoctors().get(position).getFullName());


                }else {
                    changeFragments.changeParametre2(new UserPremiumFragment(),list.getDoctors().get(position).getFullName());
                    editor.putString("Name", list.getDoctors().get(position).getFullName());
                    Log.d(TAG, "onClick: " + list.getDoctors().get(position).getFullName());
                }
                editor.apply();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.getDoctors().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        CircleImageView userImage;
        CheckBox ChechKadin,ChechErkek;
        LinearLayout LinearUser;


        public ViewHolder(View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            userImage = itemView.findViewById(R.id.userImage);
            ChechKadin =  itemView.findViewById(R.id.checkBoxFemale);
            ChechErkek = itemView.findViewById(R.id.checkBoxMale);
            LinearUser = itemView.findViewById(R.id.LinearUser);

        }
    }

    public void changeList(String query){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String panda = preferences.getString("Gender", "");
        Log.d(TAG, "changeListaa: " + panda);
        List<DoctorsItem> result = new ArrayList<>();
        mSQLiteDatabase = context.openOrCreateDatabase("database.db", Context.MODE_PRIVATE, null);
        Cursor cursor;
        
        if (query.equals("")){
            Log.d(TAG, "changeListaa: in");
            if (panda.equals("")) {
                Log.d(TAG, "changeListaa: asd");
                cursor = mSQLiteDatabase.rawQuery("SELECT * FROM data;", new String[]{});
            } else {
                cursor = mSQLiteDatabase.rawQuery("SELECT * FROM (SELECT * FROM data WHERE gender = '" + panda + "')", new String[]{});
            }
        }else {
            if ("".equals(panda)) {
                cursor = mSQLiteDatabase.rawQuery("SELECT * FROM data WHERE name LIKE  '" + query + "%';", new String[]{});
            } else {
                cursor = mSQLiteDatabase.rawQuery("SELECT * FROM (SELECT * FROM data WHERE gender = '" + panda + "') WHERE name LIKE  '" + query + "%';", new String[]{});
            }
        }

        if (cursor.moveToFirst()){
            do {
                String name = cursor.getString(0);
                String imageUrl = cursor.getString(1);
                String status = cursor.getString(2);
                String gender = cursor.getString(3);

                result.add(new DoctorsItem(name, imageUrl, status, gender, context));
                Log.d(TAG, "changeList: " + name);
                Log.d(TAG, "changeList: " + imageUrl);
                Log.d(TAG, "changeList: " + status);
                Log.d(TAG, "changeList: " + gender);
            }while (cursor.moveToNext());
        }
        cursor.close();
        list.setDoctors(result);
        notifyDataSetChanged();
    }
}
