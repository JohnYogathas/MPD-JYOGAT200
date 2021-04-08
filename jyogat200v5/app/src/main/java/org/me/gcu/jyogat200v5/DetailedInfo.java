package org.me.gcu.jyogat200v5;
/*JOHN YOGATHAS - S1708974 */

//Imports

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class DetailedInfo extends AppCompatActivity implements View.OnClickListener {

    LinearLayout DetailedInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        DetailedInfo = (LinearLayout) findViewById(R.id.DetailedInfo);
        Bundle bundle = getIntent().getExtras();

        TextView dateView = new TextView(DetailedInfo.this);
        String dateString = "Origin date & time: " + bundle.getString("date");
        dateView.setText(dateString);
        dateView.setTextColor((getColor(R.color.black)));
        dateView.setTextSize(20);
        dateView.setPadding(0, 10, 0, 10);
        DetailedInfo.addView(dateView);

        TextView locationView = new TextView(DetailedInfo.this);
        String locString = "Location: " + bundle.getString("location");
        locationView.setText(locString);
        locationView.setTextColor((getColor(R.color.black)));
        locationView.setTextSize(20);
        locationView.setPadding(0, 10, 0, 10);
        DetailedInfo.addView(locationView);

        TextView latView = new TextView(DetailedInfo.this);
        String latString = "Latitude: " + bundle.getString("geoLat");
        latView.setText(latString);
        latView.setTextColor((getColor(R.color.black)));
        latView.setTextSize(20);
        latView.setPadding(0, 10, 0, 10);
        DetailedInfo.addView(latView);

        TextView longView = new TextView(DetailedInfo.this);
        String longString = "Longitude: " + bundle.getString("geoLong");
        longView.setText(longString);
        longView.setTextColor((getColor(R.color.black)));
        longView.setTextSize(20);
        longView.setPadding(0, 10, 0, 10);
        DetailedInfo.addView(longView);

        TextView depthView = new TextView(DetailedInfo.this);
        String depthString = "Depth: " + bundle.getString("depth") + "Kilometers";
        depthView.setText(depthString);
        depthView.setTextColor((getColor(R.color.black)));
        depthView.setTextSize(20);
        depthView.setPadding(0, 10, 0, 10);
        DetailedInfo.addView(depthView);

        TextView magnitudeView = new TextView(DetailedInfo.this);
        String magnitudeString = "Magnitude: " + bundle.getString("magnitude");
        magnitudeView.setText(magnitudeString);
        magnitudeView.setTextColor((getColor(R.color.black)));
        magnitudeView.setTextSize(20);
        magnitudeView.setPadding(0, 10, 0, 10);
        DetailedInfo.addView(magnitudeView);

    }

    @Override
    public void onClick(View v) {

    }

}
