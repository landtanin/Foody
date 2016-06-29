package com.appdever.foody;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appdever.foody.fragment.HomeFragment;
import com.appdever.foody.manager.SmartFragmentStatePagerAdapter;

public class HomeActivity extends AppCompatWithFont {
    public ViewPager container;
    public TabLayout tabLayout;
    public TextView txtPageName;
    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initInstance();

    }

    public void initInstance(){

        //-----------Hamburger-start----------

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //-----------Hamburger-end-----------


        txtPageName = (TextView) findViewById(R.id.txtPageName);
        container = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        MainMenuPagerAdapter menuPagerAdapter = new MainMenuPagerAdapter(getSupportFragmentManager());
        container.setAdapter(menuPagerAdapter);
        container.setOffscreenPageLimit(4);
//        container.setCurrentItem(getIntent().getExtras().getInt("page",0));
        tabLayout.setupWithViewPager(container);
        tabLayout.setClipToPadding(true);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(menuPagerAdapter.getTabView(i));
            tab.setIcon(menuPagerAdapter.image_menu[i]);
            tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
        }
        container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                        txtPageName.setText(getString(R.string.page_home));

                        break;
                    case 1:
                        txtPageName.setText(getString(R.string.page_create));
                        break;
                    case 2:
                        txtPageName.setText(getString(R.string.page_random));
                        break;
                    case 3:
                        txtPageName.setText(getString(R.string.page_search));
                        break;

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        txtPageName.setText(getString(R.string.page_home));

                        break;
                    case 1:
                        txtPageName.setText(getString(R.string.page_create));
                        break;
                    case 2:
                        txtPageName.setText(getString(R.string.page_random));
                        break;
                    case 3:
                        txtPageName.setText(getString(R.string.page_search));
                        break;

                }
                tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    //Hambergur
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    //

    public class MainMenuPagerAdapter extends SmartFragmentStatePagerAdapter {

        public final int myHomeFragment = 0;
        public final int createMenuFragment = 1;
        public final int randomFragment = 2;
        public final int activityFragment = 3;

        private SmartFragmentStatePagerAdapter adapterViewPager;
//        private int[] text_menu = {R.string.tabmenu_course, R.string.tabmenu_register, R.string.tabmenu_news, R.string.tabmenu_activity, R.string.tabmenu_contact};
        public int[] image_menu = {R.drawable.home, R.drawable.plus, R.drawable.dice, R.drawable.chef};



        public MainMenuPagerAdapter(FragmentManager fm) {
            super(fm);
            adapterViewPager = new SmartFragmentStatePagerAdapter(getSupportFragmentManager());
            adapterViewPager.getRegisteredFragment(container.getCurrentItem());
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case myHomeFragment:
                    return HomeFragment.newInstance();

                case createMenuFragment:
                    return HomeFragment.newInstance();
                // TODO : change HomeFragment to createMenuFragment

                case randomFragment:
                    return HomeFragment.newInstance();
                // TODO : change HomeFragment to randomFragment

                case activityFragment:
                    return HomeFragment.newInstance();
                // TODO : change HomeFragment to activityFragment

                default:
                    return HomeFragment.newInstance();

            }
        }


        @Override
        public int getCount() {

            return image_menu.length;

        }


        public View getTabView(int position) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tabmenu_main, null);
//            TextView txt_menu = (TextView) view.findViewById(R.id.txt_menu);
//            txt_menu.setText(text_menu[position]);
//
//
//            Font.setFontFace(txt_menu,0);



            ImageView img_menu = (ImageView) view.findViewById(R.id.img_menu);
            img_menu.setImageResource(image_menu[position]);

            return view;
        }



    }

}
