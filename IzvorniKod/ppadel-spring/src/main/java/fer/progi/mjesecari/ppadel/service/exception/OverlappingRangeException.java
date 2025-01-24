package fer.progi.mjesecari.ppadel.service.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import fer.progi.mjesecari.ppadel.domain.Rezervacija;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class OverlappingRangeException extends RuntimeException {
    public OverlappingRangeException(Rezervacija r, Rezervacija overlap) {
		super("Range of " + r + " overlaps " + overlap + " .");
	}
}
