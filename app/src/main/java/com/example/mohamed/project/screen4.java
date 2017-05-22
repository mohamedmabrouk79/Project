package com.example.mohamed.project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class screen4 extends AppCompatActivity {
    List<Movie> movies = new ArrayList<>();
    private RecyclerView mRecyclerView;
   static   String type;
    private ProgressBar mProgressBar;
    private static String Url = "http://haladoctor.com/testSec/getAllMovies.php?studentNumber=3&studentSec=3&studentDep=2";

    public static Intent newIntent(Context context,String types){
        Intent intent=new Intent(context,screen4.class);
        type=types;
        return intent;
    }

    class getMovie extends AsyncTask<Void,Void,String>{
      MovieLab mMovieLab;
         getMovie(MovieLab movieLab){
             mMovieLab=movieLab;
         }
        @Override
        protected String doInBackground(Void... params) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        Toast.makeText(screen4.this,jsonArray.length()+":",Toast.LENGTH_LONG).show();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject =jsonArray.getJSONObject(i);

                            String id =  jsonObject.getString("movie_id_18");
                            String name =  jsonObject.getString("movie_name_18");
                            // String image=jsonObject.getString("movie_img_18");

                            String image = "http://loremflickr.com/cache/images/f512fedb2caf38c32d290f98abfddbac."+getRandomNumber().get(i)+".jpg";
                            //Toast.makeText(screen4.this, image, Toast.LENGTH_SHORT).show();
                            Movie Movie = new Movie(id, image, name);
                            mMovieLab.deleteMovie(Movie.getId());
                            mMovieLab.insertMovies(Movie);
                            movies.add(Movie);

                        }
                        for (Movie movie:movies){
                            for (Movie movie1:mMovieLab.getFavourites()) {
                                if (movie1.getId().equals(movie.getId())){
                                    mMovieLab.update(movie);
                                }
                            }
                        }
                        upadte(movies);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }

            );
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_list);
        mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        final MovieLab movieLab=MovieLab.getInstance(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(screen4.this));

        if (CheckConnection.isNetworkAvailableAndConnected(this)&& CheckConnection.isNetworkConnected(this) && type.equals("movie")){
            getMovie getMovie=new getMovie(movieLab);
            getMovie.execute();
    }else if (type.equals("favourite")){
            movies=  movieLab.getFavourites();
            upadte(movies);
    }else if (type.equals("movie")){
            movies=  movieLab.getMovies();
           upadte(movies);
        }

    }

    public void upadte(List<Movie> mMovies){
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setAdapter(new MovieAdapter(mMovies));
    }

    class MovieHolder extends RecyclerView.ViewHolder{
        private KenBurnsView mImageView;
        private TextView mTextView;
        public MovieHolder(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView.findViewById(R.id.movie_name);
            mImageView= (KenBurnsView) itemView.findViewById(R.id.movie_image);

        }
        Picasso.Builder builder;
        public void bind(final Movie movie){
          //  Toast.makeText(screen4.this, movie.getName()+"", Toast.LENGTH_SHORT).show();
             builder = new Picasso.Builder(screen4.this);
            builder.listener(new Picasso.Listener()
            {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                {
                    int x=(int) (Math.random()*10)-1;
                    getLayoutPosition();

                    String image = "http://loremflickr.com/cache/images/f512fedb2caf38c32d290f98abfddbac."+getRandomNumber().get(getLayoutPosition()<0?0:getLayoutPosition())+".jpg";
                    Movie movie1=movies.get(getLayoutPosition()<0?0:getLayoutPosition());
                    movie1.setImage(image);
                   MovieLab.getInstance(screen4.this).update(movie1);
                    builder.build().load(Uri.parse(image)).into(mImageView);
                }
            });
            builder.build().load(Uri.parse(movie.getImage())).into(mImageView);
          // Picasso.with(screen4.this).load(Uri.parse(movie.getImage())).into(mImageView);
            mTextView.setText(movie.getName());
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(AddToFavouriteActvity.newIntent(screen4.this,movie));
                }
            });

        }
    }


    class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{
        private List<Movie> movies =new ArrayList<>();
        public MovieAdapter(List<Movie> movieList){
            movies=movieList;
        }
        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(screen4.this).inflate(R.layout.movie_item,parent,false);
            return  new MovieHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position) {
         Movie movie=movies.get(position);
            holder.bind(movie);
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }



    public List<Integer> getRandomNumber(){
        Random rnd = new Random();
        List<Integer> list=new ArrayList<>();
        Set<Integer> integers=new HashSet<>();
        for (int i=0 ;;i++){
            int x = 10 + rnd.nextInt(89);
            if(integers.size()==9){
                break;
            }else {
                integers.add(x);
                list.add(x);
            }

        }

       return list;
    }
}
