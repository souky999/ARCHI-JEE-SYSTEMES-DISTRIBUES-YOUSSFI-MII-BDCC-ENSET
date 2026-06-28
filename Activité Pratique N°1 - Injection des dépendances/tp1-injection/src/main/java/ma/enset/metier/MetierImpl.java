package ma.enset.metier;

import ma.enset.dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("metier")
public class MetierImpl implements IMetier {

    // Couplage faible : on dépend de l'interface, pas de l'implémentation
    private IDao dao;

    // Injection via constructeur (utilisée par Spring Annotations)
    @Autowired
    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    // Constructeur par défaut (nécessaire pour instanciation dynamique et Spring XML)
    public MetierImpl() {}

    // Setter pour injection via setter
    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double temperature = dao.getData();
        return temperature * 2 + 10;
    }
}
