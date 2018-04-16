package com.app.debrove.tinpandog.util;

import com.app.debrove.tinpandog.data.Place;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by cp4yin on 2018/4/13.
 * package ：com.app.debrove.tinpandog.util
 * description：
 */

public class GetInfos {

    public static String getPlace(int id) {
        String name = "未知";
        List<Place> list = DataSupport.where("newsId = ?", String.valueOf(id)).find(Place.class);
        L.d("get place info", "list " + list + "size " + list.size());
        for (Place place1 : list) {
            name = place1.getName();
            L.d("get place info", " name " + name);
        }
        return name;
    }
}
