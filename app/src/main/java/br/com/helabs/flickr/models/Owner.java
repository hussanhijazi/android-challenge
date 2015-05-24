
package br.com.helabs.flickr.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Owner implements Serializable{

    @SerializedName("nsid")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("realname")
    private String realname;
    @SerializedName("location")
    private String location;

    @SerializedName("iconserver")
    private String iconServer;
    @SerializedName("iconfarm")
    private String iconFarm;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIconServer() {
        return iconServer;
    }

    public void setIconServer(String iconServer) {
        this.iconServer = iconServer;
    }

    public String getIconFarm() {
        return iconFarm;
    }

    public void setIconFarm(String iconFarm) {
        this.iconFarm = iconFarm;
    }
}
