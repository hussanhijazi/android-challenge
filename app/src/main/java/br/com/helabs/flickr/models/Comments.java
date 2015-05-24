package br.com.helabs.flickr.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Comments implements Serializable {
    @SerializedName("_content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
