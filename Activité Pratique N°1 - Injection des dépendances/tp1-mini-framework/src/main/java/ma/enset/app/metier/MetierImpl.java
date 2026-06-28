package ma.enset.app.metier;

import ma.enset.app.dao.IDao;
import ma.enset.framework.annotations.Autowired;
import ma.enset.framework.annotations.Component;

@Component("metier")
public class MetierImpl implements IMetier {

    // Injection via attribut (field injection)
    @Autowired
    private IDao dao;

    public MetierImpl() {}

    // Injection via setter
    @Autowired
    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        return dao.getData() * 2 + 10;
    }
}
