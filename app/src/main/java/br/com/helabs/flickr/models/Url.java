
package br.com.helabs.flickr.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Url implements Serializable{

    @SerializedName("type")

    private String type;


    @SerializedName("_content")

    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
