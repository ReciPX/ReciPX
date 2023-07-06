package com.recipx.recipx.firebase.database;

import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String title;
    private String contents;

    private String user_uid;
    private String user_name;
    private String user_imageUri;

    private String timeStamp;
    private Long likeCnt;

    public Post(){}

    public Post(String title, String contents, String user_uid, String user_name, String user_imageUri, String timeStamp, Long likeCnt) {
        this.title = title;
        this.contents = contents;
        this.user_uid = user_uid;
        this.user_name = user_name;
        this.user_imageUri = user_imageUri;
        this.timeStamp = timeStamp;
        this.likeCnt = likeCnt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_imageUri(String user_imageUri) {
        this.user_imageUri = user_imageUri;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setLikeCnt(Long likeCnt) {
        this.likeCnt = likeCnt;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_imageUri() {
        return user_imageUri;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Long getLikeCnt() {
        return likeCnt;
    }

    public Map<String, Object> toHashMap(){
        Map<String, Object> post = new HashMap<>();

        post.put("title", title);
        post.put("contents", contents);
        post.put("user_uid", user_uid);
        post.put("user_name", user_name);
        post.put("user_imageUri", user_imageUri);
        post.put("timeStamp", timeStamp);
        post.put("likeCnt", likeCnt);

        return post;
    }

}
