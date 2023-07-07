package com.recipx.recipx.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.recipx.recipx.LoginActivity;
import com.recipx.recipx.R;
import com.recipx.recipx.databinding.ActivityTestBinding;

public class Test extends AppCompatActivity {

    ActivityTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.storeTestBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, StoreTest.class);
            startActivity(intent);
        });
        binding.storageTestBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, StorageTest.class);
            startActivity(intent);
        });
        binding.userTestBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, UserTest.class);
            startActivity(intent);
        });
    }
}