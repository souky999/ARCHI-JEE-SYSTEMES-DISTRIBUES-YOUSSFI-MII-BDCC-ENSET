package ma.enset.app.presentation;

import ma.enset.app.metier.IMetier;
import ma.enset.framework.context.AnnotationApplicationContext;
import ma.enset.framework.context.ApplicationContext;

public class PresAnnotations {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationApplicationContext("ma.enset.app");
        IMetier metier = (IMetier) context.getBean("metier");

        System.out.println("=== Mini Framework - Version Annotations ===");
        System.out.println("Résultat : " + metier.calcul());
    }
}
