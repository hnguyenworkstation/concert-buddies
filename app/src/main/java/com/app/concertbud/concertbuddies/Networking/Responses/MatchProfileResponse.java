package com.app.concertbud.concertbuddies.Networking.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hungnguyen on 4/21/18.
 */

public class MatchProfileResponse implements Serializable {
    @Expose
    @SerializedName("id")
    private String userId;
    @Expose
    @SerializedName("name")
    private String fullName;
    @Expose
    @SerializedName("birthday")
    private String birthday;
    @Expose
    @SerializedName("picture")
    private PictureEntity picture;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    public class PictureEntity {
        @Expose
        @SerializedName("data")
        private DataEntity dataEntity;

        public DataEntity getDataEntity() {
            return dataEntity;
        }

        public void setDataEntity(DataEntity dataEntity) {
            this.dataEntity = dataEntity;
        }
    }

    public class DataEntity {
        @Expose
        @SerializedName("height")
        private int height;
        @Expose
        @SerializedName("width")
        private int width;
        @Expose
        @SerializedName("is_silhouette")
        private boolean isSilhouete;
        @Expose
        @SerializedName("url")
        private String url;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public boolean isSilhouete() {
            return isSilhouete;
        }

        public void setSilhouete(boolean silhouete) {
            isSilhouete = silhouete;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
