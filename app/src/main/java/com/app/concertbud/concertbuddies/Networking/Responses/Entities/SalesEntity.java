package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huongnguyen on 3/14/18.
 */
public class SalesEntity implements Serializable {

    @Expose
    @SerializedName("presales")
    private List<PresalesEntity> presales;
    @Expose
    @SerializedName("public")
    private PublicEntity Public;

    public List<PresalesEntity> getPresales() {
        return presales;
    }

    public void setPresales(List<PresalesEntity> presales) {
        this.presales = presales;
    }

    public PublicEntity getPublic() {
        return Public;
    }

    public void setPublic(PublicEntity Public) {
        this.Public = Public;
    }

}