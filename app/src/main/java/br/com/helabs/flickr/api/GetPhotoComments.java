package br.com.helabs.flickr.api;

import java.io.Serializable;

import br.com.helabs.flickr.models.CommentsPhoto;

public class GetPhotoComments implements Serializable{
    public CommentsPhoto comments;

    public CommentsPhoto getComments() {
        return comments;
    }

    public void setComments(CommentsPhoto comments) {
        this.comments = comments;
    }
}