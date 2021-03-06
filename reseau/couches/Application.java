package reseau.couches;

import reseau.Message;
import reseau.adresses.Adresse;
import reseau.Machine;
import reseau.clientsServeurs.ClientDNS ;

/**
 * @author martine
 */

public abstract class Application extends Couche {
    
    protected int port ;
    protected Message resultat ;                   // destiné à contenir le résutat de la requête au serveur
    protected Machine machine ;
    protected Application dns ;
    
    public Application(int port, Machine mach) {
        this.port = port ;
        this.machine = mach ;
        mach.ajouter(port, this) ;
    }

    public Application(int port, Machine mach, Application clientDNS) {
        this(port, mach);
        dns = clientDNS ;
    }
    
    public int getPort() {
        return this.port ;
    }

    /**
     * @param dest adresse du destinataire
     * @param portDest port du service demandé
     * @param message message à transmettre
     */
    public void sendMessage(Adresse dest, int portDest, Message message) {
        // On efface le résultat de la précédente requête
        resultat = null ;
        // Afficher une trace de l'envoi
        System.out.println("Je suis "+getNom()+" et j'envoie "+message.size()+" octets : " + message);
        // Transmettre à la couche Transport
        ((Transport)moinsUn).sendMessage(this.getPort(), dest, portDest, message);
    }

    /**
     * @param nom nom du destinataire
     * @param portDest port du service demandé
     * @param message message à transmettre
     */
    public void sendMessage(String nom, int portDest, Message message) {
        // On efface le résultat de la précédente requête
        resultat = null ;
        // Afficher une trace de l'envoi
        System.out.println("Je suis "+getNom()+" et j'envoie "+message.size()+" octets.");
        // Envoie au client DNS
        dns.sendMessage(nom, portDest, message);
        resultat = dns.getResultat();
    }            
        
    /**
     * Je reçois un message de la couche inférieure 
     * @param source adresse de la source
     * @param portSource port de la source
     * @param message message à transmettre
     */
    public void receiveMessage(Adresse source, int portSource, Message message) {
           System.out.println("Je suis " + getNom() + " et je reçois " + message.size() + " octets.") ;
           Message m = this.traiter(message) ;
           this.sendMessage(source, portSource, m) ;
    }

    /**
     * 
     * @return Le résultat de la dernière requête
     */
    public Message getResultat() {
        return resultat;
    }

    protected Message traiter(Message message) {
        return message ;
    }
 
}