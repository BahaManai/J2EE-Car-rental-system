package dao;

import entities.Parc;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilitaire.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class ImpDaoParc implements IDaoParc {

    @Override
    public void ajouterParc(Parc p) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(p);
            tx.commit();
            System.out.println("Parc ajouté avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierParc(Parc p) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(p);
            tx.commit();
            System.out.println("Parc modifié avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerParc(int codeParc) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Parc parc = session.get(Parc.class, codeParc);
            if (parc != null) {
                session.remove(parc);
                System.out.println("Parc supprimé avec succès.");
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Parc> listeParcs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Parc> parcs = session.createQuery("from Parc", Parc.class).list();
            return new ArrayList<>(parcs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Parc getParcById(int codeParc) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Parc.class, codeParc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getParcNames() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select p.nomParc from Parc p order by p.codeParc", String.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public int countParcs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery("select count(p) from Parc p", Long.class).uniqueResult();
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
