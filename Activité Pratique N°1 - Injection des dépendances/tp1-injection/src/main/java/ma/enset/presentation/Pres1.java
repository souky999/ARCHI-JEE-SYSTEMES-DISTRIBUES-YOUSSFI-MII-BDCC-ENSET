package ma.enset.presentation;

import ma.enset.dao.DaoImpl;
import ma.enset.metier.IMetier;
import ma.enset.metier.MetierImpl;

/**
 * Injection des dépendances par instanciation statique
 * Couplage fort : on connaît les classes concrètes à la compilation
 */
public class Pres1 {
    public static void main(String[] args) {
        // Instanciation statique : le développeur choisit manuellement les implémentations
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao);

        System.out.println("=== Injection par instanciation statique ===");
        System.out.println("Résultat : " + metier.calcul());
    }
}
