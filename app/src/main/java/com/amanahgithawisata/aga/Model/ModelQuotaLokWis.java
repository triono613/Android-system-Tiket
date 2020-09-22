package com.amanahgithawisata.aga.Model;

public class ModelQuotaLokWis {
    public String id;
    public String from_date;
    public String thru_date;
    public String quota;
    public String quota_in;
    public String quota_out;

    public ModelQuotaLokWis(String id, String from_date, String thru_date, String quota, String quota_in, String quota_out) {
        this.id = id;
        this.from_date = from_date;
        this.thru_date = thru_date;
        this.quota = quota;
        this.quota_in = quota_in;
        this.quota_out = quota_out;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getThru_date() {
        return thru_date;
    }

    public void setThru_date(String thru_date) {
        this.thru_date = thru_date;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getQuota_in() {
        return quota_in;
    }

    public void setQuota_in(String quota_in) {
        this.quota_in = quota_in;
    }

    public String getQuota_out() {
        return quota_out;
    }

    public void setQuota_out(String quota_out) {
        this.quota_out = quota_out;
    }
}
