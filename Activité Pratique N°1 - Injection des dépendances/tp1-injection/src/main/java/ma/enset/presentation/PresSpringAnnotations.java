package ma.enset.presentation;

import ma.enset.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Injection des dépendances avec Spring - Version Annotations
 */
public class PresSpringAnnotations {

    @Configuration
    @ComponentScan("ma.enset")
    static class AppConfig {}

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        IMetier metier = context.getBean("metier", IMetier.class);

        System.out.println("=== Injection avec Spring (version Annotations) ===");
        System.out.println("Résultat : " + metier.calcul());
    }
}
