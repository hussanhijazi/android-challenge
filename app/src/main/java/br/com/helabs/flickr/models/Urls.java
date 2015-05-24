
package br.com.helabs.flickr.models;


import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;


public class Urls implements Serializable {

    @Expose
    private List<Url> url;

    /**
     * @return The url
     */
    public List<Url> getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(List<Url> url) {
        this.url = url;
    }

}
