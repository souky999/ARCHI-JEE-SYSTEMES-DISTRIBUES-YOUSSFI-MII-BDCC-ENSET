package ma.enset;

import ma.enset.entities.*;
import ma.enset.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Tp2OrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(Tp2OrmApplication.class, args);
    }

    // ============================================================
    // PARTIE 1 : Gestion des Produits
    // ============================================================
    @Bean
    CommandLineRunner testProducts(ProductRepository productRepository) {
        return args -> {
            System.out.println("\n========== GESTION DES PRODUITS ==========");

            // Ajouter des produits
            productRepository.save(Product.builder().name("Ordinateur").price(15000).quantity(10).build());
            productRepository.save(Product.builder().name("Imprimante").price(3500).quantity(5).build());
            productRepository.save(Product.builder().name("Clavier").price(250).quantity(50).build());
            productRepository.save(Product.builder().name("Souris").price(150).quantity(100).build());
            productRepository.save(Product.builder().name("Ecran").price(4500).quantity(20).build());

            // Consulter tous les produits
            System.out.println("\n--- Tous les produits ---");
            productRepository.findAll().forEach(System.out::println);

            // Consulter un produit par id
            System.out.println("\n--- Produit avec id=1 ---");
            productRepository.findById(1L).ifPresent(System.out::println);

            // Chercher des produits
            System.out.println("\n--- Recherche par mot-clé 'C' ---");
            productRepository.findByNameContains("C").forEach(System.out::println);

            // Mettre à jour un produit
            System.out.println("\n--- Mise à jour du produit id=1 ---");
            productRepository.findById(1L).ifPresent(p -> {
                p.setPrice(14000);
                p.setQuantity(15);
                productRepository.save(p);
                System.out.println("Produit mis à jour : " + p);
            });

            // Supprimer un produit
            System.out.println("\n--- Suppression du produit id=4 ---");
            productRepository.deleteById(4L);
            System.out.println("Produits restants : " + productRepository.count());
        };
    }

    // ============================================================
    // PARTIE 2 : Patient, Médecin, RendezVous, Consultation
    // ============================================================
    @Bean
    CommandLineRunner testHospital(
            PatientRepository patientRepo,
            MedecinRepository medecinRepo,
            RendezVousRepository rdvRepo,
            ConsultationRepository consultationRepo
    ) {
        return args -> {
            System.out.println("\n========== GESTION HOSPITALIÈRE ==========");

            // Ajouter des patients
            patientRepo.save(Patient.builder().nom("Ibrahim").dateNaissance(new Date()).malade(false).score(56).build());
            patientRepo.save(Patient.builder().nom("Mohamed").dateNaissance(new Date()).malade(true).score(23).build());
            patientRepo.save(Patient.builder().nom("Hanae").dateNaissance(new Date()).malade(false).score(78).build());

            // Ajouter des médecins
            medecinRepo.save(Medecin.builder().nom("Hassan").email("hassan@clinic.ma").specialite("Cardiologue").build());
            medecinRepo.save(Medecin.builder().nom("Najat").email("najat@clinic.ma").specialite("Dermatologue").build());

            // Consulter tous les patients
            System.out.println("\n--- Tous les patients ---");
            patientRepo.findAll().forEach(p ->
                System.out.printf("ID=%d | Nom=%-10s | Malade=%-5s | Score=%d%n",
                        p.getId(), p.getNom(), p.isMalade(), p.getScore())
            );

            // Chercher des patients malades
            System.out.println("\n--- Patients malades ---");
            patientRepo.findByMalade(true).forEach(System.out::println);

            // Ajouter un rendez-vous
            Patient patient = patientRepo.findById(1L).get();
            Medecin medecin = medecinRepo.findById(1L).get();
            RendezVous rdv = RendezVous.builder()
                    .date(new Date())
                    .status(StatusRDV.PENDING)
                    .patient(patient)
                    .medecin(medecin)
                    .build();
            rdvRepo.save(rdv);

            // Ajouter une consultation liée au rendez-vous
            RendezVous savedRdv = rdvRepo.findById(1L).get();
            Consultation consultation = Consultation.builder()
                    .dateConsultation(new Date())
                    .rapport("Le patient souffre d'hypertension légère.")
                    .rendezVous(savedRdv)
                    .build();
            consultationRepo.save(consultation);

            System.out.println("\n--- Rendez-vous enregistrés ---");
            rdvRepo.findAll().forEach(r ->
                System.out.printf("RDV#%d | Patient=%-10s | Médecin=%-10s | Status=%s%n",
                        r.getId(), r.getPatient().getNom(), r.getMedecin().getNom(), r.getStatus())
            );
        };
    }

    // ============================================================
    // PARTIE 3 : Users & Roles
    // ============================================================
    @Bean
    CommandLineRunner testUsersRoles(AppUserRepository userRepo, AppRoleRepository roleRepo) {
        return args -> {
            System.out.println("\n========== GESTION USERS & ROLES ==========");

            // Créer les rôles
            AppRole adminRole = roleRepo.save(AppRole.builder().roleName("ADMIN").description("Administrateur").build());
            AppRole userRole  = roleRepo.save(AppRole.builder().roleName("USER").description("Utilisateur standard").build());

            // Créer les utilisateurs
            AppUser admin = userRepo.save(AppUser.builder()
                    .username("admin")
                    .password("admin123")
                    .email("admin@enset.ma")
                    .roles(List.of(adminRole, userRole))
                    .build());

            AppUser user = userRepo.save(AppUser.builder()
                    .username("user1")
                    .password("user123")
                    .email("user1@enset.ma")
                    .roles(List.of(userRole))
                    .build());

            System.out.println("\n--- Utilisateurs ---");
            userRepo.findAll().forEach(u ->
                System.out.printf("User=%-10s | Rôles=%s%n",
                        u.getUsername(), u.getRoles().stream().map(AppRole::getRoleName).toList())
            );
        };
    }
}
