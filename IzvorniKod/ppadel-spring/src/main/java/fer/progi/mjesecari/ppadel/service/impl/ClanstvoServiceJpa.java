package fer.progi.mjesecari.ppadel.service.impl;

import fer.progi.mjesecari.ppadel.dao.ClanstvoRepository;
import fer.progi.mjesecari.ppadel.dao.VlasnikRepository;
import fer.progi.mjesecari.ppadel.domain.Clanstvo;
import fer.progi.mjesecari.ppadel.domain.Vlasnik;
import fer.progi.mjesecari.ppadel.service.ClanstvoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClanstvoServiceJpa  implements ClanstvoService {
    @Autowired
    private VlasnikRepository vlasnikRepository;
    @Autowired
    private ClanstvoRepository clanstvoRepository;
    @Override
    public List<Clanstvo> listAll() {
        return clanstvoRepository.findAll();
    }
    @Override
    public List<Vlasnik> listAllPayed() {
        List<Long> paidMembershipIds = clanstvoRepository.findValidMemberships(LocalDateTime.now());
        return vlasnikRepository.findByIdIn(paidMembershipIds);
    }

    @Override
    public List<Vlasnik> listAllUnPayed() {
        List<Long> unpaidMembershipIds = clanstvoRepository.findExpiredMemberships(LocalDateTime.now());
        return vlasnikRepository.findByIdIn(unpaidMembershipIds);
    }

    @Override
    public Clanstvo CreateClanstvo(Long id) {
        Clanstvo clanstvo = new Clanstvo();
        clanstvo.setId(id);
        System.out.println(id);
        return clanstvoRepository.save(clanstvo);

    }

    @Override
    public Clanstvo UpdateClanstvo(Long id, double total, String method) {
        return clanstvoRepository.findById(id)
                .map(clanstvo -> {
                    clanstvo.setIznos(total);
                    clanstvo.setDatumIsteka(LocalDate.now().plusYears(1));
                    clanstvo.setNacinPlacanja(method);
                    return clanstvoRepository.save(clanstvo); // Save updated record
                })
                .orElseThrow(() -> new EntityNotFoundException("clanstvo with ID " + id + " not found."));

    }




}
