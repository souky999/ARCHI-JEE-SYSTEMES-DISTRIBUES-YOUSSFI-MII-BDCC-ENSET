package ma.enset.repositories;

import ma.enset.entities.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByNom(String nom);
}
