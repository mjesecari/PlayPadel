package fer.progi.mjesecari.ppadel.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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
    return tip.equals("vlasnik");
  }
  public boolean isAdmin(){
    return tip.equals("admin");
  }

  @Override
  public String toString() {
    return "Korisnik #" + id + " " + email + ", tip = " + tip;
  }
}
