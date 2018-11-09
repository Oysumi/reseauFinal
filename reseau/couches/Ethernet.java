package reseau.couches;

import reseau.Message;
import reseau.adresses.AdresseMac;

/**
 * @author martine
 */
public class Ethernet extends Liaison {
    
    public Ethernet(AdresseMac am ) {
        super(am); 
    }
        
    @Override
    public void sendMessage(AdresseMac dest, Message message) {
        Message m = new Message(this.getEntete(dest, message)) ;
        m.ajouter(message) ;
        System.out.println("Je suis " + this.getNom() + " et j'envoie " + m.size() + " octets.") ;
        this.resLoc.sendTrame(m) ;
    }
  
    /**
     * Je reçois un message de la couche moinsUn
     * @param message le message reçu
     */
    @Override
    public void receiveMessage(Message message) {
        AdresseMac am = message.extraireAdresseMac() ;
        if (this.adrMac.toString().equals(am.toString()))
        {
            System.out.println("Je suis " + this.getNom() + " et je reçois " + message.size() + " octets.") ;

            // Une adresse mac est sur 6 octets donc on retire les 6*2 octets, dest et source
            message.supprimer(12) ;
            ((Reseau)plusUn).receiveMessage(message) ;
        }
        else
        {
            System.out.println("Je suis " + this.getNom() + " et je reçois un message qui ne me concerne pas.") ;
            System.out.println("======================================================================================") ;
        }
    }

    @Override
    protected Message getEntete(AdresseMac dest, Message message) {
        // Adresse Mac destination (48 bits), adresse Mac source (48 bits)
        AdresseMac amSource = this.adrMac ;
        Message m = new Message(dest) ;
        m.ajouter(amSource) ;
        return m;
    }

}
