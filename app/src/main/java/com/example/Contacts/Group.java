package com.example.Contacts;

/**
 * Created by Administrator on 2018/3/18.
 */

public class Group {
    private String GroupName;

    private int imageId;

    public Group(String GroupName, int imageId) {
        this.GroupName = GroupName;
        this.imageId = imageId;
    }

    public String getName() {
        return GroupName;
    }

    public void setName(String GroupName) {
        this.GroupName = GroupName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

