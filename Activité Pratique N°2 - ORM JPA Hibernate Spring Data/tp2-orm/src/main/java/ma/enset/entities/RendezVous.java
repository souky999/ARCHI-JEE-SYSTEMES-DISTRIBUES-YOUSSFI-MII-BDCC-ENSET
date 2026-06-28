package ma.enset.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;

    @Enumerated(EnumType.STRING)
    private StatusRDV status;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Medecin medecin;

    @OneToOne(mappedBy = "rendezVous")
    private Consultation consultation;

    public RendezVous() {}

    public RendezVous(Long id, Date date, StatusRDV status, Patient patient, Medecin medecin, Consultation consultation) {
        this.id = id; this.date = date; this.status = status;
        this.patient = patient; this.medecin = medecin; this.consultation = consultation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public StatusRDV getStatus() { return status; }
    public void setStatus(StatusRDV status) { this.status = status; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Medecin getMedecin() { return medecin; }
    public void setMedecin(Medecin medecin) { this.medecin = medecin; }
    public Consultation getConsultation() { return consultation; }
    public void setConsultation(Consultation consultation) { this.consultation = consultation; }

    @Override
    public String toString() {
        return "RendezVous{id=" + id + ", status=" + status + "}";
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private Date date; private StatusRDV status;
        private Patient patient; private Medecin medecin; private Consultation consultation;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder date(Date date) { this.date = date; return this; }
        public Builder status(StatusRDV status) { this.status = status; return this; }
        public Builder patient(Patient p) { this.patient = p; return this; }
        public Builder medecin(Medecin m) { this.medecin = m; return this; }
        public Builder consultation(Consultation c) { this.consultation = c; return this; }
        public RendezVous build() { return new RendezVous(id, date, status, patient, medecin, consultation); }
    }
}
