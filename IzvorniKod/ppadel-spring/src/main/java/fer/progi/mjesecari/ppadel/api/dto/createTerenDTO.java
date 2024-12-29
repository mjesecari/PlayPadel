package fer.progi.mjesecari.ppadel.api.dto;

public class createTerenDTO {
    private String naziv;
    private String tip;
    private Long vlasnikTerenaId;
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
