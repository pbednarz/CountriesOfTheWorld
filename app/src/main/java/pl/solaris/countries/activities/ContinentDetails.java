package pl.solaris.countries.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

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
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.solaris.countries.R;
import pl.solaris.countries.fragment.CountryFragment;
import pl.solaris.countries.model.Country;
import pl.solaris.countries.service.Utils;

public class ContinentDetails extends ActionBarActivity {

    TestFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    private ArrayList<Country> countries;
    private String[] countriesArray;
    private String continent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_details);


        File cacheDir = StorageUtils.getCacheDirectory(this);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.color.transparent)
                        // resource or drawable
//                        .showImageForEmptyUri(R.drawable.flag) // resource or
                        // drawable
//                        .showImageOnFail(R.drawable.flag) // resource
                        // or drawable
                .resetViewBeforeLoading(false)
                        // default
                .delayBeforeLoading(0).cacheInMemory(true)
                        // default
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new FadeInBitmapDisplayer(100))
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(300, 200)
                        // default = device screen dimensions
                .discCacheExtraOptions(300, 200, Bitmap.CompressFormat.PNG,
                        100, null)
                .threadPoolSize(4)
                        // default
                .threadPriority(Thread.MAX_PRIORITY - 1)
                        // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                        // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(18 * 1024 * 1024))
                .memoryCacheSize(18 * 1024 * 1024)
                .memoryCacheSizePercentage(50)
                        // default
                .discCache(new UnlimitedDiscCache(cacheDir))
                        // default
                .discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .imageDecoder(new BaseImageDecoder(false)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().defaultDisplayImageOptions(options).build();

        ImageLoader.getInstance().init(config); // Do it on Application start

        countries = (ArrayList<Country>) getIntent().getSerializableExtra("countries");
        continent = getIntent().getStringExtra("continent");
        countriesArray = new String[countries.size()];
        int i=0;
        for(Country c: countries){
            countriesArray[i++]=c.getCountryName();
        }
        ActionBar actionBar = getSupportActionBar();  //getActionBar() throws it
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(continent);

        mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.continent_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_list:

                MyListAdapter adapter = new MyListAdapter(this,mPager.getCurrentItem() );


//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_list_item_single_choice, countriesArray);
                new AlertDialog.Builder(this)
                        .setSingleChoiceItems(adapter, mPager.getCurrentItem(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                                mPager.setCurrentItem(selectedPosition, true);
                            }})
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        })
                        .show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class TestFragmentAdapter extends FragmentPagerAdapter {

        private int mCount = countries.size();

        public TestFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CountryFragment.newInstance(countries.get(position));
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return countries.get(position).getCountryName();
        }
    }

    private class MyListAdapter extends BaseAdapter{
        private Context mContext;
        private int checked=0;
        public MyListAdapter(Context mContext, int checked){
            this.mContext = mContext;
            this.checked = checked;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return countries.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder myViewHolder;
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_of_country, null);
                myViewHolder = new ViewHolder();
                myViewHolder.txt_icon_name = (CheckedTextView) convertView.findViewById(R.id.text1);
                myViewHolder.imageView = (ImageView) convertView.findViewById(R.id.flag);
                convertView.setTag(myViewHolder);
            }
            else{
                myViewHolder = (ViewHolder) convertView.getTag();
            }


            Country country = countries.get(position);
            myViewHolder.txt_icon_name.setText(country.getCountryName());
            if(position==checked)
                myViewHolder.txt_icon_name.setChecked(true);
            else
                myViewHolder.txt_icon_name.setChecked(false);
            ImageLoader.getInstance().displayImage(Utils.FLAG_SMALL_URL+country.getCountryCode().toLowerCase()+".png", myViewHolder.imageView);
            return convertView;
        }

        class ViewHolder {
            CheckedTextView txt_icon_name;
            ImageView imageView;
        }
    }
}
