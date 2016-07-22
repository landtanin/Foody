package com.appdever.foody.searchPage.ingredientSearch;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.appdever.foody.R;
import com.appdever.foody.databinding.FragmentIngredientSearchBinding;
import com.appdever.foody.searchPage.enterSearch.EnterSearchActivity;



public class ingredientSearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    private OnFragmentInteractionListener mListener;


//    private DeselectableRadioButton pigRadioButton, chickenRadioButton, cowRadioButton,
//            fishRadioButton, shrimpRadioButton, squidRadioButton, eggRadioButton;

    FragmentIngredientSearchBinding binding;

    public ingredientSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment ingredientSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ingredientSearchFragment newInstance() {
        ingredientSearchFragment fragment = new ingredientSearchFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final FragmentIngredientSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredient_search, container, false);

        final View rootView = binding.getRoot();

        binding.chooseOtherResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());

                dialog.setContentView(R.layout.other_resource);

                dialog.setTitle("Custom Dialog");

                dialog.show();

                Button okButton = (Button) dialog.findViewById(R.id.okButtonInOtherResource);

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        binding.pigRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "test", Toast.LENGTH_LONG).show();

            }
        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent objIntent = new Intent(getActivity(), EnterSearchActivity.class);
//                objIntent.putExtra("user", pilotName);
                startActivity(objIntent);

                Log.d("Enter Button","test");


            }
        });

        return rootView;
    }


}
