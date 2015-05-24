package br.com.helabs.flickr.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RecentsPhotos implements Serializable{
    private List<RecentPhoto> photo;

    private Integer page;
    private Integer pages;
    private Integer total;

    @SerializedName("perpage") private Integer perPage;

    public List<RecentPhoto> getPhoto() {
        return photo;
    }

    public void setPhoto(List<RecentPhoto> photo) {
        this.photo = photo;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}