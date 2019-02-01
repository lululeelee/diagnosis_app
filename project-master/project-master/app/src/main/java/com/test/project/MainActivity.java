package com.test.project;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private String url;
    private TextView TextView01;
    private TextView news_marquee;
    private JSONArray news = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = "https://api.currentsapi.services/v1/search?language=zh&category=medical&apiKey=" + getResources().getString(R.string.news_api);
        TextView01= (TextView)findViewById(R.id.TextView01);
        news_marquee = findViewById(R.id.view_news);

        Button btn_map = (Button)findViewById(R.id.Button02);
        btn_map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,hospital_list
                        .class);
                startActivity(intent);
            }
        });
        Button btn_diagnosis = (Button)findViewById(R.id.Button01);
        btn_diagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,search.class);
                startActivity(intent);
            }
        });
        Button btn_calender = (Button)findViewById(R.id.Button03);
        btn_calender.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,calender.class);
                startActivity(intent);
            }
        });
        Button btn_alarm = (Button)findViewById(R.id.Button04);
        btn_alarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,alarm_list.class);
                startActivity(intent);
            }
        });

        //*************************** using news api **************************

        RequestQueue queue = Volley.newRequestQueue(this);

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    news = response.getJSONArray("news");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StringBuilder str = new StringBuilder();
                for (int i=0; i<news.length(); ++i){
                    try {
                        str.append(news.getJSONObject(i).getString("title"));
                        str.append(";                      ");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                news_marquee.setText(str);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }
}