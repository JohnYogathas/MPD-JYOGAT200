package org.me.gcu.jyogat200v5;
/*JOHN YOGATHAS - S1708974 */

//Imports

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {
    private Button mapButton;
    private String result = "";
    //private String url1 = "";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    LinkedList<PullParser> alist = new LinkedList<PullParser>();
    LinearLayout mainListFeed;
    Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag", "in onCreate");
        // Set up the raw links to the graphical components
        search = (Button) findViewById(R.id.Search);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Items", alist);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsFragment.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Items", alist);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        // More Code goes here
        mainListFeed = (LinearLayout) findViewById(R.id.mainListFeed);
        startProgress();
    }


    public void startProgress() {
        GetAsync getAsync = new GetAsync();
        getAsync.execute();
    }


    private class GetAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(urlSource);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag", "after ready");
                //
                // Now read the data. Make sure that there are no specific hedrs
                // in the data file that you need to ignore.
                // The useful data that you need is in each of the item entries
                //
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                    Log.e("MyTag", inputLine);

                }
                in.close();
                return result;
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //


            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            LinkedList<PullParser> dataList = parseData(s);

            if (dataList != null) {
                Log.e("MyTag", "List not null");
                for (Object o : dataList) {
                    Log.e("MyTag", o.toString());
                }
            } else {
                Log.e("MyTag", "List is null");
            }

            for (int i = 0; i < dataList.size(); i++) {
                Button button = new Button(MainActivity.this);
                button.setBackgroundColor(getColor(R.color.DarkGray));
                button.setText(dataList.get(i).getLocation() + "\n" + dataList.get(i).getMagnitude());
                String geoLat = dataList.get(i).getLatitude();
                String geoLong = dataList.get(i).getLongitude();
                String depth = dataList.get(i).getDepth();
                String magnitude = dataList.get(i).getMagnitude();
                String location = dataList.get(i).getLocation();
                String pubDate = dataList.get(i).getPubDate();
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, DetailedInfo.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Items", dataList);
                        intent.putExtras(bundle);
                        intent.putExtra("geoLat", geoLat);
                        intent.putExtra("geoLong", geoLong);
                        intent.putExtra("depth", depth);
                        intent.putExtra("magnitude", magnitude);
                        intent.putExtra("location", location);
                        intent.putExtra("date", pubDate);
                        startActivity(intent);
                    }
                });
                //Determines which earthquakes are which colour, catgorized by magnitude
                if (Float.parseFloat(dataList.get(i).getMagnitude()) < 1) {
                    button.setTextColor(Color.parseColor("green"));
                } else if (Float.parseFloat(dataList.get(i).getMagnitude()) >= 1 && Float.parseFloat(dataList.get(i).getMagnitude()) <= 1.9) {
                    button.setTextColor(Color.parseColor("yellow"));
                } else if (Float.parseFloat(dataList.get(i).getMagnitude()) > 1.9) {
                    button.setTextColor(Color.parseColor("red"));
                }
                button.setGravity(Gravity.CENTER);
                mainListFeed.addView(button);
                button.setTextSize(20);
            }
        }


        private LinkedList<PullParser> parseData(String notNulled) {

            PullParser item = new PullParser();
            try {
                String dataToParse = notNulled.replace("null", "");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new StringReader(dataToParse));
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    // Found a start tag
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            Log.e("MyTag", "Item Start Tag found");
                            item = new PullParser();

                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            String temp = xpp.nextText();
                            Log.e("MyTag", "Title is " + temp);
                            item.setTitle(temp);

                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            String temp = xpp.nextText();
                            item.setDescription(temp);

                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            String temp = xpp.nextText();
                            item.setLink(temp);

                        } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            String temp = xpp.nextText();
                            item.setPubDate(temp);

                        } else if (xpp.getName().equalsIgnoreCase("category")) {
                            String temp = xpp.nextText();
                            item.setCategory(temp);

                        } else if (xpp.getName().equalsIgnoreCase("geo:lat")) {
                            String temp = xpp.nextText();
                            item.setLatitude(temp);

                        } else if (xpp.getName().equalsIgnoreCase("geo:long")) {
                            String temp = xpp.nextText();
                            item.setLongitude(temp);
                        }


                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {


                        alist.add(item);

                    }


                    // Get the next event
                    eventType = xpp.next();

                }
            } catch (XmlPullParserException ae1) {
                Log.e("MyTag", "Parsing error" + ae1.toString());
            } catch (IOException ae1) {
                Log.e("MyTag", "IO error during parsing");
            }

            Log.e("MyTag", "End document");

            return alist;

        }

    }
}