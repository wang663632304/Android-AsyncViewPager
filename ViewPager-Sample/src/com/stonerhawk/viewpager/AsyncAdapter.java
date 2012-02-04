package com.stonerhawk.viewpager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.viewpagerindicator.TitleProvider;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncAdapter extends PagerAdapter implements TitleProvider
{	
	private static final DateFormat dfTitle = new SimpleDateFormat("E, dd MMM");
	
	private static final int daysDepth = 10;
	private static final int daysSize = daysDepth * 2 + 1;
	
	private static Date[] dates = new Date[ daysSize ];
	private static String[] content = new String[ daysSize ];
	
	/**
	 * Prepare dates for navigation, to past and to future
	 */
	private void prepareDates() 
	{
		Date today = new Date();

		Calendar calPast = Calendar.getInstance();
		Calendar calFuture = Calendar.getInstance();

		calPast.setTime(today);
		calFuture.setTime(today);

		dates[ daysDepth ] = calPast.getTime();
		for (int i = 1; i <= daysDepth; i++) 
		{
			calPast.add( Calendar.DATE, -1 );
			dates[ daysDepth - i ] = calPast.getTime();

			calFuture.add( Calendar.DATE, 1 );
			dates[ daysDepth + i ] = calFuture.getTime();
		}
	}
	
	private void updateView(int position, View view)
	{
		ProgressBar mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
		TextView mDate = (TextView) view.findViewById(R.id.date);
		View mContent = (View) view.findViewById(R.id.content);
		
		final String o = getItem(position);
		
		if (o != null) 
		{
			mProgressBar.setVisibility(View.GONE);
			mDate.setText(o);
			mContent.setVisibility(View.VISIBLE);
		}
		else 
		{
			new LoadContentTask().execute(position, view);

			mContent.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
		}
	}
	
	private View drawView(int position, View view) 
	{
		
		view = mInflater.inflate(R.layout.day_view, null);
		updateView(position, view);
	
		return view;
	}
	
	private class LoadContentTask extends AsyncTask<Object, Object, Object> 
	{
		
		private Integer position;
		private View view;
		
		@Override
		protected Object doInBackground(Object... arg) 
		{
			position = (Integer) arg[0];
			view = (View) arg[1];

			// long-term task is here 			
			try 
			{
				Thread.sleep(3000); // do nothing for 3000 miliseconds (3 second)
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			return getTitle(position);
		}

		protected void onPostExecute(Object result) 
		{
			// process result    	 
			content[position] = (String) result;
			
	    	updateView(position, view);
	    	view.postInvalidate();
	     }

	}
	
	private LayoutInflater mInflater = null;

	public AsyncAdapter(Context context) 
	{
		this.mInflater = LayoutInflater.from(context);
		prepareDates();
	}

	@Override
	public int getCount() 
	{
		return dates.length;
	}
	
	@Override
    public Object instantiateItem(View container, int position) 
	{            
            View v = drawView(position, ( (ViewPager) container).getChildAt(position));
            
            ((ViewPager) container).addView(v);
            
            return v;
    }
	
	@Override
    public void destroyItem(View container, int position, Object object) 
	{
            ((ViewPager) container).removeView((View) object);
    }

	@Override
	public boolean isViewFromObject(View pView, Object pObject) 
	{
		return pView.equals(pObject);
	}

	@Override
	public String getTitle(int position) 
	{
		return dfTitle.format( dates[position] );
	}
	
	public String getItem(int position) 
	{
		return content[position];
	}

	public int getTodayId() 
	{
		return daysDepth;
	}

	public Date getTodayDate() 
	{
		return dates[daysDepth];
	}

}
