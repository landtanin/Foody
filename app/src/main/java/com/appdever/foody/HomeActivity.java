package com.appdever.foody;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdever.foody.HomePage.HomeFragment;
import com.appdever.foody.addMenuPage.addMenuFragment;
import com.appdever.foody.database.Member;
import com.appdever.foody.manager.SharedPreference;
import com.appdever.foody.manager.SmartFragmentStatePagerAdapter;
import com.appdever.foody.randomPage.RandomFragment;
import com.appdever.foody.searchPage.SearchFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import io.realm.Realm;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public ViewPager container;
    public TabLayout tabLayout;
    public TextView txtPageName,txtProfileName,txtProfileEmail;
    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    public ImageView imgProfile;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public Button btnLogin, btnRegister,btnLogout,btnEdit;
    public LinearLayout hbgBeforeLogin, hbgAfterLogin;
    public final int myHomeFragment = 0;
    public final int createMenuFragment = 1;
    public final int randomFragment = 2;
    public final int searchFragment = 3;
    public Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initInstance();
        CheckStatus();

    }

    public void initInstance() {

        //-----------Hamburger-start----------

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //-----------Hamburger-end------------

        txtPageName = (TextView) findViewById(R.id.txtPageName);
        txtProfileEmail = (TextView) findViewById(R.id.txtProfileEmail);
        txtProfileName  = (TextView) findViewById(R.id.txtProfileName);

        container = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        hbgBeforeLogin = (LinearLayout) findViewById(R.id.hbgBeforeLogin);
        hbgAfterLogin = (LinearLayout) findViewById(R.id.hbgAfterLogin);

        imgProfile = (ImageView) findViewById(R.id.imgProfile) ;


        btnLogin.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnEdit.setOnClickListener(this);


        MainMenuPagerAdapter menuPagerAdapter = new MainMenuPagerAdapter(getSupportFragmentManager());

        container.setAdapter(menuPagerAdapter);
        container.setOffscreenPageLimit(4);

//        container.setCurrentItem(getIntent().getExtras().getInt("page",0));
        tabLayout.setupWithViewPager(container);
        tabLayout.setClipToPadding(true);

        // assign the integer to each tab on tabLayout
        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);

//            tab.setCustomView(menuPagerAdapter.getTabView(i));

            // set icon with primary color - dark
            tab.setIcon(menuPagerAdapter.image_menu[i]);
            tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);

        }

        tabLayout.getTabAt(myHomeFragment).getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);

        container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {

                    case myHomeFragment:
                        txtPageName.setText(getString(R.string.page_home));
                        break;
                    case createMenuFragment:
                        txtPageName.setText(getString(R.string.page_create));
                        break;
                    case randomFragment:
                        txtPageName.setText(getString(R.string.page_random));
                        break;
                    case searchFragment:
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
                switch (tab.getPosition()) {
                    case myHomeFragment:
                        txtPageName.setText(getString(R.string.page_home));

                        break;
                    case createMenuFragment:
                        txtPageName.setText(getString(R.string.page_create));
                        break;
                    case randomFragment:
                        txtPageName.setText(getString(R.string.page_random));
                        break;
                    case searchFragment:
                        txtPageName.setText(getString(R.string.page_search));
                        break;

                }
                container.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//


    }

    //            --------------- sharedPreference For CheckLogin Start------------
    public void CheckStatus() {
        SharedPreference sharedPreference = new SharedPreference(this);
        if (sharedPreference.getStatus().equals("1")){
            hbgBeforeLogin.setVisibility(View.GONE);
            hbgAfterLogin.setVisibility(View.VISIBLE);

//          Start OnlyCall Member Realm
            member= Realm.getDefaultInstance().where(Member.class).findFirst();
//          End OnlyCall Member Realm
            Toast.makeText(this,member.getName(),Toast.LENGTH_LONG).show();
            txtProfileName.setText(member.getName());
            txtProfileEmail.setText(member.getEmail());

//            Glide.with(this).load(Uri.parse(member.getPic())).into(imgProfile);
//            Glide.with(getApplicationContext()).load(Uri.parse(member.getPic())).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgProfile) {
//                @Override
//                protected void setResource(Bitmap resource) {
//                    RoundedBitmapDrawable circularBitmapDrawable =
//                            RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
//                    circularBitmapDrawable.setCircular(true);
//                    imgProfile.setImageDrawable(circularBitmapDrawable);
//                }
//            });

//          Start การใช้ Glide transformations ทำภาพให้เป็นวงกลม
            Glide.with(this).load(Uri.parse(member.getPic())).bitmapTransform(new CropCircleTransformation(this)).into(imgProfile);
//          End การใช้ Glide transformations ทำภาพให้เป็นวงกลม
        }
        else
        {
            hbgBeforeLogin.setVisibility(View.VISIBLE);
            hbgAfterLogin.setVisibility(View.GONE);

        }

    }
    //    --------------- sharedPreference For CheckLogin End------------

    //    ----------------------------onBackPressed Start----------------
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();

        } else {

            SharedPreference sharedPreference = new SharedPreference(this);
            if (sharedPreference.getStatus().equals("1")) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("ออกจากระบบ?");
                dialog.setIcon(R.drawable.ic_facebook);
                dialog.setCancelable(true);
                dialog.setMessage("คุณต้องการออกจากระบบ หรือ ไม่?");
                dialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
//            ----------------- sharedPreference start------------------------
                        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
                        sharedPreference.setStatus("0");
//            ----------------- sharedPreference end------------------------
                        CheckStatus();
                        finish();
                    }
                });

                dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("ปิดโปรแกรม?");
                dialog.setIcon(R.drawable.ic_facebook);
                dialog.setCancelable(true);
                dialog.setMessage("คุณต้องปิดโปรแกรม หรือ ไม่?");
                dialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);

                        finish();
                    }
                });

                dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();

            }
        }
    }
    //    ----------------------------onBackPressed EnD----------------



    //-----------------------Hamburger-onPostCreate-start------------------------
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                startActivity(new Intent(HomeActivity.this, Login2Activity.class));
                break;
            case R.id.btnRegister:
                startActivity(new Intent(HomeActivity.this, signupActivity.class));
                break;
            case R.id.btnEdit:
                startActivity(new Intent(HomeActivity.this, editProfileActivity.class));
                break;
            case R.id.btnLogout:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("ออกจากระบบ?");
                dialog.setIcon(R.drawable.ic_facebook);
                dialog.setCancelable(true);
                dialog.setMessage("คุณต้องการออกจากระบบ หรือ ไม่?");
                dialog.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
//            ----------------- sharedPreference start------------------------
                        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
                        sharedPreference.setStatus("0");

                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        Realm.getDefaultInstance().deleteAll();


                        realm.commitTransaction();


//                        Realm.getDefaultInstance().deleteAll();


//            ----------------- sharedPreference end------------------------
                        CheckStatus();
                        finish();
                    }
                });

                dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();

                break;
            default:
        }
    }
    //-----------------------Hamburger-onPostCreate-end--------------------------

    public class MainMenuPagerAdapter extends SmartFragmentStatePagerAdapter {

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
            Log.e("item", String.valueOf(position));
            switch (position) {

                case myHomeFragment:
                    return HomeFragment.newInstance();

                case createMenuFragment:
                    return addMenuFragment.newInstance();


                case randomFragment:
                    return RandomFragment.newInstance();


                case searchFragment:
                    return SearchFragment.newInstance();


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
//            Font.setFontFace(txt_menu,0);


            ImageView img_menu = (ImageView) view.findViewById(R.id.img_menu);
            img_menu.setImageResource(image_menu[position]);

            return view;
        }



    }

}
