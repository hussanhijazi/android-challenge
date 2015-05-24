package br.com.helabs.flickr.api;

import java.io.Serializable;

import br.com.helabs.flickr.models.Sizes;

public class GetPhotoSizes implements Serializable{
    public Sizes sizes;


    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }
}