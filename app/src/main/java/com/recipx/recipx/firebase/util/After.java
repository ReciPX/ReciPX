package com.recipx.recipx.firebase.util;

public interface After {
    Object success(Object task);
    Object fail(Object task);
}
