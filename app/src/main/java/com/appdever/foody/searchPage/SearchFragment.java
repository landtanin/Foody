package com.appdever.foody.searchPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appdever.foody.R;
import com.appdever.foody.manager.SmartFragmentStatePagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public NonSwipeableViewPager searchContainer;
    public TabLayout searchTabLayout;

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

    }

    private void initInstance(View rootView) {

        searchContainer = (NonSwipeableViewPager) rootView.findViewById(R.id.searchContainer);
        searchTabLayout = (TabLayout) rootView.findViewById(R.id.searchTabs);

        SearchMainMenuPagerAdapter searchMenuPageAdapter = new SearchMainMenuPagerAdapter(getChildFragmentManager());

        searchContainer.setAdapter(searchMenuPageAdapter);
        searchContainer.setOffscreenPageLimit(2);

        searchTabLayout.setupWithViewPager(searchContainer);
        searchTabLayout.setClipToPadding(true);

        for(int i = 0; i< searchTabLayout.getTabCount();i++) {

            TabLayout.Tab tab = searchTabLayout.getTabAt(i);

            tab.setText(searchMenuPageAdapter.tabString[i]);
        }

        // TODO: set tab color different

//        searchContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        searchTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//                    case 0:
//
//                }
                searchContainer.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        initInstance(rootView);

        return rootView ;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class SearchMainMenuPagerAdapter extends SmartFragmentStatePagerAdapter {

        private SmartFragmentStatePagerAdapter adapterViewPager;
        //        private int[] text_menu = {R.string.tabmenu_course, R.string.tabmenu_register, R.string.tabmenu_news, R.string.tabmenu_activity, R.string.tabmenu_contact};
//        public int[] image_menu = {R.drawable.home, R.drawable.plus, R.drawable.dice, R.drawable.chef};

        public String[] tabString = {"ค้นหาด้วยวัตถุดิบ","เลือกประเภทอาหาร"};

        public SearchMainMenuPagerAdapter(FragmentManager fm) {
            super(fm);
//            adapterViewPager = new SmartFragmentStatePagerAdapter(getActivity().getSupportFragmentManager());
//            adapterViewPager.getRegisteredFragment(searchContainer.getCurrentItem());
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("item", String.valueOf(position));
            switch (position) {

                case 0:
                    return ingredientSearchFragment.newInstance();

                case 1:
                    return SelectFoodFragment.newInstance();

                default:
                    return SearchFragment.newInstance();

            }
        }
        
        @Override
        public int getCount() {

            return tabString.length;

        }
        
//        public View getTabView(int position) {
//
//            View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.tabmenu_main, null);
////            TextView txt_menu = (TextView) view.findViewById(R.id.txt_menu);
////            txt_menu.setText(text_menu[position]);
////
////            Font.setFontFace(txt_menu,0);
//
//
//
//            ImageView img_menu = (ImageView) view.findViewById(R.id.img_menu);
//            img_menu.setImageResource(image_menu[position]);
//
//            return view;
//        }

    }
}
