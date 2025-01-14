package fer.progi.mjesecari.ppadel.api;

public class createTerenDTO {
    private String naziv;
    private String tip;
    private Long vlasnikTerenaId;
    private String lokacija;

    public String getLokacija() {
        return lokacija;
    }
    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public String getTip() {
        return tip;
    }
    public void setTip(String tip) {
        this.tip = tip;
    }
    public Long getVlasnikTerenaId() {
        return vlasnikTerenaId;
    }
    public void setVlasnikTerenaId(Long vlasnikTerenaId) {
        this.vlasnikTerenaId = vlasnikTerenaId;
    }

}
