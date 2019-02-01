package com.test.project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class hospital_list extends AppCompatActivity {

    ListView list;
    LocationManager locationManager;
    double X[] = {24.816296, 24.800523, 24.798279, 24.802317, 24.816158, 24.808904, 24.801572, 24.801884, 24.807304};
    double Y[] = {120.980127, 120.990793, 120.965273, 120.969697, 120.962112, 120.955652, 120.963418, 120.962818, 120.976490};
    String name[] = {"國立臺灣大學醫學院附設醫院新竹分院", "馬偕紀念醫院新竹分院", "國泰醫院新竹分院", "南門綜合醫院", "國軍新竹醫院", "和平醫院", "台灣省私立桃園仁愛之家附設新生醫院", "新中興醫院", "惠民醫院"};
    Map<String, Location> hos = new HashMap<>();
    List<String> hos_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        list = findViewById(R.id.hospital_list);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)){
            init();
        }
    }

    private void init(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc;

        for (int i=0; i<X.length; ++i){
            Location temp = new Location("GPS");
            temp.setLatitude(X[i]);
            temp.setLongitude(Y[i]);
            hos.put(name[i],temp);
        }

        try{
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc == null){
                loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            try {
                calDis(loc);
            }catch (Exception e){
                Toast.makeText(this,"please open gps & wait for a sec", Toast.LENGTH_SHORT).show();
                init();
                recreate();
            }
        }catch (SecurityException e){}


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria,true);

        try {
            if (provider != null){
                locationManager.requestLocationUpdates(provider,10,10,loclistener);
            }
            else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,10,loclistener);
            }
        }catch (SecurityException e){}


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(loclistener);
        finish();
    }

    LocationListener loclistener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            calDis(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void calDis(Location now){
        for (Map.Entry<String ,Location> e: hos.entrySet()){
            if(now.distanceTo(e.getValue())<5000){
                hos_list.add(e.getKey());
            }
        }
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hos_list));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Location temp = hos.get(hos_list.get(i));
                popUp(view, temp);
                //googlemap(temp,"d");
            }
        });
    }

    private void googlemap(Location temp, String mode){
        Uri intentUri = Uri.parse("google.navigation:q="+temp.getLatitude()+","+temp.getLongitude()+"&mode="+mode);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void popUp(View view, final Location loc){
        View popview = LayoutInflater.from(hospital_list.this).inflate(R.layout.popuplayout, null, false);
        PopupWindow p = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT, 200, true);
        p.setAnimationStyle(android.R.anim.fade_in);
        p.setBackgroundDrawable(new ColorDrawable(0x00000000));
        p.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button w = popview.findViewById(R.id.walking);
        Button d = popview.findViewById(R.id.car);
        Button b = popview.findViewById(R.id.bike);

        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlemap(loc, "w");
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlemap(loc, "d");
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlemap(loc, "b");
            }
        });
    }
}
