package pl.solaris.countries.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.solaris.countries.model.CountryItem;
import pl.solaris.countries.model.Geonames;

public class JSONHelper {
    private static final String TAG = "JSONHelper";


    public static Geonames getGeonames(JSONObject jOb) {
        try {
            GsonBuilder gb = new GsonBuilder();
            Gson gson = gb.create();
            return gson.fromJson(jOb.toString(), Geonames.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<CountryItem> getCountries(String jOb) {
        try {
            GsonBuilder gb = new GsonBuilder();
            Gson gson = gb.create();
            return gson.fromJson(jOb, new TypeToken<ArrayList<CountryItem>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}