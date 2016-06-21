package com.appdever.foody;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    public ViewPager container;
    public TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        initInstance();

    }

//    public void initInstance(){
//        container = (ViewPager) findViewById(R.id.container);
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        MainMenuPagerAdapter menuPagerAdapter = new MainMenuPagerAdapter(getSupportFragmentManager());
//        container.setAdapter(menuPagerAdapter);
//        container.setOffscreenPageLimit(5);
//        container.setCurrentItem(getIntent().getExtras().getInt("page",0));
//        tabLayout.setupWithViewPager(container);
//        tabLayout.setClipToPadding(true);
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(menuPagerAdapter.getTabView(i));
//        }
//    }
//
//    public class MainMenuPagerAdapter extends SmartFragmentStatePagerAdapter {
//        private SmartFragmentStatePagerAdapter adapterViewPager;
//        private int[] text_menu = {R.string.tabmenu_course, R.string.tabmenu_register, R.string.tabmenu_news, R.string.tabmenu_activity, R.string.tabmenu_contact};
//        private int[] image_menu = {R.drawable.ic_course, R.drawable.ic_register, R.drawable.ic_news, R.drawable.ic_activity, R.drawable.ic_contact};
//
//        public MainMenuPagerAdapter(FragmentManager fm) {
//            super(fm);
//            adapterViewPager = new SmartFragmentStatePagerAdapter(getSupportFragmentManager());
//            adapterViewPager.getRegisteredFragment(container.getCurrentItem());
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position) {
//                case 0:
//                    return CoursePager.newInstance();
//                case 1:
//                    return HomePager.newInstance().setPageName("register");
//                case 2:
//                    return NewsPager.newInstance();
//                case 3:
//                    return HomePager.newInstance().setPageName("activity");
//                case 4:
//                    return ContactPager.newInstance();
//                default:
//                    return HomePager.newInstance().setPageName("home");
//            }
//        }

}
