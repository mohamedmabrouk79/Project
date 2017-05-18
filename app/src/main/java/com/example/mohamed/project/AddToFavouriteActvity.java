package com.example.mohamed.project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by mohamed on 5/18/2017.
 */

public class AddToFavouriteActvity extends AppCompatActivity {
    private ImageView mImageView;
    private Button AddButton;
    private TextView MovieName;
    public static final  String KEY="KEY";
     static Movie movie;
    public static Intent newIntent(Context context,Movie mmovie){
     Intent intent=new Intent(context,AddToFavouriteActvity.class);
        movie=mmovie;
       return intent;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_favourite);
        mImageView= (ImageView) findViewById(R.id.add_image_view);
        AddButton= (Button) findViewById(R.id.add_to_fav);
        MovieName= (TextView) findViewById(R.id.movie_name_fav);
        Picasso.with(this).load(Uri.parse(movie.getImage())).into(mImageView);
        MovieName.setText(movie.getName());
        AddToFav();

    }

    private void AddToFav(){
      AddButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              MovieLab movieLab=MovieLab.getInstance(AddToFavouriteActvity.this);
                  Toast.makeText(AddToFavouriteActvity.this, movieLab.insertFavourite(movie)+ ":", Toast.LENGTH_SHORT).show();;

          }
      });
    }
}
