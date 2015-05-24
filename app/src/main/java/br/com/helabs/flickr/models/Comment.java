package br.com.helabs.flickr.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable{

    @SerializedName("id")
    private String Id;
    @SerializedName("author")
    private String author;
    @SerializedName("authorname")
    private String authorName;
    @SerializedName("datecreate")
    private String dateCreate;
    @SerializedName("permalink")
    private String permalink;
    @SerializedName("_content")
    private String text;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getText() {
        return text;
    }

    public void setText(String Text) {
        this.text = Text;
    }

}