package fer.progi.mjesecari.ppadel.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RezervacijaDTO {

    private Long terenId;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime vrijeme;
    
    private String korisnikEmail;

    public String getKorisnikEmail() {
        return korisnikEmail;
    }

    public void setKorisnikEmail(String korisnikEmail) {
        this.korisnikEmail = korisnikEmail;
    }

    public Long getTerenId() {
        return terenId;
    }

    public void setTerenId(Long terenId) {
        this.terenId = terenId;
    }

    public LocalDateTime getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(LocalDateTime vrijeme) {
        this.vrijeme = vrijeme;
    }





}

