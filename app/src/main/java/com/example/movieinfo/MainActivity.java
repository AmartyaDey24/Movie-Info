package com.example.movieinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RequestQueue requestQueue;
    private List<Movie_home> movie_homeList;
    Adapter_home adapter_home;
    EditText searchBar;
    CharSequence search = "";


//    public void Share_nav(View view){

//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        searchBar = findViewById(R.id.searchBar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VollySingleton.getmInstance(this).getRequestQueue();

        movie_homeList = new ArrayList<>();

        fetchMovies();

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                adapter_home.getFilter().filter(charSequence);
                search = charSequence;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.home_nav:
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            hideKeyboard(searchBar);
                            finish();
                            break;
                        case R.id.search_nav:
                            FocusOnSearch(searchBar);
                            break;
                        case R.id.share_nav:
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            String Body = "Download this App";
                            String Sub = "http://play.google.com";
                            intent.putExtra(Intent.EXTRA_TEXT, Body);
                            intent.putExtra(Intent.EXTRA_TEXT, Sub);
                            startActivity(Intent.createChooser(intent,"Share through"));
                            break;
                    }
                    return true;
                }
            };

    public void FocusOnSearch(EditText search_bar){
        InputMethodManager manager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        manager.showSoftInput(search_bar.getRootView(),InputMethodManager.SHOW_IMPLICIT);
        search_bar.requestFocus();
    }

    public void hideKeyboard(EditText search_bar){
        InputMethodManager manager = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        manager.hideSoftInputFromWindow(search_bar.getApplicationWindowToken(), 0);
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

                    adapter_home = new Adapter_home(MainActivity.this,movie_homeList);

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