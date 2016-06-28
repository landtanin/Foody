//package com.appdever.foody.fragment;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.GridView;
//
//import com.appdever.foody.HomeMenuItem;
//import com.appdever.foody.adapter.HomeAdapter;
//
//import java.util.Arrays;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link AddMenuFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link AddMenuFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AddMenuFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    private GridView mGridView;
//
//    private HomeAdapter mHomeAdapter = null;
//
//
//    String[] menuDescription = {"menu2", "menu3", "menu4"};
//
//    HomeMenuItem[] menuItem = {new HomeMenuItem(menuDescription[0], R.drawable.m2),
//            new HomeMenuItem(menuDescription[1], R.drawable.m3),
//            new HomeMenuItem(menuDescription[2], R.drawable.m4)};
//
//    public AddMenuFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter m1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AddMenuFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AddMenuFragment newInstance(String param1, String param2) {
//        AddMenuFragment fragment = new AddMenuFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View rootView = inflater.inflate(R.layout.fragment_add_menu, container, false);
////        return inflater.inflate(R.layout.fragment_add_menu, container, false);
//
//
//        mHomeAdapter = new HomeAdapter(getActivity(), Arrays.asList(menuItem));
//
//        mGridView = (GridView) rootView.findViewById(R.id.gridView);
//        mGridView.setAdapter(mHomeAdapter);
//
//        return rootView;
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//
//}
