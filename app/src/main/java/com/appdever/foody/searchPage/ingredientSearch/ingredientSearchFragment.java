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
import android.widget.CompoundButton;
import android.widget.Toast;

import com.appdever.foody.R;
import com.appdever.foody.databinding.FragmentIngredientSearchBinding;
import com.appdever.foody.manager.JSONObtained;
import com.appdever.foody.manager.KeyStore;
import com.appdever.foody.searchPage.ingEnterSearch.IngEnterSearchActivity;

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

//    private int[] foodIdArray = new int[100];

    List<String> foodIdArray = new ArrayList<String>();
    List<String> vegArray = new ArrayList<String>();

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
//                        Log.d("CHECKBOX", String.valueOf(dialogItem.getIngSelect()));


                        if (dialogItem.getIngSelect()) {

                            totalNewsList.add(new TotalItem(dialogItem.getIngName(), dialogItem.getIdMat(), dialogItem.getIdTypeMat()));

//                            Log.d("OBJECT", String.valueOf(new TotalItem(dialogItem.getIngName())));
//                            Log.d("MEMBER", String.valueOf(totalNewsList.get(0).getTotalFoodName()));
//                            Log.d("ArrayNo", String.valueOf(totalNewsList.size()));

//                            if (String.valueOf(totalNewsList.size()).equals("")) {
//
//                                foodIdArray[0] = Integer.parseInt(dialogItem.getIdMat());
//                            } else {
//                                foodIdArray[totalNewsList.size()] = new int[totalNewsList.size()];
//                                Integer.parseInt(dialogItem.getIdMat());
//                            }

//                            foodIdArray = new int[totalNewsList.size()];
//                            foodIdArray[totalNewsList.size()-1] = Integer.parseInt(dialogItem.getIdMat());

                            foodIdArray.add(dialogItem.getIdMat());

                        } else if (!dialogItem.getIngSelect()) {

                            removeFromList(dialogItem.getIngName());

                        }

//                        Log.d("TOTAL", String.valueOf(totalNewsList.get(0)));
                        Log.d("AMOUNT", String.valueOf(totalNewsList.size()));
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

        // clearTotalListButton
        binding.clearTotalListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalNewsList.clear();
                totalItemRecyclerAdapter.notifyDataSetChanged();
            }
        });


        //-----radio-button------
//        binding.pigRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                foodIdArray.add("2");
//
//            }
//        });

        binding.pigRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("2");
                } else {
                    removeMainFromList("2");
                }
            }
        });

        binding.chickenRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("1");
                } else {
                    removeMainFromList("1");
                }
            }
        });

        binding.cowRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("3");
                } else {
                    removeMainFromList("3");
                }
            }
        });

//        binding.cowRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foodIdArray.add("3");
//            }
//        });

//        binding.fishRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foodIdArray.add("272");
//            }
//        });

        binding.fishRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("272");
                } else {
                    removeMainFromList("272");
                }
            }
        });

        binding.shrimpRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("11");
                } else {
                    removeMainFromList("11");
                }
            }
        });

//        binding.shrimpRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foodIdArray.add("11");
//            }
//        });

        binding.sqiudRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("10");
                } else {
                    removeMainFromList("10");
                }
            }
        });

//        binding.sqiudRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foodIdArray.add("10");
//
//            }
//        });

        binding.shellRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("12");
                } else {
                    removeMainFromList("12");
                }
            }
        });

//        binding.shellRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foodIdArray.add("12");
//            }
//        });

        binding.crabRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("300");
                } else {
                    removeMainFromList("300");
                }
            }
        });
//
//        binding.crabRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foodIdArray.add("300");
//            }
//        });

        binding.hamRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("297");
                } else {
                    removeMainFromList("297");
                }
            }
        });

//        binding.hamRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foodIdArray.add("297");
//            }
//        });

        binding.sausageRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("301");
                } else {
                    removeMainFromList("301");
                }
            }
        });
//        binding.sausageRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foodIdArray.add("301");
//            }
//        });

        binding.eggRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodIdArray.add("255");
                } else {
                    removeMainFromList("255");
                }
            }
        });

//        binding.eggRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foodIdArray.add("255");
//
//            }
//        });

        binding.vegRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.w("VEGCHECK", "YEAH");

                if (isChecked) {

                    for(int i=0; i<vegArray.size(); i++) {
                        foodIdArray.add(vegArray.get(i));
                    }

                } else {
//                    removeMainFromList("302");

                    for(int i=0; i<vegArray.size(); i++) {
                        removeMainFromList(vegArray.get(i));
                    }

                }
            }
        });

//        binding.vegRadioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foodIdArray.add("302");
//
//            }
//        });

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (foodIdArray.size()==0) {

                    Toast.makeText(getContext(), "กรุณาเลือกวัตถุดิบที่ต้องการ", Toast.LENGTH_SHORT).show();

                }else {
                    Intent objIntent = new Intent(getActivity(), IngEnterSearchActivity.class);

                    objIntent.putStringArrayListExtra(KeyStore.ING_MAT_ID_SEND_KEY, (ArrayList<String>) foodIdArray);
                    startActivity(objIntent);

                    Log.d("SEARCHFIRST", String.valueOf(foodIdArray.get(0)));
                }

                Log.d("SEARCHSIZE", String.valueOf(foodIdArray.size()));


            }
        });

        return rootView;
    }

    private void removeFromList(String foodResName) {

        boolean done = false;
        for (int i = 0; i < totalNewsList.size() && !done; i++) {

            String strTarget = totalNewsList.get(i).getTotalFoodName();

            if (strTarget.equals(foodResName)) {
                totalNewsList.remove(i);
                foodIdArray.remove(i);

                done = true;
            }

        }

    }

    private void removeMainFromList(String idRes) {

        boolean done = false;
        for (int i = 0; i < foodIdArray.size() && !done; i++) {

            String strTarget = foodIdArray.get(i);

            if (strTarget.equals(idRes)) {

                foodIdArray.remove(i);

                done = true;
            }

        }

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
                                dialogNewsList.add(new DialogItem(strNameMaterial, checkBoxStatus, strIdMaterial, strIdTypeMaterial));

                                if (strIdTypeMaterial.equals("2")) {
                                    vegArray.add(strIdMaterial);
                                }

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

