package com.recipx.recipx.firebase.util;

public interface After {
    Object success(Object result);
    Object fail(Object result);
}
