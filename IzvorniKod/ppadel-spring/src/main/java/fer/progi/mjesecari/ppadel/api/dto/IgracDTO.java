package fer.progi.mjesecari.ppadel.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fer.progi.mjesecari.ppadel.domain.Korisnik;

public class IgracDTO {
    private String ImeIgrac;
    private String PrezimeIgrac;
    private String BrojTel;
    private String Role;
    private String Email;

    public IgracDTO(@JsonProperty("imeIgrac") String imeIgrac,@JsonProperty("prezimeIgrac") String prezimeIgrac,@JsonProperty("brojTel") String brojTel, @JsonProperty("role") String role, @JsonProperty("email") String email) {
        this.ImeIgrac = imeIgrac;
        this.PrezimeIgrac = prezimeIgrac;
        this.BrojTel = brojTel;
        this.Role = role;
        this.Email = email;
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

    public String getImeIgrac() {
        return ImeIgrac;
    }

    public void setImeIgrac(String imeIgrac) {
        this.ImeIgrac = imeIgrac;
    }

    public String getPrezimeIgrac() {
        return PrezimeIgrac;
    }

    public void setPrezimeIgrac(String prezimeIgrac) {
        this.PrezimeIgrac = prezimeIgrac;
    }

    public String getBrojTel() {
        return BrojTel;
    }

    public void setBrojTel(String brojTel) {
        this.BrojTel = brojTel;
    }
}
