package com.proyecto.appcaruso.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.proyecto.appcaruso.Fragments.Fragment_Login;
import com.proyecto.appcaruso.Fragments.Fragment_Register;





public class AdapterPager extends FragmentStatePagerAdapter {

    int tabCount;

    //Constructor to the class
    public AdapterPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }
    @Override
    public int getCount() {
        return tabCount;
    }
    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                return new Fragment_Login();
            case 1:
                return new Fragment_Register();
            default:
                return null;
        }
    }
}
