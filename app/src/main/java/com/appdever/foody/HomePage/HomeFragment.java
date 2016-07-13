package com.appdever.foody.HomePage;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.appdever.foody.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arisak on 23/6/2559.
 */
public class HomeFragment extends Fragment {
    private static final String LOG_TAG = HomeFragment.class.getSimpleName();


//    private String foodGenres;

    private RecyclerView rv;
    private RecyclerAdapter recyclerAdapter;
    List<DataTest01> newsList=new ArrayList<>();

    private ImageView homeFoodBanner,homeFoodBanner2;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private Context mContext;
    private ViewFlipper mViewFlipper;
//    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());


    public HomeFragment() {
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        //-----------------------------------------------------------------------

        mContext = getContext();
        mViewFlipper = (ViewFlipper) rootView.findViewById(R.id.view_flipper);
//        mViewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
//        mViewFlipper.setOutAnimation(getContext(),R.animator.my_duration);

//        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(final View view, final MotionEvent event) {
//                detector.onTouchEvent(event);
//                return true;
//            }
//        });

        //-----------------------------------------------------------------------


        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

//        Toast start
//        int width = size.x;
//        int height = size.y;
//
//        Toast.makeText(getActivity(),"Width =" + width +",Height"+ height,
//                Toast.LENGTH_SHORT).show();
//       Toast end


        StaggeredGridLayoutManager aaa = new StaggeredGridLayoutManager(2,1);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_test01);
        rv.setLayoutManager(aaa);
        recyclerAdapter = new RecyclerAdapter(getActivity(), newsList);
        rv.setAdapter(recyclerAdapter);
//        rv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        rv.setHasFixedSize(true);

        for (int i=0; i < 10; i++){
            newsList.add(new DataTest01(R.drawable.banner_02,"Test".concat(String.valueOf(i))));
        }

        recyclerAdapter.notifyDataSetChanged();
//        Log.e("Kasira", String.valueOf(recyclerAdapter.getItemCount()));

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        homeFoodBanner = (ImageView) getView().findViewById(R.id.homeFoodBanner);
        homeFoodBanner2 = (ImageView) getView().findViewById(R.id.homeFoodBanner2);
        homeFoodBanner.setImageResource(R.drawable.bannertest);
        homeFoodBanner2.setImageResource(R.drawable.m2);


    }
//
//    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            try {
//                // right to left swipe
//                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
//                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
//                    mViewFlipper.showNext();
//                    return true;
//                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
//                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
//                    mViewFlipper.showPrevious();
//                    return true;
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return false;
//        }
//    }
}
