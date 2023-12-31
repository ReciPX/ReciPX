package com.recipx.recipx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.recipx.recipx.Community.Fragment_community;
import com.recipx.recipx.Favorites.Fragment_Favorites;
import com.recipx.recipx.MyPage.Fragment_MyPage;
import com.recipx.recipx.PX_API.GetInfo;
import com.recipx.recipx.PX_API.Top_PX_Product;
import com.recipx.recipx.Ranking.Fragment_Ranking;

import com.recipx.recipx.databinding.ActivityMainBinding;
import com.recipx.recipx.test.Test;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Button api_test_btn;
    Button px_product_test;
    FragmentTransaction fragmentTransaction;
    FloatingActionButton fab;
    BottomNavigationView main_bottom;
    int currentTab = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.testBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Test.class);
            startActivity(intent);
        });

        api_test_btn = findViewById(R.id.api_test_btn);
        api_test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GetInfo.class);
                startActivity(intent);
            }
        });
        px_product_test = findViewById(R.id.px_product_test);
        px_product_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), Top_PX_Product.class);
                startActivity(it);
            }
        });
        main_bottom = findViewById(R.id.main_bottom);
        main_bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setFragment(item.getItemId());
                return true;
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.floating_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //네비게이션 fab 버튼 누르면 동작하게
                switch(currentTab){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // fragment 설정
    public void setFragment(int n) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(n==R.id.bottom_community) {
            if (currentTab != 1) {
                Fragment_community Community = new Fragment_community();
                fragmentTransaction.replace(R.id.main_frame, Community).commitAllowingStateLoss();
                currentTab = 1;
                fab.setImageResource(R.drawable.upload);
//                searchItem.setVisible(true);
//                NameItem.setVisible(true);
//                TagItem.setVisible(true);
//                nosearch = findViewById(R.id.no_search);
            }
        }
        else if(n== R.id.bottom_ranking) {
            if(currentTab!=2) {
                Fragment_Ranking ranking = new Fragment_Ranking();
                fragmentTransaction.replace(R.id.main_frame, ranking).commit();
                currentTab = 2;
                fab.setImageResource(R.drawable.upload);
//                nosearch = findViewById(R.id.no_search2);
//                searchItem.setVisible(true);
//                NameItem.setVisible(true);
//                TagItem.setVisible(true);
            }
        }
        else if(n==R.id.bottom_favorites) {
            if(currentTab!=3) {
                Fragment_Favorites favorites = new Fragment_Favorites();
                fragmentTransaction.replace(R.id.main_frame, favorites).commit();
                currentTab = 3;
                fab.setImageResource(R.drawable.upload);
//                searchItem.setVisible(false);
//                NameItem.setVisible(false);
//                TagItem.setVisible(false);
            }
        }
        else if(n==R.id.bottom_mypage) {
            if(currentTab!=4) {
                Fragment_MyPage MyPage = new Fragment_MyPage();
                fragmentTransaction.replace(R.id.main_frame, MyPage).commit();
                currentTab = 4;
                fab.setImageResource(R.drawable.upload);
//                searchItem.setVisible(false);
//                NameItem.setVisible(false);
//                TagItem.setVisible(false);
            }
        }
    }
}