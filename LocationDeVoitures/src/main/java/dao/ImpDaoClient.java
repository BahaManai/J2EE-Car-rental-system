package dao;

import entities.Client;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilitaire.HibernateUtil;

import java.util.List;

public class ImpDaoClient implements IDaoClient {

    @Override
    public void ajouterClient(Client client) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(client);
            tx.commit();
            System.out.println("Client ajouté avec succès.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Erreur lors de l'ajout du client : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void modifierClient(Client client) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(client);
            tx.commit();
            System.out.println("Client modifié avec succès.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Erreur lors de la modification du client : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerClient(int codeClient) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Client client = session.get(Client.class, codeClient);
            if (client != null) {
                session.remove(client);
            }
            tx.commit();
            System.out.println("Client supprimé avec succès.");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Erreur lors de la suppression du client : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> listeClients() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Client", Client.class).list();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des clients : " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public Client getClientById(int codeClient) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Client.class, codeClient);
        } catch (Exception e) {
            System.err.println("Erreur dans getClientById : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Client findByEmailAndPassword(String email, String motDePasse) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "from Client where email = :email and motDePasse = :pwd", Client.class)
                    .setParameter("email", email)
                    .setParameter("pwd", motDePasse)
                    .uniqueResult();
        } catch (Exception e) {
            System.err.println("Erreur dans findByEmailAndPassword : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
