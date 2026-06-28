package ma.enset.app.presentation;

import ma.enset.app.metier.IMetier;
import ma.enset.framework.context.ApplicationContext;
import ma.enset.framework.context.XMLApplicationContext;

public class PresXML {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new XMLApplicationContext("beans.xml");
        IMetier metier = (IMetier) context.getBean("metier");

        System.out.println("=== Mini Framework - Version XML ===");
        System.out.println("Résultat : " + metier.calcul());
    }
}
