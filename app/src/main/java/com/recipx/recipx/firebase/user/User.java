package com.recipx.recipx.firebase.user;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.recipx.recipx.R;
import com.recipx.recipx.firebase.util.After;

import java.util.concurrent.Executor;

public class User {

    private FirebaseAuth mAuth = null;
    private Context mContext;
    private String TAG;

    private String name;
    private String email;
    private Uri photoUrl;

    public User(Context mContext) {
        if (mAuth == null)
            this.mAuth = FirebaseAuth.getInstance();
        this.mContext = mContext;
        this.TAG = mContext.getString(R.string.Dirtfy_test);
    }

    public void create(String email, String password, After after){
        String path = "firebase.User.create - ";

        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        after.success(task);
                        Log.d(TAG, path+"success");
                    } else {
                        after.fail(task);
                        Log.d(TAG, path+"fail");
                    }
                });
    }

    public void delete(After after){
        String path = "firebase.User.delete - ";

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            user.delete().
                    addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            after.success(task);
                            Log.d(TAG, path+"success");
                        } else{
                            after.fail(task);
                            Log.d(TAG, path+"fail");
                        }
                    });
        } else {
            Log.d(TAG, path+"login first!");
        }


    }

    public void login(String email, String password, After after) {
        String path = "firebase.User.login - ";

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, path + "success");
                after.success(task);
            } else {
                Log.w(TAG, path + "fail", task.getException());
                after.fail(task);
            }
        });
    }

    public void logout(){
        String path = "firebase.User.logout - ";

        try {
            mAuth.signOut();
            Log.d(TAG, path+"success");
        }
        catch (Exception e){
            Log.d(TAG, path+"fail");
        }
    }

    public void updateProfile(String name, Uri imgUri, After after){
        String path = "firebase.User.updateProfile - ";

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(imgUri)
                .build();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            after.success(task);
                            Log.d(TAG, path+"success");
                        } else {
                            after.fail(task);
                            Log.d(TAG, path+"fail");
                        }
                    });
        } else {
            Log.d(TAG, path+"login first!");
        }
    }

    public void getProfile(){
        String path = "firebase.User.getProfile - ";

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                name = profile.getDisplayName();
                email = profile.getEmail();
                photoUrl = profile.getPhotoUrl();
            }
        } else {
            Log.d(TAG, path+"login first!");
        }
    }

    public boolean isLogin(){
        return (mAuth.getCurrentUser() != null);
    }

    public String getUid() {
        String path = "firebase.User.getProfile - ";

        if (mAuth.getCurrentUser() != null)
            return mAuth.getCurrentUser().getUid();
        else {
            Log.d(TAG, path+"login first!");
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }
}
