package com.fenazola.mxcome.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class GuidePagerAdapter extends PagerAdapter {

	private List<ViewGroup> views;

	public GuidePagerAdapter(List<ViewGroup> views) {
		this.views = views;
	}

	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		((ViewPager) view).addView(views.get(position), 0);
		return views.get(position);
	}

	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		return (view == arg1);
	}

	@Override
	public void destroyItem(ViewGroup view, int position, Object arg2) {
		((ViewPager) view).removeView(views.get(position));
	}
}