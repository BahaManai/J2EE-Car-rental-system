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
	    Transaction tx = null;
	    Session session = null;

	    try {
	        session = HibernateUtil.getSessionFactory().openSession();
	        tx = session.beginTransaction();

	        // Réattacher les entités
	        Client client = session.get(Client.class, l.getClient().getCodeClient());
	        Voiture voiture = session.get(Voiture.class, l.getVoiture().getCodeVoiture());

	        l.setClient(client);
	        l.setVoiture(voiture);

	        session.merge(l);
	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null && session != null && session.isOpen()) {
	            try {
	                tx.rollback();
	            } catch (Exception rollbackEx) {
	                System.err.println("Échec du rollback : " + rollbackEx.getMessage());
	            }
	        }
	        e.printStackTrace();
	    } finally {
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
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
    public List<Location> listeLocations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("""
                SELECT l FROM Location l
                JOIN FETCH l.client
                JOIN FETCH l.voiture
            """, Location.class).getResultList();
        }
    }

    @Override
    public ArrayList<Location> getLocationsByClientId(int clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        	Query<Location> query = session.createQuery("""
        		    SELECT l FROM Location l
        		    JOIN FETCH l.voiture
        		    WHERE l.client.codeClient = :clientId
        		""", Location.class);
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
            Query<Location> query = session.createQuery(
                "FROM Location l WHERE l.statut = 'accepté'", Location.class);
            List<Location> locations = query.list();

            double total = 0.0;
            for (Location l : locations) {
                if (l.getDateDeb() != null && l.getDateFin() != null && l.getVoiture() != null) {
                    long days = (l.getDateFin().getTime() - l.getDateDeb().getTime()) / (1000 * 60 * 60 * 24);
                    total += l.getVoiture().getPrixParJour() * days;
                }
            }
            return total;
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
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -5); // On veut les 6 derniers mois (inclut mois courant)
            cal.set(Calendar.DAY_OF_MONTH, 1); // Début du mois

            Query<Object[]> query = session.createNativeQuery("""
                SELECT 
                    YEAR(l.date_debut) as yr,
                    MONTH(l.date_debut) as mn,
                    SUM(v.prix_par_jour * DATEDIFF(l.date_fin, l.date_debut)) as revenue
                FROM location l
                JOIN voiture v ON l.code_voiture = v.code_voiture
                WHERE l.statut = 'accepté' AND l.date_debut >= :startDate
                GROUP BY YEAR(l.date_debut), MONTH(l.date_debut)
                ORDER BY yr, mn
            """);
            query.setParameter("startDate", cal.getTime());

            // Initialise les 6 derniers mois avec 0
            Calendar now = Calendar.getInstance();
            for (int i = 5; i >= 0; i--) {
                Calendar m = (Calendar) now.clone();
                m.add(Calendar.MONTH, -i);
                revenues.add(0.0);
            }

            // Injecte les revenus dans les bons mois
            List<Object[]> results = query.list();
            for (Object[] row : results) {
                int year = ((Number) row[0]).intValue();
                int month = ((Number) row[1]).intValue();
                double revenue = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;

                Calendar dataCal = Calendar.getInstance();
                dataCal.set(year, month - 1, 1);

                int index = (dataCal.get(Calendar.YEAR) - now.get(Calendar.YEAR)) * 12 +
                            (dataCal.get(Calendar.MONTH) - now.get(Calendar.MONTH)) + 5;

                if (index >= 0 && index < 6) {
                    revenues.set(index, revenue);
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
    
    public List<String> getRevenueMonths() {
        List<String> months = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                "select function('MONTHNAME', l.dateDeb) " +
                "from Location l where l.statut = 'accepté' and l.dateDeb >= :sixMonthsAgo " +
                "group by function('MONTHNAME', l.dateDeb), function('MONTH', l.dateDeb) " +
                "order by function('YEAR', l.dateDeb), function('MONTH', l.dateDeb)", String.class);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -6);
            query.setParameter("sixMonthsAgo", cal.getTime());

            months = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return months;
    }

}