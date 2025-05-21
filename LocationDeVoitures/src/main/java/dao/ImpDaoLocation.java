package dao;

import entities.Location;
import entities.Client;
import entities.Voiture;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilitaire.HibernateUtil;

import java.util.Date;
import java.util.List;

public class ImpDaoLocation implements IDaoLocation {

    @Override
    public void ajouterLocation(Location l) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(l);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerLocation(int code) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Location location = session.get(Location.class, code);
            if (location != null) {
                session.remove(location);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void modifierLocation(Location l) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(l);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void updateLocationStatus(int codeLocation, String statut) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Location l = session.get(Location.class, codeLocation);
            if (l != null) {
                l.setStatut(statut);
                session.merge(l);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Location> listeLocations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Location", Location.class).getResultList();
        }
    }

    @Override
    public List<Location> getLocationsByClientId(int clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Location WHERE client.codeClient = :id", Location.class)
                    .setParameter("id", clientId)
                    .getResultList();
        }
    }

    @Override
    public List<Location> getLocationsByStatus(String statut) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Location WHERE statut = :statut", Location.class)
                    .setParameter("statut", statut)
                    .getResultList();
        }
    }

    @Override
    public Location getLocationById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Location.class, id);
        }
    }

    @Override
    public boolean estVoitureDisponible(int codeVoiture, Date debut, Date fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery("""
                    SELECT COUNT(*) FROM Location l
                    WHERE l.voiture.codeVoiture = :codeVoiture AND l.statut <> 'refusé' AND
                          (:debut BETWEEN l.dateDeb AND l.dateFin OR
                           :fin BETWEEN l.dateDeb AND l.dateFin OR
                           l.dateDeb BETWEEN :debut AND :fin)
                    """, Long.class)
                    .setParameter("codeVoiture", codeVoiture)
                    .setParameter("debut", debut)
                    .setParameter("fin", fin)
                    .uniqueResult();
            return count == 0;
        }
    }
}
