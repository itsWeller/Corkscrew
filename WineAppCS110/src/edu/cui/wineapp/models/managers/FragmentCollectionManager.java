package edu.cui.wineapp.models.managers;

import edu.cui.wineapp.WineNotesFragment;
import edu.cui.wineapp.WineOverviewFragment;
import edu.cui.wineapp.WineReviewFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentCollectionManager extends FragmentPagerAdapter {
	public FragmentCollectionManager(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = null;

		switch(i){

		//Overview
		case 0: fragment = new WineOverviewFragment(); break;
		//Notes
		case 1: fragment = new WineNotesFragment(); break;
		//Reviews
		case 2: fragment = new WineReviewFragment(); break;
		//More Info
		case 3: fragment = new WineOverviewFragment(); break;	
		}
		return fragment;
	}

	@Override
	public int getCount() {return 4;}

	@Override
	public CharSequence getPageTitle(int position) {
		String tabTitle = null;

		switch(position){
		case 0:tabTitle = "Overview";break;
		case 1:tabTitle = "Notes";break;
		case 2:tabTitle = "Reviews";break;
		case 3:tabTitle = "More Info";break;
		}

		return tabTitle;
	}

}