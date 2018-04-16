package com.app.concertbud.concertbuddies.Helpers;

import android.content.Context;
import android.graphics.Bitmap;

import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.Networking.Responses.Entities.EventClusterItemEntity;
import com.app.concertbud.concertbuddies.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by hungnguyen on 3/27/18.
 */

public class EventClusterRenderer extends DefaultClusterRenderer<EventClusterItemEntity> {
    private final IconGenerator mIconGenerator = new IconGenerator(BaseApplication.getInstance().getBaseContext());
    private final IconGenerator mClusterIconGenerator = new IconGenerator(BaseApplication.getInstance().getBaseContext());

    private Context context;
    private GoogleMap mMap;
    private ClusterManager<EventClusterItemEntity> mClusterManager;

    public EventClusterRenderer(Context context, GoogleMap map,
                               ClusterManager<EventClusterItemEntity> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
        this.mMap = mMap;
        this.mClusterManager = mClusterManager;
    }

    @Override
    protected void onBeforeClusterItemRendered(EventClusterItemEntity rideCluster, MarkerOptions markerOptions) {
        mIconGenerator.setBackground(context.getResources().getDrawable(R.drawable.vector_map_pin));

        Bitmap icon = mIconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
                .title(rideCluster.getTitle())
                .snippet(rideCluster.getSnippet());
    }


    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        // Always render clusters.
        return cluster.getSize() > 1;
    }
}
