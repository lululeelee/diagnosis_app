package com.test.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.test.project.db.databasehelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class calender extends AppCompatActivity {
    CalendarView calendarView;
    TextView date_textview;
    String date;
    TextView condition_textview;
    databasehelper mycalendar = new databasehelper(this);
    Button viewall_btn;
    String datetoday = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_calender);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        viewall_btn = (Button)findViewById(R.id.viewall_btn) ;
        condition_textview = (TextView) findViewById(R.id.condition_textview);
        date_textview = (TextView) findViewById(R.id.date_textview);
        date_textview.setText(datetoday);
        date=datetoday;

        showData();
        condition_button();
        viewalldata2();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    private void showData(){
        Cursor res = mycalendar.checkdate(date);
        if(res.getCount()==0){
            condition_textview.setText("No record!");
        }
        else{
            StringBuffer buffer = new StringBuffer();
            res.moveToNext();
            buffer.append("Temperature : " + res.getString(1)+"\n");
            buffer.append("Bphigh : " + res.getString(2)+" ");
            buffer.append("Bplow : " + res.getString(3)+"\n");
            buffer.append("Sysm : " + res.getString(4)+"\n");
            buffer.append("Other : " + res.getString(5)+"\n\n");
            condition_textview.setText(buffer);
        }



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if(dayOfMonth < 10) {
                    date = year + "/" + (month + 1) + "/0" + dayOfMonth;
                }

                else
                    date = year + "/" + (month + 1) + "/" + dayOfMonth;
                date_textview.setText(date);


                ///////
                Cursor res = mycalendar.checkdate(date);

                if(res.getCount()==0){
                    condition_textview.setText("No record!");


                }
                else{

                    StringBuffer buffer = new StringBuffer();
                    res.moveToNext();
                    buffer.append("Temperature : " + res.getString(1)+"\n");
                    buffer.append("Bphigh : " + res.getString(2)+" ");
                    buffer.append("Bplow : " + res.getString(3)+"\n");
                    buffer.append("Sysm : " + res.getString(4)+"\n");
                    buffer.append("Other : " + res.getString(5)+"\n\n");
                    condition_textview.setText(buffer);

                }


            }
        });
    }

    private void condition_button(){
        Button modify_btn= (Button) findViewById(R.id.modify_btn);
        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calender.this,conditions.class);
                intent.putExtra("date",date_textview.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void viewalldata2() {
        viewall_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mycalendar.getalldata();
                if(res.getCount() == 0){

                    showMessage2("Error","Nothing found!");
                    return;
                }
                else{
                    StringBuffer buffer = new StringBuffer();
                    res.moveToLast();
                    while(true){
                        buffer.append("Date : " + res.getString(0)+"\n");
                        buffer.append("Temperature : " + res.getString(1)+"\n");
                        buffer.append("Bphigh : " + res.getString(2)+"\n");
                        buffer.append("Bplow : " + res.getString(3)+"\n");
                        buffer.append("Sysm : " + res.getString(4)+"\n");
                        buffer.append("Other : " + res.getString(5)+"\n\n");

                        if(res.isFirst()){
                            break;
                        }
                        res.moveToPrevious();
                    }

                    //show all data
                    showMessage2("Data",buffer.toString());
                }


            }


        });

    };

    public void showMessage2(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    };
}

