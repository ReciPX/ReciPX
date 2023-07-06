package com.recipx.recipx.firebase.database;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.recipx.recipx.R;
import com.recipx.recipx.firebase.util.After;

public class Storage {
    private FirebaseStorage mStorage = null;
    private String TAG;
    private Context mContext;

    public Storage(Context mContext) {
        if (mStorage == null)
            this.mStorage = FirebaseStorage.getInstance();
        this.mContext = mContext;
        this.TAG = mContext.getString(R.string.Dirtfy_test);
    }

    public void upload(Uri file, String filepath, After after){
        String path = "firebase.Storage.uploadImage - ";

        StorageReference storageRef = mStorage.getReference();
        StorageReference ref = storageRef.child(filepath+file.getLastPathSegment());
        UploadTask uploadTask = ref.putFile(file);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            after.success(taskSnapshot);
            Log.d(TAG, path+"success");
        }).addOnFailureListener(exception -> {
            after.fail(exception);
            Log.d(TAG, path+"fail");
        });
    }

    public void downloadUri(String filepath, After after){
        String path = "firebase.Storage.uploadImage - ";

        StorageReference storageRef = mStorage.getReference();

        storageRef.child(filepath).getDownloadUrl().addOnSuccessListener(uri -> {
            after.success(uri);
            Log.d(TAG, path+"success");
        }).addOnFailureListener(exception -> {
            after.fail(exception);
            Log.d(TAG, path+"fail");
        });
    }

}
