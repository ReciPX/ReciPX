package com.recipx.recipx.firebase.database;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.recipx.recipx.R;
import com.recipx.recipx.firebase.util.After;

import java.util.ArrayList;

public class Store {

    private FirebaseFirestore mDb = null;
    private Context mContext;
    private String TAG;

    private final String POST = "Post";

    public Store(Context mContext) {
        if (mDb == null)
            this.mDb = FirebaseFirestore.getInstance();
        this.mContext = mContext;
        this.TAG = mContext.getString(R.string.Dirtfy_test);
    }

    public void add(Post post, After after){
        String path = "firebase.Storage.addPost - ";

        mDb.collection(POST)
                .document()
                .set(post)
                .addOnSuccessListener(documentReference -> {
                    after.success(documentReference);
                    Log.d(TAG, path+"success");
                })
                .addOnFailureListener(e -> {
                    after.fail(e);
                    Log.w(TAG, path+"fail", e);
                });
    }

    public void getAll(String collection, After after){
        String path = "firebase.Storage.getAll - ";

        mDb.collection(collection)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        after.success(task.getResult());
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                        Log.d(TAG, path+"success");
                    } else {
                        after.fail(task);
                        Log.w(TAG, path+"fail", task.getException());
                    }
                });
    }

    public void getPostByTitle(String title, After after){
        String path = "firebase.Storage.getPostByTitle - ";

        mDb.collection(POST)
                .whereEqualTo("title", title)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Post> posts = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            posts.add(document.toObject(Post.class));
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                        after.success(posts);
                        Log.d(TAG, path+"success");
                    } else {
                        after.fail(task.getException());
                        Log.d(TAG, path+"fail", task.getException());
                    }
                });
    }

}
