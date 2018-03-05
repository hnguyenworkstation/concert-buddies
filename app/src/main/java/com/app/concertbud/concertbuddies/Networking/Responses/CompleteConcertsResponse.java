package com.app.concertbud.concertbuddies.Networking.Responses;

import com.app.concertbud.concertbuddies.Networking.Responses.Entities.ResultspageEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 3/5/18.
 */

public class CompleteConcertsResponse {

    @Expose
    @SerializedName("resultsPage")
    private ResultspageEntity resultspage;

    public ResultspageEntity getResultspage() {
        return resultspage;
    }

    public void setResultspage(ResultspageEntity resultspage) {
        this.resultspage = resultspage;
    }
}
