package ma.enset.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date dateNaissance;
    private boolean malade;
    private int score;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<RendezVous> rendezVous;

    public Patient() {}

    public Patient(Long id, String nom, Date dateNaissance, boolean malade, int score, List<RendezVous> rendezVous) {
        this.id = id; this.nom = nom; this.dateNaissance = dateNaissance;
        this.malade = malade; this.score = score; this.rendezVous = rendezVous;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }
    public boolean isMalade() { return malade; }
    public void setMalade(boolean malade) { this.malade = malade; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public List<RendezVous> getRendezVous() { return rendezVous; }
    public void setRendezVous(List<RendezVous> rendezVous) { this.rendezVous = rendezVous; }

    @Override
    public String toString() {
        return "Patient{id=" + id + ", nom='" + nom + "', malade=" + malade + ", score=" + score + "}";
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private String nom; private Date dateNaissance;
        private boolean malade; private int score; private List<RendezVous> rendezVous;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder nom(String nom) { this.nom = nom; return this; }
        public Builder dateNaissance(Date d) { this.dateNaissance = d; return this; }
        public Builder malade(boolean m) { this.malade = m; return this; }
        public Builder score(int s) { this.score = s; return this; }
        public Builder rendezVous(List<RendezVous> r) { this.rendezVous = r; return this; }
        public Patient build() { return new Patient(id, nom, dateNaissance, malade, score, rendezVous); }
    }
}
