package com.stonerhawk.viewpager;

import com.viewpagerindicator.TitlePageIndicator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class ViewPagerSampleActivity extends Activity 
{
	private ViewPager viewPager = null;
	private TitlePageIndicator mTitleIndicator;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        viewPager = (ViewPager) findViewById(R.id.viewPager);
		AsyncAdapter adapter = new AsyncAdapter(getApplicationContext());
		viewPager.setAdapter(adapter);
		
		mTitleIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mTitleIndicator.setViewPager(viewPager);
		mTitleIndicator.setCurrentItem(0);
    }

}