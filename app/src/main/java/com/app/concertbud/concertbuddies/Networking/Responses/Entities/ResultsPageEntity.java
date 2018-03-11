package com.app.concertbud.concertbuddies.Networking.Responses.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultsPageEntity {
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("page")
    private int page;
    @Expose
    @SerializedName("perPage")
    private int perpage;
    @Expose
    @SerializedName("totalEntries")
    private int totalentries;
    @Expose
    @SerializedName("results")
    private ResultsEntity results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getTotalentries() {
        return totalentries;
    }

    public void setTotalentries(int totalentries) {
        this.totalentries = totalentries;
    }

    public ResultsEntity getResults() {
        return results;
    }

    public void setResults(ResultsEntity results) {
        this.results = results;
    }
}
