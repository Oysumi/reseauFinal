package reseau.couches;

import java.util.HashMap;
import reseau.Message;
 
import reseau.adresses.Adresse;

/**
 * @author martine
 * @version 19 nov. 2014
 */

public abstract class Transport extends Couche {
    
    private HashMap<Integer, Application> lesApplications ;     // Accès aux applications via le port
    
    public Transport() {
        lesApplications = new HashMap<>();
    }

    /**
     * Ajouter une nouvelle application avec son no de port
     * @param port port de l'application
     * @param appli  application associée au port
     */
    public void ajouter(int port, Application appli) {
        lesApplications.put(port, appli) ;
    }
    
    /**
     * @param port port de l'application
     * @return l'application liée au port donné
     */
    public Application getApplication(int port) {
        Application a = lesApplications.get(port) ;
        if (a==null) throw new IllegalArgumentException("Port inconnu "+port);
        return a;
    }
   
    /**
     * Envoyer un message à un destinataire précis
     * @param portSource port source de l'application
     * @param dest adresse destination
     * @param portDest port destination de l'application
     * @param message message à transmettre
     */
    public void sendMessage(int portSource, Adresse dest, int portDest, Message message) {
      Message mes = this.getEntete(portSource, dest, portDest, message);
      mes.ajouter(message);
      System.out.println("Je suis " + this.getNom() + " et j'envoie " + mes.size() + " octets : "  + mes+"\n");
      ((Reseau)moinsUn).sendMessage(dest, mes);
    }
 
    public void receiveMessage(Adresse source, Message message) {
    
        System.out.println("Je suis " + this.getNom() + " et je reçois " + message.size() + " octets: " + message+"\n") ;
        Message m = new Message(message);
        int portSource = m.extraireEntier(0) ; // extraction du port source
        m.supprimer(2) ; // on supprime le port source
        int portDest = m.extraireEntier(0) ; // extraction du port dest
        m.supprimer(4) ; // on supprime le port dest et la longueur du message codé sur 2 octets (2+2)
        int sommeControle = m.extraireEntier(0) ; // on récupère la somme de contrôle
        m.supprimer(2) ; // on supprime la somme de contrôle
        
        if ( sommeControle == 0 )
        {
            Application app = this.getApplication(portDest) ;
            app.receiveMessage(source, portSource, m) ;
        }
        else
        {
            System.out.println("Problème sur le message, la somme de contrôle ne vaut pas 0.\n") ;
        }
    }

    /**
     * Construit l'entête du message
     * @param portSource port source de l'application
     * @param dest adresse destination
     * @param portDest port destination de l'application
     * @param message message à transmettre
     * @return entête construite
     */
    protected abstract Message getEntete(int portSource, Adresse dest, int portDest, Message message) ;
    
}
