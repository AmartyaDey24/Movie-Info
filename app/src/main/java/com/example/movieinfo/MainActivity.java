package com.example.movieinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RequestQueue requestQueue;
    private List<Movie_home> movie_homeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VollySingleton.getmInstance(this).getRequestQueue();

        movie_homeList = new ArrayList<>();

        fetchMovies();

    }

    private void fetchMovies() {
        String url = "https://api.tvmaze.com/search/shows?q=all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        JSONObject show = jsonObject.getJSONObject("show");
                        String title = show.getString("name");

                        JSONObject image = show.getJSONObject("image");
                        String thumbnail = image.getString("medium");

                        String summery = show.getString("summary");
                        String summeryFinal1 = summery.replaceAll("<p>","");
                        String summeryFinal2 = summeryFinal1.replaceAll("</p>","");
                        String summeryFinal3 = summeryFinal2.replaceAll("<b>","");
                        String summeryFinal4 = summeryFinal3.replaceAll("</b>","");
//                        String summeryFinal = summeryFinal4.substring(0,100);

                        JSONObject rating_object = show.getJSONObject("rating");
                        Double rating = rating_object.getDouble("average");

                        Movie_home movie_home = new Movie_home(title,thumbnail,summeryFinal4,rating);
                        movie_homeList.add(movie_home);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Adapter_home adapter_home = new Adapter_home(MainActivity.this,movie_homeList);

                    recyclerView.setAdapter(adapter_home);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

}