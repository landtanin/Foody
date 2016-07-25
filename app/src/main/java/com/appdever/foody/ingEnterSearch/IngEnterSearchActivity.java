package com.appdever.foody.ingEnterSearch;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appdever.foody.R;
import com.appdever.foody.databinding.ActivityIngEnterSearchBinding;

public class IngEnterSearchActivity extends AppCompatActivity {

    ActivityIngEnterSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ing_enter_search);

        // -----------------Spinner-------------------------------------
//        binding.enterSearchSpinner.setOnItemSelectedListener(this);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.hard_code_menus, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.enterSearchSpinner.setAdapter(adapter);
        //---------------Spinner-end------------------------------------
    }
}
