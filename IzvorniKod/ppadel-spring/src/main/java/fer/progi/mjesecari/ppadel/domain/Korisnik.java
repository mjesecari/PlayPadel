package fer.progi.mjesecari.ppadel.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true, nullable = false)
  @NotNull
  private String email;

  private String tip;

  @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Rezervacija> rezervacije;
  @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SlikaTurnir> slikeTurniri;
  @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<KomentarTurnir> komentarTurniri;

  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getTip() {
    return tip;
  }
  
  public void setTip(String tip) {
    this.tip = tip;
  }
  
  public boolean isOwner(){
    return tip.equals("vlasnik") || isAdmin();
  }
  public boolean isAdmin(){
    return tip.equals("admin");
  }

  @Override
  public String toString() {
    return "Korisnik #" + id + " " + email + ", tip = " + tip;
  }
}
