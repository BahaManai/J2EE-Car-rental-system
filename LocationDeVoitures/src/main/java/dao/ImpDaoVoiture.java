
package dao;

import entities.Parc;
import entities.Voiture;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilitaire.HibernateUtil;

import java.util.List;

public class ImpDaoVoiture implements IDaoVoiture {

    @Override
    public void ajouterVoiture(Voiture v) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(v);
            tx.commit();
            System.out.println("Voiture ajoutée avec succès.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void modifierVoiture(Voiture v) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(v);
            tx.commit();
            System.out.println("Voiture modifiée avec succès.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerVoiture(int codeVoiture) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Voiture v = session.get(Voiture.class, codeVoiture);
            if (v != null) {
                session.remove(v);
                System.out.println("Voiture supprimée avec succès.");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Voiture> listeVoitures() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Voiture", Voiture.class).getResultList();
        }
    }

    @Override
    public Voiture getVoitureById(int codeVoiture) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Voiture.class, codeVoiture);
        }
    }

    public double calculateOccupancyRate() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select (count(distinct l.voiture.codeVoiture) * 100.0 / (select count(v) from Voiture v)) " +
                         "from Location l where l.statut = 'accepté' and l.dateFin > current_date()";
            Double rate = session.createQuery(hql, Double.class).getSingleResult();
            return rate != null ? rate : 0.0;
        }
    }

    public List<String> getCarTypeLabels() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select distinct v.model from Voiture v", String.class).getResultList();
        }
    }

    public List<Long> getCarTypeCounts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(v) from Voiture v group by v.model";
            return session.createQuery(hql, Long.class).getResultList();
        }
    }

    public int countVoitures() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(v) from Voiture v";
            Long count = session.createQuery(hql, Long.class).getSingleResult();
            return count != null ? count.intValue() : 0;
        }
    }
}
