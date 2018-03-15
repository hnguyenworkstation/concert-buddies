package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class ClassificationsEntity {
    @Expose
    @SerializedName("family")
    private boolean family;
    @Expose
    @SerializedName("subType")
    private SubtypeEntity subtype;
    @Expose
    @SerializedName("type")
    private TypeEntity type;
    @Expose
    @SerializedName("subGenre")
    private SubgenreEntity subgenre;
    @Expose
    @SerializedName("genre")
    private GenreEntity genre;
    @Expose
    @SerializedName("segment")
    private SegmentEntity segment;
    @Expose
    @SerializedName("primary")
    private boolean primary;

    public boolean getFamily() {
        return family;
    }

    public void setFamily(boolean family) {
        this.family = family;
    }

    public SubtypeEntity getSubtype() {
        return subtype;
    }

    public void setSubtype(SubtypeEntity subtype) {
        this.subtype = subtype;
    }

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public SubgenreEntity getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(SubgenreEntity subgenre) {
        this.subgenre = subgenre;
    }

    public GenreEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreEntity genre) {
        this.genre = genre;
    }

    public SegmentEntity getSegment() {
        return segment;
    }

    public void setSegment(SegmentEntity segment) {
        this.segment = segment;
    }

    public boolean getPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}