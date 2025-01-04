package fer.progi.mjesecari.ppadel.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KorisnikDTO {
    private String role;
    private String email;

    public KorisnikDTO(@JsonProperty("role") String role, @JsonProperty("email") String email) {
        this.role = role;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
