package com.example.mohamed.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MovieSelectActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_favourite);
    }

    public void showallbutton(View view) {
        switch (view.getId()){
            case R.id.buShowall:
                startActivity(screen4.newIntent(this,"movie"));
               break;
            case R.id.buFavourite:
                startActivity(screen4.newIntent(this,"favourite"));
                break;
        }
    }
}
