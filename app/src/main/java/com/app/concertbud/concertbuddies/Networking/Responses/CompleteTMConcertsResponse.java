package com.app.concertbud.concertbuddies.Networking.Responses;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EmbeddedEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.LinksEntity;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.PageEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class CompleteTMConcertsResponse implements Serializable {

    @Expose
    @SerializedName("page")
    private PageEntity page;
    @Expose
    @SerializedName("_links")
    private LinksEntity Links;
    @Expose
    @SerializedName("_embedded")
    private EmbeddedEntity Embedded;

    public PageEntity getPage() {
        return page;
    }

    public void setPage(PageEntity page) {
        this.page = page;
    }

    public LinksEntity getLinks() {
        return Links;
    }

    public void setLinks(LinksEntity Links) {
        this.Links = Links;
    }

    public EmbeddedEntity getEmbedded() {
        return Embedded;
    }

    public void setEmbedded(EmbeddedEntity Embedded) {
        this.Embedded = Embedded;
    }
}
