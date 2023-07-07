package com.recipx.recipx.firebase.util;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.recipx.recipx.firebase.database.Nutrient;
import com.recipx.recipx.firebase.database.Post;
import com.recipx.recipx.firebase.database.Storage;
import com.recipx.recipx.firebase.database.Store;
import com.recipx.recipx.firebase.user.User;

public class Util {

    public static Post calcNutrients(Post post, Context context){
        Store store = new Store(context);

        Long protein = 0L;
        Long fat = 0L;
        Long carbohydrate = 0L;

        for (String name : post.getIngredients()){
            Nutrient nutrient = store.getIngredient(name);

            protein += nutrient.getProtein();
            fat += nutrient.getFat();
            carbohydrate += nutrient.getCarbohydrate();
        }

        post.setProtein(protein);
        post.setFat(fat);
        post.setCarbohydrate(carbohydrate);

        return post;
    }

    public static void createUser(User user, Uri profileImg, Context context){
        Storage storage = new Storage(context);

        storage.upload(profileImg, "user profiles/"+user.getUid(), new After() {
            @Override
            public Object success(Object result) {
                toast("success", context);
                return null;
            }

            @Override
            public Object fail(Object result) {
                toast("fail", context);
                return null;
            }
        });
    }

    public static void toast(String str, Context context){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

}
