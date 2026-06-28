package ma.enset.app.dao;

import ma.enset.framework.annotations.Component;

@Component("dao")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("[Mini Framework] Accès base de données");
        return 23.5;
    }
}
