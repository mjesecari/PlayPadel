package fer.progi.mjesecari.ppadel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Administrator extends Korisnik {
    @NotNull
    private Float cijenaClanarine;

    public Administrator() {
        cijenaClanarine = 0.0F;
    }

    public @NotNull Float getCijenaClanarine() {
        return cijenaClanarine;
    }

    public void setCijenaClanarine(@NotNull Float cijenaClanarine) {
        if (cijenaClanarine < 0){
            throw new IllegalArgumentException("Membership price should be a positive number!");
        }
        this.cijenaClanarine = cijenaClanarine;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "cijenaClanarine=" + cijenaClanarine +
                '}';
    }
}
