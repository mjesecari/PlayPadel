package fer.progi.mjesecari.ppadel.api.dto;

public class KomentarTurnirDTO {
    private String tekst;
    private Long userId;

    // Getters and setters
    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
