package utilitaire;

public class HibernateTest {
    public static void main(String[] args) {
        System.out.println(HibernateUtil.getSessionFactory() != null ? "SessionFactory OK" : "SessionFactory failed");
    }
}