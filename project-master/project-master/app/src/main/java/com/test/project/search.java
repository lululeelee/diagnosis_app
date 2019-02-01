package com.test.project;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.test.project.db.databasehelper;
import com.test.project.db.AssetsDatabaseManager;
import com.test.project.db.database;
import com.test.project.db.disease;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class search extends AppCompatActivity {

    TextView textView,Search_result;
    Spinner spinner,spinner1,spinner2,spinner3,spinner4,spinner5;
    Button b1_summit,b2_reset,b3_search,b4_sub,b5_save;

    databasehelper mycalendar;
    boolean data_existed;
    ArrayAdapter<CharSequence> adapter;
    //  String selectedSys;
    String text,text1,text2,text3,text4,text5;
    String datetoday = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AssetsDatabaseManager.initManager(getApplication());
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        database db = new database();
        db.setData(mg.getDatabase("project.db"));
        setContentView(R.layout.activity_search);
        textView=(TextView)findViewById(R.id.textView2);
        textView.setText("");
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner3 = (Spinner)findViewById(R.id.spinner3);
        spinner4 = (Spinner)findViewById(R.id.spinner4);
        spinner5 = (Spinner)findViewById(R.id.spinner5);
        mycalendar = new databasehelper(this);

        Search_result = (TextView)findViewById(R.id.textView3);

        b1_summit=(Button)findViewById(R.id.Submit_button);
        b2_reset=(Button)findViewById(R.id.Reset_button);
        b3_search=(Button)findViewById(R.id.Search_button);
        b4_sub=(Button)findViewById(R.id.sub_button);
        b5_save=(Button)findViewById(R.id.save_button);

        List<String> handLable = db.symptom("Hand");
        ArrayAdapter<String> handData = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,handLable);
        List<String> headLable = db.symptom("head");
        ArrayAdapter<String> headData = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,headLable);
        List<String> chestLable = db.symptom("chest");
        ArrayAdapter<String> chestData = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,chestLable);
        List<String> otherLable = db.symptom("other");
        ArrayAdapter<String> otherData = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,otherLable);
        List<String> skinLable = db.symptom("skin");
        ArrayAdapter<String> skinData = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,skinLable);
        List<String> urinaryLable = db.symptom("urinary");
        ArrayAdapter<String> urinaryData = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,urinaryLable);
        spinner.setAdapter(headData);
        spinner1.setAdapter(chestData);
        spinner2.setAdapter(urinaryData);
        spinner3.setAdapter(skinData);
        spinner4.setAdapter(handData);
        spinner5.setAdapter(otherData);

        Cursor res = mycalendar.checkdate(datetoday);

        if(res.getCount()==0){
            //   showMessage("Empty","New data");
            data_existed=false;
        }
        else{

            //   StringBuffer buffer = new StringBuffer();
            res.moveToNext();
            data_existed=true;
        }


        b1_summit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView=(TextView)findViewById(R.id.textView2);
                if (textView.getText().toString().equals(null)) {
                    textView.setText("");
                }
                if(spinner.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text)==false) {
                    textView.append(text);
                    textView.append(",");
                }
                if(spinner1.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text1)==false) {
                    textView.append(text1);
                    textView.append(",");
                }
                if(spinner2.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text2)==false) {
                    textView.append(text2);
                    textView.append(",");
                }
                if(spinner3.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text3)==false) {
                    textView.append(text3);
                    textView.append(",");
                }
                if(spinner4.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text4)==false) {
                    textView.append(text4);
                    textView.append(",");
                }
                if(spinner5.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text5)==false) {
                    textView.append(text5);
                    textView.append(",");
                }

                // selectedSys=text+text1+text2;
                // spinner.setSelection(0);
                // spinner1.setSelection(0);
                // spinner2.setSelection(0);
            }
        });
        b2_reset.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView=(TextView)findViewById(R.id.textView2);
                textView.setText("");
                Search_result.setText("");
                spinner.setSelection(0);
                spinner1.setSelection(0);
                spinner2.setSelection(0);
                spinner3.setSelection(0);
                spinner4.setSelection(0);
                spinner5.setSelection(0);
            }


        });
        b4_sub.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView=(TextView)findViewById(R.id.textView2);
              //  textView.setText("");
             //   Search_result.setText("");
                if(spinner.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text)==true) {
                    String temp = "";
                    temp=textView.getText().toString();
                    temp=temp.replace(text+",","");
                    textView.setText(temp);
                }
                if(spinner1.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text1)==true) {
                    String temp = "";
                    temp=textView.getText().toString();
                    temp=temp.replace(text1+",","");
                    textView.setText(temp);
                }
                if(spinner2.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text2)==true) {
                    String temp = "";
                    temp=textView.getText().toString();
                    temp=temp.replace(text2+",","");
                    textView.setText(temp);
                }
                if(spinner3.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text3)==true) {
                    String temp = "";
                    temp=textView.getText().toString();
                    temp=temp.replace(text3+",","");
                    textView.setText(temp);
                }
                if(spinner4.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text4)==true) {
                    String temp = "";
                    temp=textView.getText().toString();
                    temp=temp.replace(text4+",","");
                    textView.setText(temp);
                }
                if(spinner5.getSelectedItemPosition()!=0 && textView.getText().toString().contains(text5)==true) {
                    String temp = "";
                    temp=textView.getText().toString();
                    temp=temp.replace(text5+",","");
                    textView.setText(temp);
                }





            }


        });
        b3_search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "";
                String line = textView.getText().toString();
                String[] name = line.split(",");
                String cmd = "";
                //Search feunctions
                AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
                database db = new database();
                db.setData(mg.getDatabase("project.db"));


                if (name.length > 0) {
                    cmd = "SELECT * FROM disease WHERE disease.id in (SELECT d_id FROM symptom WHERE symptom.name='" + name[0] + "'";
                    for (int i = 1; i < name.length; ++i) {
                        cmd += " INTERSECT ";
                        cmd += "SELECT d_id FROM symptom WHERE symptom.name='" + name[i] + "'";
                    }
                    cmd += ")";

                    List<disease> D = db.query(cmd);
                    //pseudo ans
                    if (D.size() > 0) {
                        for (disease disease : D) {
                            message += disease.getName() + ", ";
                        }
                    }
                    else{
                        message = "none of the diseases has a symptom like that";
                    }

                    Search_result.setText(message);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please insert a symptom for me", Toast.LENGTH_SHORT).show();
                }
            }


        });
        adddata();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"is selected",Toast.LENGTH_SHORT).show();

                text= spinner.getSelectedItem().toString();

                //  textView=(TextView)findViewById(R.id.textView);
                //  textView.append(text);
                // textView.setText(text);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"is selected",Toast.LENGTH_SHORT).show();
                text1= spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"is selected",Toast.LENGTH_SHORT).show();
                text2= spinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"is selected",Toast.LENGTH_SHORT).show();
                text3= spinner3.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"is selected",Toast.LENGTH_SHORT).show();
                text4= spinner4.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"is selected",Toast.LENGTH_SHORT).show();
                text5= spinner5.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void adddata() {
        b5_save.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!data_existed) {
                    boolean isInserted = mycalendar.insertdata(datetoday,
                            null,
                            null,
                            null,
                            textView.getText().toString(),
                            null);
                    if (isInserted)
                        Toast.makeText(getBaseContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getBaseContext(),"Data Not Inserted", Toast.LENGTH_LONG).show();
                }
                else if(data_existed){
                    boolean isUpdated = mycalendar.updatedata(datetoday,
                            null,
                            null,
                            null,
                            textView.getText().toString(),null
                            );
                    if (isUpdated)
                        Toast.makeText(getBaseContext(), "Data Updated", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getBaseContext(),"Data Not Updated", Toast.LENGTH_LONG).show();
                }
            }

        });

    };
}
