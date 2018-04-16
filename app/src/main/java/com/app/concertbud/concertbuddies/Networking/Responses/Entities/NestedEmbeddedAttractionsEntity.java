package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huongnguyen on 3/14/18.
 */
public class NestedEmbeddedAttractionsEntity implements Serializable {
    @Expose
    @SerializedName("_links")
    private NestedEmbeddedLinksEntity Links;
    @Expose
    @SerializedName("upcomingEvents")
    private UpcomingeventsEntity upcomingevents;
    @Expose
    @SerializedName("classifications")
    private List<ClassificationsEntity> classifications;
    @Expose
    @SerializedName("images")
    private List<ImagesEntity> images;
    @Expose
    @SerializedName("externalLinks")
    private ExternallinksEntity externallinksEntity;
    @Expose
    @SerializedName("locale")
    private String locale;
    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("test")
    private boolean test;
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("type")
    private String type;
    @Expose
    @SerializedName("name")
    private String name;

    public NestedEmbeddedLinksEntity getLinks() {
        return Links;
    }

    public void setLinks(NestedEmbeddedLinksEntity Links) {
        this.Links = Links;
    }

    public UpcomingeventsEntity getUpcomingevents() {
        return upcomingevents;
    }

    public void setUpcomingevents(UpcomingeventsEntity upcomingevents) {
        this.upcomingevents = upcomingevents;
    }

    public List<ClassificationsEntity> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<ClassificationsEntity> classifications) {
        this.classifications = classifications;
    }

    public List<ImagesEntity> getImages() {
        return images;
    }

    public void setImages(List<ImagesEntity> images) {
        this.images = images;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}