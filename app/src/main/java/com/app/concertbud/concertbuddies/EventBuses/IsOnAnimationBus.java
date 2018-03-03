package com.app.concertbud.concertbuddies.EventBuses;

/**
 * Created by hungnguyen on 3/3/18.
 */

public class IsOnAnimationBus {
    private boolean isOnAnimation;

    public IsOnAnimationBus(boolean isOnAnimation) {
        this.isOnAnimation = isOnAnimation;
    }

    public boolean isOnAnimation() {
        return isOnAnimation;
    }
}
