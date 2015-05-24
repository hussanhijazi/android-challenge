package br.com.helabs.flickr.models;

import com.google.gson.annotations.*;

import java.io.Serializable;


public class RecentPhoto implements Serializable{

    private String title;
    private String tags;
    private Long id;
    private String secret;
    private Integer farm;
    private Integer server;
    private String owner; // ID do owner

    @SerializedName("iconserver")
    private Integer iconServer;
    @SerializedName("iconfarm")
    private Integer iconFarm;

    @SerializedName("ownername")
    private String ownerName;

    @SerializedName("url_sq")
    private String thumbURL;

    public void setTags(String tags) {
        if (tags == null || tags.isEmpty()) {
            return;
        }

        this.tags = buildTagsWithHash(tags.split(" "));
    }

    public String getTagsTagged() {
        return buildTagsWithHash(tags.split(" "));
    }

    String buildTagsWithHash(String[] tags) {
        StringBuilder sb = new StringBuilder();

        for (String tag : tags) {
            tag = tag.trim();

            if (!tag.startsWith("#")) {
                tag = "#".concat(tag);
            }

            sb.append(tag).append(" ");
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public boolean hasTags() {
        return tags != null && !tags.isEmpty();
    }

    public boolean hasTitle() {
        return title != null && !title.isEmpty();
    }
    public boolean hasOwnerName() {
        return ownerName != null && !ownerName.isEmpty();
    }

    public boolean hasThumb() {
        return thumbURL != null && !thumbURL.isEmpty();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getFarm() {
        return farm;
    }

    public void setFarm(Integer farm) {
        this.farm = farm;
    }

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getIconServer() {
        return iconServer;
    }

    public void setIconServer(Integer iconServer) {
        this.iconServer = iconServer;
    }

    public Integer getIconFarm() {
        return iconFarm;
    }

    public void setIconFarm(Integer iconFarm) {
        this.iconFarm = iconFarm;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getThumbURL() {
        return thumbURL;
    }

    public void setThumbURL(String thumbURL) {
        this.thumbURL = thumbURL;
    }

    public String getTags() {
        return tags;
    }
}