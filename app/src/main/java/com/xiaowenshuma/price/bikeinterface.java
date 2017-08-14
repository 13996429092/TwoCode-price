package com.xiaowenshuma.price;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Mac on 17/8/10.
 */

public interface bikeinterface <T> {
    long insert(T t);
    List<T> query(String whereClause, String[] whereArgs);
    int query();
}
