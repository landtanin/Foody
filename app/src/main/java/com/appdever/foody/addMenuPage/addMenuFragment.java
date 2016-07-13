package com.appdever.foody.addMenuPage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdever.foody.HomeActivity;
import com.appdever.foody.R;
//import com.isseiaoki.simplecropview.CropImageView;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class addMenuFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private Button add_meterial,save;
    private ImageView selectimage;
    private EditText field_meterial,field_namefood,field_process;
    private LinearLayout addfield_layout;

    private Bitmap image_food;
    private List<EditText> fieldlist = new ArrayList<EditText>();
    private static final int RESULT_SELECT_IMAGE = 1;

    //private CropImageView mCropView;
    private LinearLayout mRootLayout;

    public addMenuFragment() {
        // Required empty public constructor
    }

    public static addMenuFragment newInstance() {
        addMenuFragment fragment = new addMenuFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        //Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_add_menu, container, false);


        setviewid(rootview);

        selectimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                selectImage();
                //getActivity().setContentView(R.layout.fragment_editimage);

                //mRootLayout = (LinearLayout) getActivity().findViewById(R.id.layout_root);
                //mCropView = (CropImageView) getActivity().findViewById(R.id.cropImageView);


                //if (mCropView.getImageBitmap() == null) {
                //mCropView.setImageResource(R.drawable.sample5);
                //}

            }});


        add_meterial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                LayoutInflater layoutInflater =
                        (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.addmenu_row, null);
                final EditText field = (EditText)addView.findViewById(R.id.field);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    field.setId(View.generateViewId());
                }
                fieldlist.add(field);

                final ImageButton buttonRemove = (ImageButton)addView.findViewById(R.id.delete_field);
                buttonRemove.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                        fieldlist.remove(field);
                    }
                });
                addfield_layout.addView(addView);

            }});
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String material[] = new String[fieldlist.size()+1];
                material[0] = field_meterial.getText().toString();

                for (int i = 1; i <= fieldlist.size(); i++) {
                    //Log.e("field", String.valueOf(fieldlist.get(i).getText().toString()));
                    //Log.e("fieldID", String.valueOf(fieldlist.get(i).getId()));
                    material[i] = fieldlist.get(i-1).getText().toString();
                }

                if(image_food!=null){
                    new Upload(
                            image_food,
                            field_namefood.getText().toString(),
                            field_process.getText().toString(),
                            material
                    ).execute();
                }


            }});



        return rootview;
    }


    private void setviewid(View rootview){
        add_meterial = (Button) rootview.findViewById(R.id.addmaterial_button);
        field_meterial = (EditText) rootview.findViewById(R.id.material_field);
        field_namefood = (EditText) rootview.findViewById(R.id.namefood_field);
        field_process = (EditText) rootview.findViewById(R.id.process_field);
        addfield_layout = (LinearLayout) rootview.findViewById(R.id.addfield_layout);
        selectimage = (ImageView) rootview.findViewById(R.id.addImage_Button);
        save = (Button) rootview.findViewById(R.id.ok_Button);
    }

    private void selectImage() {
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent, RESULT_SELECT_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SELECT_IMAGE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri image = data.getData();
            Bitmap getImage = null;

            try {
                getImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image_food = Bitmap.createScaledBitmap(getImage,(int)(getImage.getWidth()*0.1), (int)(getImage.getHeight()*0.1), true);
            selectimage.setImageBitmap(image_food);

        }
    }

    private class Upload extends AsyncTask<Void, Void, String> {
        private Bitmap image;
        private String namefood;
        private String process;
        private String material[] = new String[fieldlist.size()];

        ProgressDialog loading;

        public Upload(Bitmap image, String namefood, String process,String[] material) {
            this.image = image;
            this.namefood = namefood;
            this.process = process;
            this.material = material;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading = ProgressDialog.show(getActivity(), "Uploading Image", "Please wait...",true,true);
            loading.setCanceledOnTouchOutside(false);
        }

        @Override
        protected String doInBackground(Void... params) {

            /*for (int i = 0; i < material.length; i++) {
                Log.e("sendd", String.valueOf(material[i]));
            }*/
            postHttp http = new postHttp();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //compress the image to jpg format
            this.image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            JSONArray material_json = new JSONArray();

            for (String i:material )
            {
                material_json.put(i);
            }


            RequestBody formBody = new FormBody.Builder()
                    .add("namefood", namefood)
                    .add("process", process)
                    .add("image", encodeImage)
                    .add("material", material_json.toString())
                    .build();

            String response = null;
            try {
                response = http.run("http://foodyth.azurewebsites.net/testAPI/test.php", formBody);
                Log.e("sendd", String.valueOf(String.valueOf(response)));

                return response;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(String s) {
            //show image uploaded
            loading.dismiss();
            Toast.makeText(getActivity().getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
        }
    }

    public class postHttp {
        OkHttpClient client = new OkHttpClient();

        String run(String url, RequestBody body) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }






    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/
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

}
