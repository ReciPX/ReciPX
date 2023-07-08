package com.recipx.recipx.PX_API;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.recipx.recipx.R;

import java.util.ArrayList;
import java.util.List;

public class Top_PX_Product extends AppCompatActivity {
    RecyclerView px_product_recyclerview;
    List<String> titles;
    List<Integer> images;
    PX_Product_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_px_product);
        px_product_recyclerview = findViewById(R.id.px_product_recyclerview);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("aa");
        titles.add("ab");
        titles.add("ac");
        titles.add("ad");
        titles.add("aa");
        titles.add("ab");
        titles.add("ac");
        titles.add("ad");

        images.add(R.drawable.ic_launcher_background);
        images.add(R.drawable.icon_padlock);
        images.add(R.drawable.ic_launcher_background);
        images.add(R.drawable.ic_launcher_background);
        images.add(R.drawable.ic_launcher_background);
        images.add(R.drawable.icon_padlock);
        images.add(R.drawable.ic_launcher_background);
        images.add(R.drawable.ic_launcher_background);

        adapter = new PX_Product_Adapter(this,titles,images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        px_product_recyclerview.setLayoutManager(gridLayoutManager);
        px_product_recyclerview.setAdapter(adapter);
    }
}
