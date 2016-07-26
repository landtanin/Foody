package com.appdever.foody.randomPage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appdever.foody.FoodDetailActivity;
import com.appdever.foody.manager.KeyStore;
import com.appdever.foody.R;
import com.bumptech.glide.Glide;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RandomFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RandomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RandomFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ImageButton button;
    private ImageView img;
    ProgressDialog pDialog;
    Bitmap bitmap;
    private String strFoodPic = "0";
    private String strFoodID = "0";
    private String strFoodName = "0";
    private String strFoodPrepare = "0";
    private String strFoodDescription = "0";
    private String strFoodCooking = "0";
    private String strFoodTypefood = "0";



    private OnFragmentInteractionListener mListener;

    public RandomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment RandomFragment.
     */
    public static RandomFragment newInstance() {
        RandomFragment fragment = new RandomFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_random, container, false);

        button = (ImageButton) rootView.findViewById(R.id.imageButton);
        //img = (ImageView) rootView.findViewById(R.id.imageView3);
        //img = (ImageView) rootView.findViewById(R.id.goProDialogImage);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Random();
               // new LoadImage().execute(strFoodPic);


               /* new LoadImage().execute(strFoodPic);*/


                /*Toast.makeText(getContext(),
                        "Button is clicked", Toast.LENGTH_LONG).show();
*/
            }
        });

        return rootView;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public boolean Random()
    {

    /*    final AlertDialog.Builder ad = new AlertDialog.Builder(getContext());*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        String url = "http://foodyth.azurewebsites.net/foody/random3.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        String resultServer  = getHttpPost(url,params);

        /*** Default Value ***/
        //String strStatusID = "0";
        //String strFoodID = "0";
       // String strFoodName = "0";


        //String strError = "Unknow Status!";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            //strStatusID = c.getString("StatusID");
            strFoodID = c.getString("id_food");
            strFoodName = c.getString("name_food");
            strFoodPic = c.getString("img");
            strFoodPrepare = c.getString("prepare_ingredient");
            strFoodDescription = c.getString("description");
            strFoodCooking = c.getString("cooking_method");
            strFoodTypefood = c.getString("id_typefood");
            //strError = c.getString("Error");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Prepare Login

        if(strFoodID != null)
        {
            // Dialog
          /*  ad.setTitle("อาหารที่ถูกสุ่ม");
            //      ad.setIcon(android.R.drawable.btn_star_big_on);
            ad.setPositiveButton("Close", null);
            //ad.setMessage(strFoodID);
            ad.setMessage(strFoodName);

            ad.show();*/
            builder.setPositiveButton("ดูรายละเอียด", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent objIntent = new Intent(getContext(), FoodDetailActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString(KeyStore.FOODID_DETAIL_SEND_KEY,strFoodID);
                    extras.putString(KeyStore.FOODTYOEID_DETAIL_SEND_KEY,strFoodTypefood);
                    extras.putString(KeyStore.NAMEFOOD_DETAIL_SEND_KEY,strFoodName);
                    extras.putString(KeyStore.FOODMETHOD_DETAIL_SEND_KEY,strFoodCooking);
                    extras.putString(KeyStore.FOODIMG_DETAIL_SEND_KEY,strFoodPic);
                    extras.putString(KeyStore.FOOD_INGREDIENT_SEND_KEY,strFoodPrepare);
                    extras.putString(KeyStore.FOOD_DESCRIPTION_SEND_KEY,strFoodDescription);
                    objIntent.putExtras(extras);
                    startActivity(objIntent);

                    /*Intent i=new Intent(getActivity(), FoodDetailActivity.class);
                    startActivity(i);*/
                }
            }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            final AlertDialog dialog = builder.create();
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.random_menu_popup, null);
            ImageView randomDialogImage= (ImageView) dialogLayout.findViewById(R.id.randomDialogImage);
            Glide.with(getContext()).load(strFoodPic).into(randomDialogImage);
            dialog.setView(dialogLayout);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setTitle(strFoodName);
//            dialog.setMessage(strFoodPic);

            dialog.show();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface d) {

                    ImageView image = (ImageView) dialog.findViewById(R.id.randomDialogImage);
                    Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                            R.drawable.whygoprodialogimage);
                    float imageWidthInPX = (float)image.getWidth();

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                            Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                    image.setLayoutParams(layoutParams);

                }
            });
        }

      /*  else
        {
            Toast.makeText(getContext(), "Login OK", Toast.LENGTH_SHORT).show();
            Intent newActivity = new Intent(getActivity(),MainActivity.class);
            newActivity.putExtra("FoodID","FoodName");
            startActivity(newActivity);
        }*/
        return true;
    }


    public String getHttpPost(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(getContext(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
