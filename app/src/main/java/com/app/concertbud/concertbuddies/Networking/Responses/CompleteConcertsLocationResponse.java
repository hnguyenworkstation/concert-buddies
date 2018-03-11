package com.app.concertbud.concertbuddies.Networking.Responses;

import com.app.concertbud.concertbuddies.Networking.Responses.Entities.ResultsPageEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hungnguyen on 3/11/18.
 */

public class CompleteConcertsLocationResponse {
    @Expose
    @SerializedName("resultsPage")
    private ResultsPageEntity resultspage;

    public ResultsPageEntity getResultsPage() {
        return resultspage;
    }

    public void setResultsPage(ResultsPageEntity resultspage) {
        this.resultspage = resultspage;
    }
}
