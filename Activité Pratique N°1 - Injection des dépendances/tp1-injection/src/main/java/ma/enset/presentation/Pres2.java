package ma.enset.presentation;

import ma.enset.dao.IDao;
import ma.enset.metier.IMetier;
import ma.enset.metier.MetierImpl;

import java.io.File;
import java.util.Scanner;

/**
 * Injection des dépendances par instanciation dynamique
 * Couplage faible : les classes concrètes sont lues depuis un fichier de config
 */
public class Pres2 {
    public static void main(String[] args) throws Exception {
        // Lire les noms de classes depuis le fichier de configuration
        Scanner scanner = new Scanner(new File("config.txt"));
        String daoClassName    = scanner.nextLine();
        String metierClassName = scanner.nextLine();
        scanner.close();

        // Instanciation dynamique via réflexion
        IDao dao = (IDao) Class.forName(daoClassName).getConstructor().newInstance();

        MetierImpl metier = (MetierImpl) Class.forName(metierClassName).getConstructor().newInstance();
        metier.setDao(dao);

        System.out.println("=== Injection par instanciation dynamique ===");
        System.out.println("DAO utilisé : " + dao.getClass().getSimpleName());
        System.out.println("Résultat : " + metier.calcul());
    }
}
