package com.example.mohamed.project;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mohamed on 5/18/2017.
 */

public class SherdPrefarnaceForLogin {
    private Context mContext;
    public SherdPrefarnaceForLogin(Context context){
        mContext=context;
    }

    public void Save(String email,String password){
        SharedPreferences.Editor editor = mContext.getSharedPreferences("login.txt", MODE_PRIVATE).edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();
    }

    public boolean CheckUser(String email,String password){
        SharedPreferences editor = mContext.getSharedPreferences("login.txt", MODE_PRIVATE);
        String mail=editor.getString("email",null);
        String pass=editor.getString("password",null);
        if (mail.equals(email) && pass.equals(password)){
            return true;
        }
        return false;
    }
}
