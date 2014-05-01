package pl.solaris.countries.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cengalabs.flatui.views.FlatButton;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import pl.solaris.countries.R;
import pl.solaris.countries.activities.WebViewActivity;
import pl.solaris.countries.model.Country;
import pl.solaris.countries.service.DataRequest;
import pl.solaris.countries.service.Utils;

/**
 * Created by solaris on 2014-04-27.
 */
public class CountryFragment extends Fragment{

    private Country country;
    private Activity activity;

    public static CountryFragment newInstance(Country country) {
        CountryFragment fragment = new CountryFragment();
        Bundle args = new Bundle();
        args.putSerializable("country", country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        country = (Country) getArguments().get("country");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_country, container, false);

        if(country.getCountryName()!=null && !country.getCountryName().equals(""))
            ((TextView) root.findViewById(R.id.name)).setText(country.getCountryName());
        else
            ((TextView) root.findViewById(R.id.name)).setVisibility(View.GONE);

        if(country.getLanguages()!=null && !country.getLanguages().equals(""))
            ((TextView) root.findViewById(R.id.languages)).setText("Languages: "+country.getLanguages());
        else
            ((TextView) root.findViewById(R.id.languages)).setVisibility(View.GONE);

        if(country.getCurrencyCode()!=null && !country.getCountryCode().equals(""))
            ((TextView) root.findViewById(R.id.currency)).setText("Currency: "+country.getCurrencyCode());
        else
            ((TextView) root.findViewById(R.id.currency)).setVisibility(View.GONE);

        if(country.getPopulation()!=null && !country.getPopulation().equals("") && !country.getPopulation().equals("0"))
            ((TextView) root.findViewById(R.id.population)).setText("Population: "+country.getPopulation());
        else
            ((TextView) root.findViewById(R.id.population)).setVisibility(View.GONE);

        if(country.getAreaInSqKm()!=null && !country.getAreaInSqKm().equals("") && !country.getAreaInSqKm().equals("0"))
            ((TextView) root.findViewById(R.id.area)).setText(Html.fromHtml("Area: "+country.getAreaInSqKm()+" km<sup>2</sup>"));
        else
            ((TextView) root.findViewById(R.id.area)).setVisibility(View.GONE);


        ((FlatButton) root.findViewById(R.id.wikipedia)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, WebViewActivity.class);
                try {
                    i.putExtra("country", country.getCountryName());
                    i.putExtra("url", Utils.WIKIPEDIA_URL + URLEncoder.encode(country.getCountryName(), "UTF-8"));
                    startActivity(i);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        if(country.getCapital()!=null && !country.getCapital().equals(""))
            ((TextView) root.findViewById(R.id.capital)).setText("Capital: "+country.getCapital());
        else
            ((TextView) root.findViewById(R.id.capital)).setVisibility(View.GONE);
        ImageLoader.getInstance().displayImage(Utils.FLAG_NORMAL_URL+country.getCountryCode().toLowerCase()+".png", ((ImageView) root.findViewById(R.id.flag)));
        return root;
    }
}
