package reseau.couches;

import reseau.tables.ARP;
import reseau.Message;
import reseau.adresses.*;

/**
 * @author martine
 */
public class IP extends Reseau {

    static private int compteur = 0;

    protected Adresse masque ;
    protected Adresse adresseIP ;

    private ARP arp = new ARP() ;
    
    /**
     * @param ici la machine où je suis
     * @param masque masque à appliquer
     */
    public IP(Adresse ici, Adresse masque) {
        adresseIP = ici ;
        this.masque = masque;
    }

    /**
     * @param dest adresse destination
     * @param message message à transmettre
     * @return entête du message
     */
    public Message getEntete(Adresse dest, Message message) {
        // Longueur totale (16 bits), Identification (16 bits), Protocole (8 bits)
        // Adresse IP source (32 bits), Adresse IP destination (32 bits)
        short protocole = 17;
        int longueur = 13 + message.size(); // 2 longueur + 2 ident + 1 prot + 4 source + 4 dest
        Message m = new Message(longueur, this.compteur);
        m.ajouter(protocole);
        m.ajouter(this.adresseIP);
        m.ajouter(dest);
        
        return m ;
    }
    
    /**
     * Utilisation de la table ARP pour retrouver l'adresseMac à partir d'une adresse IP
     * @param adr adresse IP
     * @return adresseMac associée à l'adresse IP
     */
    private AdresseMac getAdresseMac(Adresse adr) {
        ARP apr = new ARP();
        
        return new AdresseMac( arp.adresseConnue(adr) ? arp.get(adr) : null );
    }
    
    /**
     * @return mon adresse IP
     */
    public Adresse getAdresseIP() {
        return this.adresseIP;
    }
    
    /**
     * @param dest adresse du destinataire
     * @param message message à transmettre
     */
    @Override
    public void sendMessage(Adresse dest, Message message) {
      Message mes = this.getEntete(dest, message);
      mes.ajouter(message);
      
      Adresse destMasque = new Adresse(dest) ;
      Adresse sourceMasque = new Adresse(this.getAdresseIP()) ;
      
      destMasque.masquer(this.masque) ;
      sourceMasque.masquer(this.masque) ;
      
      if ( destMasque.toString().equals(sourceMasque.toString()) )
      {
            System.out.println("Je suis "+this.getNom() + " et j'envoie " + mes.size() +" octets.");
            ((Liaison)moinsUn).sendMessage(this.getAdresseMac(dest), mes);
      }
      else
      {
            System.out.println("Les deux machines ne sont pas sur le même réseau.") ;
            System.out.println("======================================================================================") ;
      }
    } 
  
    @Override
    public void receiveMessage(Message message) {
       System.out.println("Je suis " + this.getNom() + " et je reçois " + message.size() + " octets.") ;
       message.supprimer(5) ; // on retire l'entête jusqu'au protocole
       Adresse adrSource = message.extraireAdresse(4) ;
       message.supprimer(4) ;
       Adresse adrDest = message.extraireAdresse(4) ;
       message.supprimer(4) ;
       if (this.adresseIP.toString().equals(adrDest.toString()))
       {
            System.out.println("Je reçois un message qui m'est bien destiné.") ;
            ((Transport)plusUn).receiveMessage(adrSource, message) ;
       }
       else
       {
            System.out.println("Je reçois un message qui ne m'est pas destiné.") ;
            System.out.println("======================================================================================") ;
       }
    }  

}
