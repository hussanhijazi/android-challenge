
package br.com.helabs.flickr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Photo implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("secret")
    private String secret;

    @SerializedName("server")
    private String server;

    @SerializedName("isfavorite")
    private String isFavorite;

    @SerializedName("license")
    private String license;

    @SerializedName("rotation")
    private String rotation;

    @SerializedName("originalsecret")
    private String originalSecret;

    @SerializedName("originalformat")
    private String originalFormat;

    private Owner owner;
    private Title title;
    private Comments comments;


    //    private String description;
    @Expose
    private Urls urls;

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getSecret() {
        return secret;
    }


    public void setSecret(String secret) {
        this.secret = secret;
    }


    public String getServer() {
        return server;
    }


    public void setServer(String Server) {
        this.server = Server;
    }


    public String getIsFavorite() {
        return isFavorite;
    }


    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }


    public String getLicense() {
        return license;
    }


    public void setLicense(String license) {
        this.license = license;
    }


    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getOriginalSecret() {
        return originalSecret;
    }


    public void setOriginalSecret(String originalSecret) {
        this.originalSecret = originalSecret;
    }

    public String getOriginalFormat() {
        return originalFormat;
    }


    public void setOriginalformat(String originalFormat) {
        this.originalFormat = originalFormat;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
    public String getOwnerPic()
    {
        return "http://farm"+getOwner().getIconFarm()+".staticflickr.com/"+getOwner().getIconServer()+"/buddyicons/"+getOwner().getId()+".jpg";

    }

    public void setOriginalFormat(String originalFormat) {
        this.originalFormat = originalFormat;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }
    //    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//

}
