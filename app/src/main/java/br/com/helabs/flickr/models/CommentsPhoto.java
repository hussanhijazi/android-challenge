package br.com.helabs.flickr.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CommentsPhoto implements Serializable {

    @SerializedName("photo_id")

    private String photoId;

    //private Comment comment;

    private List<Comment> comment;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}
