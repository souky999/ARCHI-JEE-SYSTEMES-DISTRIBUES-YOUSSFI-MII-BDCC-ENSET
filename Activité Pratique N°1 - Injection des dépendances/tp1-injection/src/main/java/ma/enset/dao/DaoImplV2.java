package ma.enset.dao;

public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version capteur (web service)");
        return 37.2;
    }
}
