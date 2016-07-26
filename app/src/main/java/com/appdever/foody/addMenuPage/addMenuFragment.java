package com.appdever.foody.addMenuPage;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appdever.foody.R;
import com.squareup.picasso.Picasso;
//import com.isseiaoki.simplecropview.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class addMenuFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private Button add_meterial,save;
    private ImageView selectimage;
    private EditText field_meterial,field_namefood,field_process,field_about,count_field;
    private LinearLayout addfield_layout;
    private Spinner type;

    private Bitmap image_food;
    private List<AutoCompleteTextView> fieldlist = new ArrayList<AutoCompleteTextView>();
    private List<AutoCompleteTextView> countlist = new ArrayList<AutoCompleteTextView>();
    private ArrayList<String> mattlist = new ArrayList<String>();
    private ArrayList<String> typefoodlist = new ArrayList<String>();
    private ArrayList<LoremItem> checklist = new ArrayList<LoremItem>();

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


    void getmaterial() {
        /*new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                getHttp http = new getHttp();
                String response = null;
                try {
                    response = http.run("http://foodyth.azurewebsites.net/testAPI/getmaterial.php");
                    return  response;
                } catch (IOException e) {
                    e.printStackTrace();
                    return  null;
                }

            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);
                try {
                        JSONArray data = new JSONArray(string);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject c = null;
                            c = data.getJSONObject(i);

                            mattlist.add(c.getString("name_material"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

        }.execute();*/
        getHttp http = new getHttp();
        String response = null;
        try {
            response = http.run("http://foodyth.azurewebsites.net/testAPI/getmaterial.php");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject getrespon = new JSONObject(response);

            JSONArray getmatt = getrespon.getJSONArray("material");

            for (int i = 0; i < getmatt.length(); i++) {
                JSONObject c = null;
                c = getmatt.getJSONObject(i);

                mattlist.add(c.getString("name_material"));
                LoremItem loremItem = new LoremItem();
                loremItem.setLoremText(c.getString("name_material"));
                //loremItem.setLoremCheck(i == 0);
                checklist.add(loremItem);
            }

            JSONArray gettypefood = getrespon.getJSONArray("typefood");
            for (int i = 0; i < gettypefood.length(); i++) {
                JSONObject c = null;
                c = gettypefood.getJSONObject(i);

                typefoodlist.add(c.getString("typefood"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }







    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_add_menu, container, false);

        getmaterial();


        type = (Spinner) rootview.findViewById(R.id.type_spiner);

        ArrayAdapter<String> adapterThai = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, typefoodlist);
        type.setAdapter(adapterThai);

        /*type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),
                        "Select : " + mattlist.get(position),
                        Toast.LENGTH_SHORT).show();
                Log.e("gg", "onItemSelected: " );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        setviewid(rootview);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line, mattlist);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                rootview.findViewById(R.id.material_field);
        textView.setAdapter(adapter);




        selectimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //selectImage();
                Log.e( "onClick: GGGG",String.valueOf(checklist.size()));
                //getActivity().setContentView(R.layout.fragment_editimage);

                //mRootLayout = (LinearLayout) getActivity().findViewById(R.id.layout_root);
                //mCropView = (CropImageView) getActivity().findViewById(R.id.cropImageView);


                //if (mCropView.getImageBitmap() == null) {
                //mCropView.setImageResource(R.drawable.sample5);
                //}

            }});


        add_meterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_addmenu);
                dialog.setCancelable(true);

                Log.e( "onClick: ", String.valueOf(checklist.size()));
                final CustomAdapter adapter = new CustomAdapter(getContext(),checklist);


                final ListView listView = (ListView) dialog.findViewById(R.id.listView);
                listView.setAdapter(adapter);

                final SearchView searchView = (SearchView) dialog.findViewById(R.id.searchView1);
                searchView.setFocusable(true);
                searchView.setIconified(false);
                searchView.requestFocusFromTouch();
                dialog.show();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String searchQuery) {
                        adapter.filter(searchQuery.toString().trim());
                        //listView.invalidate();
                        //listView.setAdapter(adapter);
                        return true;
                    }

                });
/*
                Button button1 = (Button)dialog.findViewById(R.id.button1);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(getContext()
                                , "Close dialog", Toast.LENGTH_SHORT);
                        dialog.cancel();
                    }
                });*/



                /*if(field_meterial.getText().length()==0){
                    field_meterial.setError("ใส่ข้อมูล");
                    field_meterial.requestFocus();
                }
                else{
                    if(fieldlist.size()!=0){
                        if(fieldlist.get(fieldlist.size()-1).getText().length()>0)
                            addmaterialfield();
                        else
                            fieldlist.get(fieldlist.size()-1).setError("ใส่ข้อมูล");
                    }
                    else {
                        addmaterialfield();
                    }
                }*/



            }});
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String material[] = new String[fieldlist.size()+1];
                material[0] = field_meterial.getText().toString();

                String count[] = new String[fieldlist.size()+1];
                count[0] = count_field.getText().toString();

                for (int i = 1; i <= fieldlist.size(); i++) {
                    //Log.e("field", String.valueOf(fieldlist.get(i).getText().toString()));
                    //Log.e("fieldID", String.valueOf(fieldlist.get(i).getId()));
                    material[i] = fieldlist.get(i-1).getText().toString();
                    count[i] = countlist.get(i-1).getText().toString();
                }

                if(checkfield()){
                    new Upload(
                            image_food,
                            field_namefood.getText().toString(),
                            field_process.getText().toString(),
                            field_about.getText().toString(),
                            material,
                            count
                    ).execute();
                }


            }});



        return rootview;
    }
    private void addmaterialfield(){
        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.addmenu_row, null);
        final AutoCompleteTextView field = (AutoCompleteTextView)addView.findViewById(R.id.field);
        final AutoCompleteTextView countfield = (AutoCompleteTextView) addView.findViewById(R.id.count);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line, mattlist);
        field.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            field.setId(View.generateViewId());
        }
        fieldlist.add(field);
        countlist.add(countfield);

        final ImageButton buttonRemove = (ImageButton)addView.findViewById(R.id.delete_field);
        buttonRemove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((LinearLayout)addView.getParent()).removeView(addView);
                fieldlist.remove(field);
                countlist.remove(countlist);
            }
        });
        addfield_layout.addView(addView);
    }
    private boolean checkfield(){
        if(field_namefood.getText().length()==0){
            field_namefood.setError("ใส่ชื่ออาหาร");
            field_namefood.requestFocus();
            return false;
        }
        if(field_about.getText().length()==0){
            field_about.setError("ใส่ข้อมูล");
            field_about.requestFocus();
            return false;
        }
        if(field_meterial.getText().length()==0){
            field_meterial.setError("ใส่ข้อมูล");
            field_meterial.requestFocus();
            return false;
        }
        if (field_process.getText().length()==0) {
            field_process.setError("ใส่วิธีการทำ");
            field_process.requestFocus();
            return false;
        }
        if(image_food==null){
            Toast.makeText(getContext(), "เลือกรูปภาพ", Toast.LENGTH_SHORT).show();
            selectimage.requestFocus();
            return false;
        }
        return true;
    }
    private void setviewid(View rootview){
        add_meterial = (Button) rootview.findViewById(R.id.addmaterial_button);
        field_meterial = (EditText) rootview.findViewById(R.id.material_field);
        count_field = (EditText) rootview.findViewById(R.id.count_field);
        field_namefood = (EditText) rootview.findViewById(R.id.namefood_field);
        field_process = (EditText) rootview.findViewById(R.id.process_field);
        addfield_layout = (LinearLayout) rootview.findViewById(R.id.addfield_layout);
        selectimage = (ImageView) rootview.findViewById(R.id.addImage_Button);
        save = (Button) rootview.findViewById(R.id.ok_Button);
        field_about = (EditText) rootview.findViewById(R.id.about_field);

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
            image_food = Bitmap.createScaledBitmap(getImage,(int)(getImage.getWidth()*0.2), (int)(getImage.getHeight()*0.2), true);
            Picasso.with(getContext()).load(image).resize(100, 100)
                    .centerCrop().into(selectimage);


        }
    }
    private class Upload extends AsyncTask<Void, Void, String> {
        private Bitmap image;
        private String namefood;
        private String process;
        private String about;
        private String material[] = new String[fieldlist.size()];
        private String count[] = new String[countlist.size()];

        ProgressDialog loading;

        public Upload(Bitmap image, String namefood, String process,String about,String[] material,String[] count) {
            this.image = image;
            this.namefood = namefood;
            this.process = process;
            this.material = material;
            this.count = count;
            this.about = about;
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

            JSONArray count_json = new JSONArray();
            for (String i:count )
            {
                count_json.put(i);
            }


            RequestBody formBody = new FormBody.Builder()
                    .add("namefood", namefood)
                    .add("process", process)
                    .add("image", encodeImage)
                    .add("about",about)
                    .add("material", material_json.toString())
                    .add("count", count_json.toString())
                    .build();

            String response = null;
            try {
                response = http.run("http://foodyth.azurewebsites.net/testAPI/testadd.php", formBody);
                Log.e("sendd", "GG");
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
    public class getHttp {
        OkHttpClient client = new OkHttpClient();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
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
}
class CustomAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<LoremItem> strName;
    ArrayList<LoremItem> arraylist;

    public CustomAdapter(Context context, ArrayList<LoremItem> strName) {
        Log.e( " strName  ", String.valueOf(strName.size()));
        this.mContext= context;
        this.strName = strName;
        arraylist = new ArrayList<LoremItem>();
        arraylist.addAll(strName);
    }

    public int getCount() {
        return strName.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.dialog_listview_addmenu, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.item_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                strName.get(position).setLoremCheck(isChecked);
            }
        });

        viewHolder.item_text.setText(strName.get(position).getLoremText());
        viewHolder.item_check.setChecked(strName.get(position).getLoremCheck());

        return view;
    }
    public void filter(String charText) {
        Log.e( "filter: arraylist  ", String.valueOf(arraylist.size()));
        Log.e( "filter: strName    ", String.valueOf(strName.size()));

        charText = charText.toLowerCase(Locale.getDefault());
        strName.clear();
        //strName.addAll(arraylist);
        if (charText.length() == 0) {
            strName.addAll(arraylist);
        } else {
            strName.clear();
            for (LoremItem postDetail : arraylist) {
                if (charText.length() != 0 && postDetail.getLoremText().toLowerCase(Locale.getDefault()).contains(charText)) {
                    strName.add(postDetail);
                } else if (charText.length() != 0 && postDetail.getLoremText().toLowerCase(Locale.getDefault()).contains(charText)) {
                    strName.add(postDetail);
                }
            }
        }
        notifyDataSetChanged();
    }
    private class ViewHolder {
        public TextView item_text;
        public CheckBox item_check;

        public ViewHolder(View convertView) {
            item_text  = (TextView) convertView.findViewById(R.id.textView3);
            item_check = (CheckBox) convertView.findViewById(R.id.checkmatt);
        }
    }

}
class LoremItem {

    private String loremText;
    private Boolean loremCheck;

    public LoremItem() {
        this.loremText = "";
        loremCheck = false;
    }

    public String getLoremText() {
        return loremText;
    }

    public void setLoremText(String loremText) {
        this.loremText = loremText;
    }

    public Boolean getLoremCheck() {
        return loremCheck;
    }

    public void setLoremCheck(Boolean loremCheck) {
        this.loremCheck = loremCheck;
    }
}