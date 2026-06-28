package ma.enset.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String specialite;

    @OneToMany(mappedBy = "medecin", fetch = FetchType.LAZY)
    private List<RendezVous> rendezVous;

    public Medecin() {}

    public Medecin(Long id, String nom, String email, String specialite, List<RendezVous> rendezVous) {
        this.id = id; this.nom = nom; this.email = email;
        this.specialite = specialite; this.rendezVous = rendezVous;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
    public List<RendezVous> getRendezVous() { return rendezVous; }
    public void setRendezVous(List<RendezVous> r) { this.rendezVous = r; }

    @Override
    public String toString() {
        return "Medecin{id=" + id + ", nom='" + nom + "', specialite='" + specialite + "'}";
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private String nom; private String email;
        private String specialite; private List<RendezVous> rendezVous;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder nom(String nom) { this.nom = nom; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder specialite(String s) { this.specialite = s; return this; }
        public Builder rendezVous(List<RendezVous> r) { this.rendezVous = r; return this; }
        public Medecin build() { return new Medecin(id, nom, email, specialite, rendezVous); }
    }
}
