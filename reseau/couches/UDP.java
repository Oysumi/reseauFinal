package reseau.couches;

import reseau.Message;
import reseau.adresses.Adresse;

/**
 * @author martine
 */
public class UDP extends Transport {

    public UDP() { 
        super();
    }
    
    @Override
    protected Message getEntete(int portSource, Adresse adrDest, int portDest, Message message) {
        // Port source (16 bits), port destination (16 bits), longueur entête + données (16 bits), somme de contrôle (16 bits) 
        int sommeControle = 0;
        int longueur = 6 + message.size(); // 4 octets pour l'entête et 2 octets pour somme de contrôle
        Message mess = new Message(portSource, portDest, longueur, sommeControle);
        return mess ;
    }

}
