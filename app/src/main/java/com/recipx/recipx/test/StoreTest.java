package com.recipx.recipx.test;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.recipx.recipx.R;
import com.recipx.recipx.databinding.ActivityStoreTestBinding;
import com.recipx.recipx.firebase.database.Post;
import com.recipx.recipx.firebase.database.Store;
import com.recipx.recipx.firebase.user.User;
import com.recipx.recipx.firebase.util.After;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StoreTest extends AppCompatActivity {

    ActivityStoreTestBinding binding;

    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User user = new User(this);

        user.login("irsam78@gmail.com", "12345678", new After() {
            @Override
            public Object success(Object result) {
                toast("success");
                return null;
            }

            @Override
            public Object fail(Object result) {
                toast("fail");
                return null;
            }
        });
        user.getProfile();

        store = new Store(this);

        binding.writeBtn.setOnClickListener(view -> {
           String title = binding.writeTitleEt.getText().toString();
           String contents = binding.writeContentsEt.getText().toString();

           Post post = new Post(title, contents);
           post.setUser_uid(user.getUid());
           post.setUser_name(user.getName());
           post.setUser_imageUri(user.getPhotoUrl().toString());
           post.setTimeStamp(Calendar.getInstance().getTimeInMillis());
           post.setLikeCnt(0L);

           store.add(post, new After() {
               @Override
               public Object success(Object result) {
                   toast("success");
                   return null;
               }

               @Override
               public Object fail(Object result) {
                   toast("fail");
                   return null;
               }
           });
        });
        binding.titleSearchBtn.setOnClickListener(view -> {
            String title = binding.titleSearchEt.getText().toString();

            store.getPostByTitle(title, new After() {
                @Override
                public Object success(Object task) {
                    toast("success");
                    ArrayList<Post> result = (ArrayList<Post>) task;
                    Post firstPost = result.get(0);
                    Glide.with(StoreTest.this)
                            .load(firstPost.getUser_imageUri())
                            .into(binding.userImgIv);
                    binding.userNameTv.setText(firstPost.getUser_name());
                    binding.titleTv.setText(firstPost.getTitle());
                    binding.timestampTv.setText(convertMillsToDateTime(firstPost.getTimeStamp()));
                    binding.likeCntTv.setText(firstPost.getLikeCnt().toString());
                    return null;
                }

                @Override
                public Object fail(Object result) {
                    toast("fail");
                    return null;
                }
            });
        });
    }

    public void toast(String str){
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public String convertMillsToDateTime(Long mills) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mills);
        Date currentTime = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_hhmmss", Locale.getDefault());
        return format.format(currentTime);
    }
}