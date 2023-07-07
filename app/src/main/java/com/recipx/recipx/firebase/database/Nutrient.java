package com.recipx.recipx.firebase.database;

import com.google.firebase.firestore.DocumentSnapshot;

public class Nutrient {

    private Long protein;
    private Long fat;
    private Long carbohydrate;

    public Nutrient(DocumentSnapshot snapshot) {
        this.protein = (Long) snapshot.get("protein");
        this.fat = (Long) snapshot.get("fat");
        this.carbohydrate = (Long) snapshot.get("carbohydrate");
    }

    public Long getProtein() {
        return protein;
    }

    public Long getFat() {
        return fat;
    }

    public Long getCarbohydrate() {
        return carbohydrate;
    }
}
