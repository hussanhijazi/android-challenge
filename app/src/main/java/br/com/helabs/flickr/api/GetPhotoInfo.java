package br.com.helabs.flickr.api;

import java.io.Serializable;

import br.com.helabs.flickr.models.Photo;

public class GetPhotoInfo implements Serializable {


    private Photo photo;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
