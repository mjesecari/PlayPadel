package fer.progi.mjesecari.ppadel.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VlasnikDTO{
    private String NazivVlasnik;
    private String Lokacija;
    private String BrojTel;
    private String Role;
    private String Email;

    public VlasnikDTO(@JsonProperty("role") String role, @JsonProperty("email") String email, @JsonProperty("nazivVlasnik") String nazivVlasnik,@JsonProperty("lokacija") String lokacija,@JsonProperty("brojTel") String brojTel) {
        this.NazivVlasnik = nazivVlasnik;
        this.Lokacija = lokacija;
        BrojTel = brojTel;
        this.Role = role;
        this.Email = email;
    }

    public String getNazivVlasnik() {
        return NazivVlasnik;
    }

    public void setNazivVlasnik(String nazivVlasnik) {
        NazivVlasnik = nazivVlasnik;
    }

    public String getLokacija() {
        return Lokacija;
    }

    public void setLokacija(String lokacija) {
        Lokacija = lokacija;
    }

    public String getBrojTel() {
        return BrojTel;
    }

    public void setBrojTel(String brojTel) {
        BrojTel = brojTel;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
