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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.recipx.recipx.MainActivity;
import com.recipx.recipx.R;
import com.recipx.recipx.databinding.ActivityStorageTestBinding;
import com.recipx.recipx.firebase.database.Storage;
import com.recipx.recipx.firebase.user.User;
import com.recipx.recipx.firebase.util.After;

public class StorageTest extends AppCompatActivity {

    String TAG;

    ActivityStorageTestBinding binding;

    Storage storage;

    Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStorageTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TAG = this.getString(R.string.Dirtfy_test);

        storage = new Storage(this);

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

        binding.load.setOnClickListener(view -> {
            file= null;

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            launcher.launch(intent);
        });
        binding.upload.setOnClickListener(view -> {
            storage.upload(file, "user profiles/"+user.getUid(), new After() {
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
        binding.downloadUri.setOnClickListener(view -> {
            file = null;
            storage.downloadUri("user profiles/" + user.getUid(), new After() {
                @Override
                public Object success(Object result) {
                    binding.uri.setText(((Uri) result).toString());
                    Glide.with(StorageTest.this)
                            .load(result)
                            .into(binding.imageView2);
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
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == RESULT_OK)
                    {
                        Log.e(TAG, "result : " + result);
                        Intent intent = result.getData();
                        Log.e(TAG, "intent : " + intent);
                        Uri uri = intent.getData();
                        file = uri;
                        Log.e(TAG, "uri : " + uri);
//                        imageview.setImageURI(uri);
                        Glide.with(StorageTest.this)
                                .load(uri)
                                .into(binding.imageView);
                    }
                }
            });

    void toast(String str){
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}