package com.lukamaletin.codeshare.model.dto;


public class PhotoDto {

    private String photoUrl;

    private PhotoDto() {
    }

    public PhotoDto(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
