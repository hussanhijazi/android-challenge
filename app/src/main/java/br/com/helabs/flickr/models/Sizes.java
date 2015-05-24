package br.com.helabs.flickr.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sizes implements Serializable {

    private List<Size> size = new ArrayList<>();

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

}