package dao;

import entities.Client;
import entities.Location;
import entities.Voiture;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utilitaire.HibernateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ImpDaoLocation implements IDaoLocation {

    @Override
    public void ajouterLocation(Location l) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(l);
            tx.commit();
            System.out.println("Location ajoutée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerLocation(int code) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Location location = session.get(Location.class, code);
            if (location != null) {
                session.remove(location);
                System.out.println("Location supprimée avec succès.");
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierLocation(Location l) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(l);
            tx.commit();
            System.out.println("Location modifiée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLocationStatus(int codeLocation, String statut) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Location location = session.get(Location.class, codeLocation);
            if (location != null) {
                location.setStatut(statut);
                session.merge(location);
                System.out.println("Statut de la location mis à jour avec succès.");
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Location> listeLocations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Location> query = session.createQuery("from Location", Location.class);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<Location> getLocationsByClientId(int clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Location> query = session.createQuery("from Location where client.codeClient = :clientId", Location.class);
            query.setParameter("clientId", clientId);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<Location> getLocationsByStatus(String statut) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Location> query = session.createQuery("from Location where statut = :statut", Location.class);
            query.setParameter("statut", statut);
            return new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Location getLocationById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Location.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean estVoitureDisponible(int codeVoiture, Date debut, Date fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "select count(*) from Location l where l.voiture.codeVoiture = :codeVoiture and l.statut <> 'refusé' and (" +
                ":debut between l.dateDeb and l.dateFin or " +
                ":fin between l.dateDeb and l.dateFin or " +
                "l.dateDeb between :debut and :fin)", Long.class);
            query.setParameter("codeVoiture", codeVoiture);
            query.setParameter("debut", debut);
            query.setParameter("fin", fin);
            return query.uniqueResult() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public double calculateTotalRevenue() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                "select sum(v.prixParJour * (cast(l.dateFin as long) - cast(l.dateDeb as long)) / 86400000) " +
                "from Location l join l.voiture v where l.statut = 'accepté'", Double.class);
            Double result = query.uniqueResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    @Override
    public List<Double> getRevenuePerParc() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                "select sum(v.prixParJour * (cast(l.dateFin as long) - cast(l.dateDeb as long)) / 86400000) " +
                "from Location l join l.voiture v join v.parc p where l.statut = 'accepté' group by p.codeParc order by p.codeParc", Double.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    @Override
    public List<Double> getMonthlyRevenue() {
        List<Double> revenues = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                "select year(l.dateDeb), month(l.dateDeb), " +
                "sum(v.prixParJour * (cast(l.dateFin as long) - cast(l.dateDeb as long)) / 86400000) " +
                "from Location l join l.voiture v where l.statut = 'accepté' and l.dateDeb >= :sixMonthsAgo " +
                "group by year(l.dateDeb), month(l.dateDeb) order by year(l.dateDeb), month(l.dateDeb)", Object[].class);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -6);
            query.setParameter("sixMonthsAgo", cal.getTime());
            
            // Initialize 6 months with 0.0
            for (int i = 0; i < 6; i++) {
                revenues.add(0.0);
            }
            
            // Fill with data
            List<Object[]> results = query.list();
            for (Object[] row : results) {
                int year = (Integer) row[0];
                int month = (Integer) row[1];
                Double revenue = (Double) row[2];
                
                // Calculate months between current date and result date
                Calendar dataCal = Calendar.getInstance();
                dataCal.set(year, month - 1, 1);
                long monthsBetween = ((cal.get(Calendar.YEAR) - dataCal.get(Calendar.YEAR)) * 12 +
                                    (cal.get(Calendar.MONTH) - dataCal.get(Calendar.MONTH))) * -1;
                if (monthsBetween >= 0 && monthsBetween < 6) {
                    revenues.set((int) monthsBetween, revenue != null ? revenue : 0.0);
                }
            }
            return revenues;
        } catch (Exception e) {
            e.printStackTrace();
            return revenues;
        }
    }
    @Override
    public int countLocations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("select count(*) from Location", Long.class);
            return query.uniqueResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public int countActiveLocations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "select count(*) from Location where statut = 'accepté' and dateFin > current_date", Long.class);
            return query.uniqueResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}