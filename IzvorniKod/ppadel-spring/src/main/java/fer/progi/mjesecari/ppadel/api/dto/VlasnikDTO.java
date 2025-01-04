package fer.progi.mjesecari.ppadel.api.dto;

public class VlasnikDTO extends KorisnikDTO{
    private String NazivVlasnik;
    private String Lokacija;
    private String BrojTel;

    public VlasnikDTO(String role, String email, String nazivVlasnik, String lokacija, String brojTel) {
        super(role, email);
        NazivVlasnik = nazivVlasnik;
        Lokacija = lokacija;
        BrojTel = brojTel;
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
}
