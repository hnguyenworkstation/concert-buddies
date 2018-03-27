package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huongnguyen on 3/14/18.
 */

public class EventsEntity {
    @Expose
    @SerializedName("_embedded")
    private NestedEmbeddedEntity Embedded;
    @Expose
    @SerializedName("_links")
    private NestedLinksEntity Links;
    @Expose
    @SerializedName("seatmap")
    private SeatmapEntity seatmap;
    @Expose
    @SerializedName("products")
    private List<ProductsEntity> products;
    @Expose
    @SerializedName("priceRanges")
    private List<PriceRangesEntity> priceranges;
    @Expose
    @SerializedName("pleaseNote")
    private String pleaseNote;
    @Expose
    @SerializedName("info")
    private String info;
    @Expose
    @SerializedName("promoters")
    private List<PromoterEntity> promoterEntities;
    @Expose
    @SerializedName("promoter")
    private PromoterEntity promoterEntity;
    @Expose
    @SerializedName("classifications")
    private List<ClassificationsEntity> classifications;
    @Expose
    @SerializedName("dates")
    private DatesEntity dates;
    @Expose
    @SerializedName("sales")
    private SalesEntity sales;
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

    /* Customized constructor */
    public EventsEntity(String type) {
        this.type = type;
    }

    public NestedEmbeddedEntity getEmbedded() {
        return Embedded;
    }

    public void setEmbedded(NestedEmbeddedEntity Embedded) {
        this.Embedded = Embedded;
    }

    public NestedLinksEntity getLinks() {
        return Links;
    }

    public void setLinks(NestedLinksEntity Links) {
        this.Links = Links;
    }

    public SeatmapEntity getSeatmap() {
        return seatmap;
    }

    public void setSeatmap(SeatmapEntity seatmap) {
        this.seatmap = seatmap;
    }

    public List<ProductsEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsEntity> products) {
        this.products = products;
    }

    public List<PriceRangesEntity> getPriceranges() {
        return priceranges;
    }

    public void setPriceranges(List<PriceRangesEntity> priceranges) {
        this.priceranges = priceranges;
    }

    public List<ClassificationsEntity> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<ClassificationsEntity> classifications) {
        this.classifications = classifications;
    }

    public DatesEntity getDates() {
        return dates;
    }

    public void setDates(DatesEntity dates) {
        this.dates = dates;
    }

    public SalesEntity getSales() {
        return sales;
    }

    public void setSales(SalesEntity sales) {
        this.sales = sales;
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


    public PromoterEntity getPromoterEntity() {
        return promoterEntity;
    }

    public void setPromoterEntity(PromoterEntity promoterEntity) {
        this.promoterEntity = promoterEntity;
    }

    public List<PromoterEntity> getPromoterEntities() {
        return promoterEntities;
    }

    public void setPromoterEntities(List<PromoterEntity> promoterEntities) {
        this.promoterEntities = promoterEntities;
    }

    public boolean isTest() {
        return test;
    }

}