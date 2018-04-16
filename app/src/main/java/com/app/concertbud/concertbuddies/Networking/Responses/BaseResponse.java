package com.app.concertbud.concertbuddies.Networking.Responses;

import android.support.annotation.Keep;

/**
 * Created by hungnguyen on 4/6/18.
 */
@Keep
public class BaseResponse<T> {
    public boolean error;
    public T results;
}
