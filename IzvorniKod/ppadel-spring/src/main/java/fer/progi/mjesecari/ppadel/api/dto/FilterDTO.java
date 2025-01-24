package fer.progi.mjesecari.ppadel.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterDTO {
    private Float cijenaKotizacijemin;
    private Float getCijenaKotizacijemax;
    private Float nagrademin;
    private Float nagrademax;

    public FilterDTO(@JsonProperty("cijenaKotizacijeMin") Float cijenaKotizacijemin, @JsonProperty("cijenaKotizacijeMax") Float getCijenaKotizacijemax,
                     @JsonProperty("nagradeMin") Float nagrademin, @JsonProperty("nagradeMax") Float nagrademax) {
        this.cijenaKotizacijemin = cijenaKotizacijemin;
        this.getCijenaKotizacijemax = getCijenaKotizacijemax;
        this.nagrademin = nagrademin;
        this.nagrademax = nagrademax;
    }

    public Float getCijenaKotizacijemin() {
        return cijenaKotizacijemin;
    }

    public void setCijenaKotizacijemin(Float cijenaKotizacijemin) {
        this.cijenaKotizacijemin = cijenaKotizacijemin;
    }

    public Float getGetCijenaKotizacijemax() {
        return getCijenaKotizacijemax;
    }

    public void setGetCijenaKotizacijemax(Float getCijenaKotizacijemax) {
        this.getCijenaKotizacijemax = getCijenaKotizacijemax;
    }

    public Float getNagrademin() {
        return nagrademin;
    }

    public void setNagrademin(Float nagrademin) {
        this.nagrademin = nagrademin;
    }

    public Float getNagrademax() {
        return nagrademax;
    }

    public void setNagrademax(Float nagrademax) {
        this.nagrademax = nagrademax;
    }
}
