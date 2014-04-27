package pl.solaris.countries.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.solaris.countries.R;
import pl.solaris.countries.model.Country;
import pl.solaris.countries.model.CountryItem;
import pl.solaris.countries.model.Geonames;
import pl.solaris.countries.service.JSONHelper;


public class MainActivity extends ActionBarActivity {


    private String[] continents = new String[] {"Europe",
            "Asia",
            "North America",
            "Oceania",
            "Africa",
            "South America"};


    private ArrayList<Country> europe;
    private ArrayList<Country> asia;
    private ArrayList<Country> north_america;
    private ArrayList<Country> south_america;
    private ArrayList<Country> oceania;
//    private ArrayList<Country> antartica;
    private ArrayList<Country> africa;

    private HashMap<String, ArrayList<Country>> continentsMap;
    private Geonames geo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadJSONFromAsset();
        findViewById(R.id.btn_atlas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AtlasActivity.class));

            }
        });


        europe = new ArrayList<Country>();
        asia = new ArrayList<Country>();
        north_america = new ArrayList<Country>();
        south_america = new ArrayList<Country>();
        oceania = new ArrayList<Country>();
//        antartica = new ArrayList<Country>();
        africa = new ArrayList<Country>();

        geo = (Geonames) getIntent().getSerializableExtra("geonames");

        if(geo!=null && geo.getGeonames()!=null) {
            for(Country c : geo.getGeonames()) {
                if(c.getContinentName().equals("Europe"))  {
                    europe.add(c);
                }

                else if(c.getContinentName().equals("Africa"))  {
                    africa.add(c);
                }

                else if(c.getContinentName().equals("Asia"))  {
                    asia.add(c);
                }

                else if(c.getContinentName().equals("North America"))  {
                    north_america.add(c);
                }

                else if(c.getContinentName().equals("South America"))  {
                    south_america.add(c);
                }

//                else if(c.getContinentName().equals("Antarctica"))  {
//                    antartica.add(c);
//                }

                 else if(c.getContinentName().equals("Oceania"))  {
                    oceania.add(c);
                }
            }
        }

        continentsMap = new HashMap<String, ArrayList<Country>>();
        continentsMap.put("Africa", africa);
        continentsMap.put("Europe", europe);
        continentsMap.put("Asia", asia);
//        continentsMap.put("Antarctica", antartica);
        continentsMap.put("South America", south_america);
        continentsMap.put("North America", north_america);
        continentsMap.put("Oceania", oceania);

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < continents.length; ++i) {
            list.add(continents[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);


        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(MainActivity.this, ContinentDetails.class);
                i.putExtra("countries", continentsMap.get(continents[position]));
                i.putExtra("continent", continents[position]);
                startActivity(i);
            }
        });

        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("country-codes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            ArrayList<CountryItem> list = JSONHelper.getCountries(json);

            list.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
