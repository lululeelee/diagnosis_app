package com.test.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class alarm_list extends AppCompatActivity {

    private View list_layout;
    private TextView tv;
    private ListView lv;
    private Button btn_add;

    private List<String> list = new ArrayList<>();
    private List<String> alarm_time = new ArrayList<>();
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        list_layout = findViewById(R.id.alarm_view_layout);
        tv = findViewById(R.id.text_nothing);
        lv = findViewById(R.id.list_alarm);

        btn_add = findViewById(R.id.btn_alarm);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(alarm_list.this, alarm.class);
                pref = getSharedPreferences("alarm_list",Context.MODE_PRIVATE);
                Set<String> set = pref.getStringSet("alarm", null);
                if (set == null){
                    intent.putExtra("code",0);
                }
                else{
                    intent.putExtra("code", set.size());
                }
                startActivity(intent);

            }
        });

        showList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
    }

    private void showList(){

        pref = this.getSharedPreferences("alarm_list",Context.MODE_PRIVATE);
        Set<String> set = pref.getStringSet("alarm", null);

        if (set == null){
            tv.setVisibility(TextView.VISIBLE);
            list_layout.setVisibility(TextView.GONE);
        }
        else{
            tv.setVisibility(TextView.GONE);
            list_layout.setVisibility(TextView.VISIBLE);

            list.clear();
            list.addAll(set);
            Log.d("alarm", set.toString());

            lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(alarm_list.this, alarm.class);
                    intent.putExtra("Edit", true);
                    intent.putExtra("item_name", list.get(position));
                    intent.putExtra("time", pref.getString(list.get(position), null));
                    intent.putExtra("code",list.size());
                    startActivity(intent);
                }
            });
        }
    }
}
