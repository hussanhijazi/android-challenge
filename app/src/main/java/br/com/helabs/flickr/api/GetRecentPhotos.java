package br.com.helabs.flickr.api;

import java.io.Serializable;
import java.util.List;

import br.com.helabs.flickr.models.RecentPhoto;
import br.com.helabs.flickr.models.RecentsPhotos;

public class GetRecentPhotos implements Serializable{

    private RecentsPhotos photos;
    private String stat;
    private static final String OK_STAT = "ok";

    public RecentsPhotos getPhotos() {
        return photos;
    }
    public void setPhotos(RecentsPhotos photos) {
        this.photos = photos;
    }
    public List<RecentPhoto> getPhoto() {
        return photos == null ? null : photos.getPhoto();
    }
    public Integer getPage() {
        return photos == null ? null : photos.getPage();
    }
    public Integer getPages() {
        return photos == null ? null : photos.getPages();
    }
    public Integer getPerPage() {
        return photos == null ? null : photos.getPerPage();
    }
    public Integer getTotal() {
        return photos == null ? null : photos.getTotal();
    }
    public String getStat() {
        return stat;
    }
    public void setStat(String stat) {
        this.stat = stat;
    }
    public boolean isOk() {
        return stat != null && OK_STAT.equals(stat);
    }

}
