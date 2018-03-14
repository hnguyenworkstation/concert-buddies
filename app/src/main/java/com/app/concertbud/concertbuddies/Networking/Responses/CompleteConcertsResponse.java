package com.app.concertbud.concertbuddies.Networking.Responses;

import com.app.concertbud.concertbuddies.Networking.Responses.Entities.ResultsPageEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huongnguyen on 3/5/18.
 */

public class CompleteConcertsResponse {
    @Expose
    @SerializedName("resultsPage")
    private ResultsPageEntity resultspage;

    public ResultsPageEntity getResultspage() {
        return resultspage;
    }

    public void setResultspage(ResultsPageEntity resultspage) {
        this.resultspage = resultspage;
    }
}
