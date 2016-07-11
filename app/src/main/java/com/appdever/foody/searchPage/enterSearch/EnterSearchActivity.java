package com.appdever.foody.searchPage.enterSearch;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appdever.foody.R;
import com.appdever.foody.databinding.ActivityEnterSearchBinding;

import java.util.ArrayList;
import java.util.List;

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
