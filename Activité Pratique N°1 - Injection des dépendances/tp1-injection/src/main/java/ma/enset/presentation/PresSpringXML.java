package ma.enset.presentation;

import ma.enset.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Injection des dépendances avec Spring - Version XML
 */
public class PresSpringXML {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        IMetier metier = context.getBean("metier", IMetier.class);

        System.out.println("=== Injection avec Spring (version XML) ===");
        System.out.println("Résultat : " + metier.calcul());
    }
}
