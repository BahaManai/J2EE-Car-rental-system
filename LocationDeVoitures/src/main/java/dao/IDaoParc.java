package dao;

import java.util.List;
import entities.Parc;

public interface IDaoParc {
    void ajouterParc(Parc p);
    void modifierParc(Parc p);
    void supprimerParc(int codeParc);
    List<Parc> listeParcs();
    Parc getParcById(int codeParc);
}
