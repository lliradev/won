package com.lliramx.won.model;

public enum DeviceType {

    LAPTOP(""),
    DESKTOP("test"),
    MOBILE("test");

    private final String image;

    DeviceType(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
