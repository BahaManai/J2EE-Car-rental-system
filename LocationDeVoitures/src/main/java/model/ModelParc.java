package model;

import java.util.ArrayList;
import java.util.List;
import dao.ImpDaoParc;
import entities.Parc;

public class ModelParc {
    private Parc parc;
    private ImpDaoParc daoParc;

    public ModelParc() {
        this.daoParc = new ImpDaoParc();
    }

    public void setParc(Parc parc) {
        this.parc = parc;
    }

    public void ajouterParc() {
        if (this.parc != null) {
            daoParc.ajouterParc(this.parc);
        } else {
            System.err.println("Aucun parc à ajouter.");
        }
    }

    public void modifierParc() {
        if (this.parc != null) {
            daoParc.modifierParc(this.parc);
        } else {
            System.err.println("Aucun parc à modifier.");
        }
    }

    public void supprimerParc(int codeParc) {
        if (codeParc > 0) {
            daoParc.supprimerParc(codeParc);
        } else {
            System.err.println("Code parc invalide.");
        }
    }

    public ArrayList<Parc> getAllParcs() {
        return daoParc.listeParcs();
    }

    public Parc getParcById(int codeParc) {
        return daoParc.getParcById(codeParc);
    }

    public int countParcs() {
        return daoParc.countParcs(); // Utiliser une requête SQL directe
    }

    public List<String> getParcNames() {
        return daoParc.getParcNames(); // Liste des noms des parcs
    }
}