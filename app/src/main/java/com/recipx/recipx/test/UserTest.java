package com.recipx.recipx.test;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.recipx.recipx.MainActivity;
import com.recipx.recipx.R;
import com.recipx.recipx.databinding.ActivityUserTestBinding;
import com.recipx.recipx.firebase.user.User;
import com.recipx.recipx.firebase.util.After;

public class UserTest extends AppCompatActivity {

    String TAG;

    ActivityUserTestBinding binding;
    User user;

    Uri img_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TAG = this.getString(R.string.Dirtfy_test);

        user = new User(this);

        binding.isLoginBtn.setOnClickListener(view -> {
            if (user.isLogin())
                toast("login");
            else
                toast("not login");
        });
        binding.loginBtn.setOnClickListener(view -> {
            String email = binding.emailEt.getText().toString();
            String password = binding.passwordEt.getText().toString();

            user.login(email, password, new After() {
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
        binding.logoutBtn.setOnClickListener(view -> {
            if (user.isLogin())
                user.logout();
            else
                toast("login first!");
        });
        binding.createBtn.setOnClickListener(view -> {
            String email = binding.emailEt.getText().toString();
            String password = binding.passwordEt.getText().toString();

            user.create(email, password, new After() {
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
        binding.deleteBtn.setOnClickListener(view -> {
            user.delete(new After() {
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

        binding.setImgIv.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            launcher.launch(intent);
        });
        binding.setProfileBtn.setOnClickListener(view -> {
            String name = binding.setNameEt.getText().toString();

            user.updateProfile(name, img_uri, new After() {
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

        binding.getProfileBtn.setOnClickListener(view -> {
            user.getProfile();
            Glide.with(UserTest.this)
                    .load(user.getPhotoUrl())
                    .into(binding.getImgIv);
            binding.getEmailTv.setText(user.getEmail());
            binding.getNameTv.setText(user.getName());
            binding.getUidTv.setText(user.getUid());
        });
    }

    public void toast(String str){
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        Intent intent = result.getData();
                        img_uri = intent.getData();
                        Glide.with(UserTest.this)
                                .load(img_uri)
                                .into(binding.setImgIv);
                        Log.d(TAG, "result ok");
                    }
                }
            });

}