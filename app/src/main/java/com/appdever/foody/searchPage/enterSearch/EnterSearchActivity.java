package com.appdever.foody.searchPage.enterSearch;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appdever.foody.JSONObtained;
import com.appdever.foody.KeyStore;
import com.appdever.foody.R;
import com.appdever.foody.databinding.ActivityEnterSearchBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;

public class EnterSearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ActivityEnterSearchBinding binding;

    private RecyclerView rv;
    private EnterSearchRecyclerAdapter recyclerAdapter;
    List<EnterSearchMenu> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_enter_search);

        binding.enterSearchSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hard_code_menus, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.enterSearchSpinner.setAdapter(adapter);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

//        int width = size.x;
//        int height = size.y;
//
//        Toast.makeText(EnterSearchActivity.this,"Width =" + width +",Height"+ height,
//                Toast.LENGTH_SHORT).show();

        int namefac = 0;
        StaggeredGridLayoutManager aaa = new StaggeredGridLayoutManager(1,1);
        rv = (RecyclerView) findViewById(R.id.rv_enter_search);
        rv.setLayoutManager(aaa);
        recyclerAdapter = new EnterSearchRecyclerAdapter(EnterSearchActivity.this, newsList);
        rv.setAdapter(recyclerAdapter);

        rv.setHasFixedSize(true);
        for (int i=0; i < 20; i++){
            newsList.add(new EnterSearchMenu("Test"));
        }


        connectDatabase();

    }

    private void connectDatabase() {

//        Request request = new Request.Builder().url(JSONObtained.getAbsoluteUrl("food.php" + "?type=ย่าง")).build();
// optional
        int getSendKey = getIntent().getExtras().getInt(KeyStore.SELECT_FOOD_SEND_KEY);

        HttpUrl myurl = HttpUrl.parse(JSONObtained.getAbsoluteUrl("food.php")).newBuilder().addQueryParameter("id_typefood", String.valueOf(getSendKey)).build();

        JSONObtained.getInstance().newCall(JSONObtained.getRequest(myurl)).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("eeeeee", e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("xxxxxx", response.body().string());
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) parent.getChildAt(0)).setTextSize(25);

        String item = parent.getItemAtPosition(position).toString();

        Toast.makeText(EnterSearchActivity.this,item, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
