package com.appdever.foody.searchPage.ingredientSearch;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.appdever.foody.R;
import com.appdever.foody.databinding.FragmentIngredientSearchBinding;
import com.appdever.foody.manager.JSONObtained;
import com.appdever.foody.searchPage.enterSearch.EnterSearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Response;


public class ingredientSearchFragment extends Fragment {

    private RecyclerView rvDialog, rvTotal;
    private DialogItemRecyclerAdapter dialogRecyclerAdapter;
    private TotalItemRecyclerAdapter totalItemRecyclerAdapter;
    private boolean checkBoxStatus;

    List<DialogItem> dialogNewsList = new ArrayList<>();
    List<TotalItem> totalNewsList = new ArrayList<>();

    private String dialogResultServer;

    private String strIdMaterial, strNameMaterial, strIdTypeMaterial;

    private String ingList = "";

//    private DeselectableRadioButton pigRadioButton, chickenRadioButton, cowRadioButton,
//            fishRadioButton, shrimpRadioButton, squidRadioButton, eggRadioButton;

    FragmentIngredientSearchBinding binding;

    public ingredientSearchFragment() {
        // Required empty public constructor
    }


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

        // total recyclerView
        final StaggeredGridLayoutManager totalList = new StaggeredGridLayoutManager(1, 1);
        rvTotal = (RecyclerView) rootView.findViewById(R.id.rv_total_resource);
        rvTotal.setLayoutManager(totalList);
        totalItemRecyclerAdapter = new TotalItemRecyclerAdapter(getContext(), totalNewsList);
        rvTotal.setAdapter(totalItemRecyclerAdapter);
        rvTotal.setHasFixedSize(true);

        // end total recyclerView

        binding.chooseOtherResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //for recyclerView
                StaggeredGridLayoutManager dialogGridLayout = new StaggeredGridLayoutManager(1, 1);

                final Dialog dialog = new Dialog(getContext());

                dialog.setContentView(R.layout.other_resource);

                //for recyclerView
                rvDialog = (RecyclerView) dialog.findViewById(R.id.rv_other_resource);
                rvDialog.setLayoutManager(dialogGridLayout);
                dialogRecyclerAdapter = new DialogItemRecyclerAdapter(getContext(), dialogNewsList, new DialogItemRecyclerAdapter.dialogListCarrier() {
                    @Override
                    public void dialogOnClickListener(DialogItem dialogItem) {

//                        Log.d("DIALOG", dialogItem.getIngName());
                        Log.d("CHECKBOX", String.valueOf(dialogItem.getIngSelect()));


//                        ingList = dialogItem.getIngName();
                        // TODO: GET THE CHECKED ITEMS
                        if (dialogItem.getIngSelect()) {

                            totalNewsList.add(new TotalItem(dialogItem.getIngName()));
                            Log.d("OBJECT", String.valueOf(new TotalItem(dialogItem.getIngName())));
                            Log.d("MEMBER", String.valueOf(totalNewsList.get(0).getTotalFoodName()));

                        } else if (!dialogItem.getIngSelect()) {
//                            totalNewsList.remove(dialogItem.getIngName());

//                            int a;
//                            Object object = dialogItem.getIngName();
//                            a = totalNewsList.indexOf(new TotalItem(dialogItem.getIngName()));
//                            Log.d("OBJECT2", String.valueOf(new TotalItem(dialogItem.getIngName())));
//                            Log.d("INDEX", String.valueOf(a));
                            boolean done = false;
                            for (int i = 0; i<totalNewsList.size()&&!done; i++) {

                                String strContain = totalNewsList.get(i).getTotalFoodName();
                                if (strContain.equals(dialogItem.getIngName())) {
                                    totalNewsList.remove(i);
                                    done = true;
                                }

                            }

//                            totalNewsList.remove(totalNewsList.contains(totalList.equals(dialogItem.getIngName())));
//                            totalNewsList.remove(a);
                        }

                        Log.d("TOTAL", String.valueOf(totalNewsList.size()));
                        totalItemRecyclerAdapter.notifyDataSetChanged();

                    }
                });

                rvDialog.setAdapter(dialogRecyclerAdapter);
                rvDialog.setHasFixedSize(true);

                dialogConnectDatabase();

//                dialog.setTitle("Custom Dialog");

                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

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

                Log.d("Enter Button", "test");


            }
        });

//        for(int i = 0; i<4;i++) {
//            totalNewsList.add(new TotalItem(String.valueOf(i)));
//        }
//        if(!ingList.equals(""))  totalNewsList.add(new TotalItem(ingList));


        return rootView;
    }

    private void dialogConnectDatabase() {

//        int getSendKey = getIntent().getExtras().getInt(KeyStore.SELECT_FOOD_SEND_KEY);

        final HttpUrl myurl = HttpUrl.parse(JSONObtained.getAbsoluteUrl("getIngredients.php")).newBuilder().build();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {


                Response response = null;
                try {
                    response = JSONObtained.getInstance().newCall(JSONObtained.getRequest(myurl)).execute();


                    if (response.isSuccessful())

                    {

                        dialogResultServer = response.body().string();

//                        Log.d("xxxxxx", resultServer);

                        JSONArray ingJSONArray;
                        JSONObject ingJSONObject = null;

                        try {
                            ingJSONArray = new JSONArray(dialogResultServer);
                            Log.d("CHECKJSON", ingJSONArray.toString());

                            for (int i = 0; i < ingJSONArray.length(); i++) {

                                ingJSONObject = ingJSONArray.getJSONObject(i);
                                strIdMaterial = ingJSONObject.getString("id_material");
                                strNameMaterial = ingJSONObject.getString("name_material");
                                strIdTypeMaterial = ingJSONObject.getString("id_typematerial");

                                // update data to ArrayList in recycler adapter
                                dialogNewsList.add(new DialogItem(strNameMaterial, checkBoxStatus));

                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                dialogRecyclerAdapter.notifyDataSetChanged();

                super.onPostExecute(s);
            }

        }.execute();

    }

}

