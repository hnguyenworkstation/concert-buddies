package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by hungnguyen on 3/27/18.
 */
public class EventClusterItemEntity implements ClusterItem {
    private EventsEntity entity;
    private LatLng position;
    private String title;
    private String snippet;

    public EventClusterItemEntity(EventsEntity entity, LatLng position, String title, String snippet) {
        this.entity = entity;
        this.position = position;
        this.title = title;
        this.snippet = snippet;
    }

    public EventsEntity getEntity() {
        return entity;
    }

    public void setEntity(EventsEntity entity) {
        this.entity = entity;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }
}
