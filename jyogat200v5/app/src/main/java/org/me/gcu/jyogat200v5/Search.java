package org.me.gcu.jyogat200v5;
/*JOHN YOGATHAS - S1708974 */

//Imports

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Search extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<PullParser> alist;
    private TextView startDate;
    private TextView endDate;
    private Button searchButton;
    private LinearLayout resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        alist = (ArrayList<PullParser>) getIntent().getExtras().getSerializable("Items");
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        resultList = (LinearLayout) findViewById(R.id.resultList);
    }


    @Override
    public void onClick(View v) {
        if (v == searchButton) {
            searchFunction();
        }
    }

    private void searchFunction() {
        SimpleDateFormat enterDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat PullParserDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
        ArrayList<PullParser> inPull = new ArrayList();

        try {
            Date startDateString = enterDateFormat.parse(startDate.getText().toString());
            Date endDateDateString = enterDateFormat.parse(endDate.getText().toString());

            for (int i = 0; i < alist.size(); i++) {
                try {
                    Date currDate = PullParserDateFormat.parse(alist.get(i).getPubDate());
                    if (currDate.after(startDateString) && currDate.before(endDateDateString)) {
                        inPull.add(alist.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            PullParser east = null;
            PullParser west = null;
            PullParser north = null;
            PullParser south = null;
            PullParser magnitude = null;
            PullParser deepestDepth = null;
            PullParser shallowestDepth = null;


            for (int f = 0; f < inPull.size(); f++) {

                if (f == 0) {
                    north = inPull.get(0);
                    south = inPull.get(0);
                    west = inPull.get(0);
                    east = inPull.get(0);
                    deepestDepth = inPull.get(0);
                    shallowestDepth = inPull.get(0);

                } else {
                    if (Float.parseFloat(inPull.get(f).getLatitude()) > Float.parseFloat(north.getLatitude())) {
                        north = inPull.get(f);
                    } else if (Float.parseFloat(inPull.get(f).getLatitude()) < Float.parseFloat(south.getLatitude())) {
                        south = inPull.get(f);
                    } else if (Float.parseFloat(inPull.get(f).getLongitude()) < Float.parseFloat(west.getLongitude())) {
                        west = inPull.get(f);
                    } else if (Float.parseFloat(inPull.get(f).getLongitude()) > Float.parseFloat(east.getLongitude())) {
                        east = inPull.get(f);
                    }

                }
                if (f == 0) {
                    magnitude = inPull.get(0);
                } else if (Float.parseFloat(inPull.get(f).getMagnitude()) > Float.parseFloat(magnitude.getMagnitude())) {
                    magnitude = inPull.get(f);

                }
                if (f == 0) {
                    deepestDepth = inPull.get(0);
                } else if (Float.parseFloat(inPull.get(f).getDepth()) > Float.parseFloat(deepestDepth.getDepth())) {
                    deepestDepth = inPull.get(f);

                }
                if (f == 0) {
                    shallowestDepth = inPull.get(0);
                } else if (Float.parseFloat(inPull.get(f).getDepth()) < Float.parseFloat(shallowestDepth.getDepth())) {
                    shallowestDepth = inPull.get(f);

                }
            }

            //TextViews for output. Make changes to design here if needed
            TextView northText = new TextView(Search.this);
            northText.setText("Most northerly earthquake: " + north.getLocation());
            northText.setTextColor((getColor(R.color.black)));
            TextView southText = new TextView(Search.this);
            southText.setText("Most southerly earthquake: " + south.getLocation());
            southText.setTextColor((getColor(R.color.black)));
            TextView westText = new TextView(Search.this);
            westText.setText("Most westerly earthquake: " + west.getLocation());
            westText.setTextColor((getColor(R.color.black)));
            TextView eastText = new TextView(Search.this);
            eastText.setText("Most easterly earthquake: " + east.getLocation());
            eastText.setTextColor((getColor(R.color.black)));
            TextView magnitudeText = new TextView(Search.this);
            magnitudeText.setText("Largest magnitude earthquake: " + magnitude.getMagnitude());
            magnitudeText.setTextColor((getColor(R.color.black)));
            TextView deepestDepthText = new TextView(Search.this);
            deepestDepthText.setTextColor((getColor(R.color.black)));
            deepestDepthText.setText("Deepest earthquake: " + deepestDepth.getDepth() + "Kilometers");
            TextView shallowestDepthText = new TextView(Search.this);
            shallowestDepthText.setTextColor((getColor(R.color.black)));
            shallowestDepthText.setText("Shallowest earthquake " + shallowestDepth.getDepth() + "Kilometers");

            //
            resultList.addView(northText);
            resultList.addView(eastText);
            resultList.addView(westText);
            resultList.addView(southText);
            resultList.addView(magnitudeText);
            resultList.addView(deepestDepthText);
            resultList.addView(shallowestDepthText);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
