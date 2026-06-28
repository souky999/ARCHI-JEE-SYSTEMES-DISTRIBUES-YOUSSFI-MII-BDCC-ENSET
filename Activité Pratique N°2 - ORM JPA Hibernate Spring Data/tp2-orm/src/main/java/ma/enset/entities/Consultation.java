package ma.enset.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateConsultation;
    private String rapport;

    @OneToOne
    private RendezVous rendezVous;

    public Consultation() {}

    public Consultation(Long id, Date dateConsultation, String rapport, RendezVous rendezVous) {
        this.id = id; this.dateConsultation = dateConsultation;
        this.rapport = rapport; this.rendezVous = rendezVous;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(Date d) { this.dateConsultation = d; }
    public String getRapport() { return rapport; }
    public void setRapport(String rapport) { this.rapport = rapport; }
    public RendezVous getRendezVous() { return rendezVous; }
    public void setRendezVous(RendezVous r) { this.rendezVous = r; }

    @Override
    public String toString() {
        return "Consultation{id=" + id + ", rapport='" + rapport + "'}";
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private Date dateConsultation;
        private String rapport; private RendezVous rendezVous;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder dateConsultation(Date d) { this.dateConsultation = d; return this; }
        public Builder rapport(String r) { this.rapport = r; return this; }
        public Builder rendezVous(RendezVous rv) { this.rendezVous = rv; return this; }
        public Consultation build() { return new Consultation(id, dateConsultation, rapport, rendezVous); }
    }
}
