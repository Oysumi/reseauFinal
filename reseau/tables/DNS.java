package reseau.tables;

import java.util.*;
import reseau.adresses.Adresse;
import reseau.adresses.AdresseMac;

/**
 * @author martine
 */

public class DNS {

    private HashMap<String, Adresse> nameToIP;

    public DNS() {
        nameToIP = new HashMap<String, Adresse>(100) ; // On prévoit une grande taille
    }

    public void ajouter(String nom, Adresse ip) {
        nameToIP.put(nom, ip);
    }
    
    public boolean machineConnue(String nom) {
        return nameToIP.containsKey(nom);
    }
    
    /**
     * @param name nom de la machine
     * @return l'adresse IP associée au nom de la machine passé en paramètre ou null si non reconnu
     */
    public Adresse getAdresse(String nom) {
        return nameToIP.get(nom) ;
    }
}
