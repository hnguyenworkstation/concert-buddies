package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class NestedEmbeddedVenuesEntity implements Serializable {

    @Expose
    @SerializedName("_links")
    private NestedEmbeddedLinksEntity Links;
    @Expose
    @SerializedName("upcomingEvents")
    private UpcomingeventsEntity upcomingevents;
    @Expose
    @SerializedName("generalInfo")
    private GeneralinfoEntity generalinfo;
    @Expose
    @SerializedName("accessibleSeatingDetail")
    private String accessibleseatingdetail;
    @Expose
    @SerializedName("parkingDetail")
    private String parkingdetail;
    @Expose
    @SerializedName("boxOfficeInfo")
    private BoxofficeinfoEntity boxofficeinfo;
    @Expose
    @SerializedName("social")
    private SocialEntity social;
    @Expose
    @SerializedName("dmas")
    private List<DmasEntity> dmas;
    @Expose
    @SerializedName("markets")
    private List<MarketsEntity> markets;
    @Expose
    @SerializedName("location")
    private LocationEntity location;
    @Expose
    @SerializedName("address")
    private AddressEntity address;
    @Expose
    @SerializedName("country")
    private CountryEntity country;
    @Expose
    @SerializedName("state")
    private StateEntity state;
    @Expose
    @SerializedName("city")
    private CityEntity city;
    @Expose
    @SerializedName("timezone")
    private String timezone;
    @Expose
    @SerializedName("postalCode")
    private String postalcode;
    @Expose
    @SerializedName("units")
    private String units;
    @Expose
    @SerializedName("distance")
    private double distance;
    @Expose
    @SerializedName("images")
    private List<ImagesEntity> images;
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

    public GeneralinfoEntity getGeneralinfo() {
        return generalinfo;
    }

    public void setGeneralinfo(GeneralinfoEntity generalinfo) {
        this.generalinfo = generalinfo;
    }

    public String getAccessibleseatingdetail() {
        return accessibleseatingdetail;
    }

    public void setAccessibleseatingdetail(String accessibleseatingdetail) {
        this.accessibleseatingdetail = accessibleseatingdetail;
    }

    public String getParkingdetail() {
        return parkingdetail;
    }

    public void setParkingdetail(String parkingdetail) {
        this.parkingdetail = parkingdetail;
    }

    public BoxofficeinfoEntity getBoxofficeinfo() {
        return boxofficeinfo;
    }

    public void setBoxofficeinfo(BoxofficeinfoEntity boxofficeinfo) {
        this.boxofficeinfo = boxofficeinfo;
    }

    public SocialEntity getSocial() {
        return social;
    }

    public void setSocial(SocialEntity social) {
        this.social = social;
    }

    public List<DmasEntity> getDmas() {
        return dmas;
    }

    public void setDmas(List<DmasEntity> dmas) {
        this.dmas = dmas;
    }

    public List<MarketsEntity> getMarkets() {
        return markets;
    }

    public void setMarkets(List<MarketsEntity> markets) {
        this.markets = markets;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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
