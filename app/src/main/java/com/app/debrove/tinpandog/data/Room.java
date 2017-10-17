package com.app.debrove.tinpandog.data;

/**
 * Created by debrove on 2017/10/6.
 * Package Name : com.app.debrove.tinpandog.data
 *
 * 教室实体类
 */

public class Room {

    /**
     * id : 0
     * name : 实C202
     * capacity : 120
     * isUsed : 0
     */

    private int id;
    private String name;
    private int capacity;
    private int isUsed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }
}
