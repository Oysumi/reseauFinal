package reseau.couches;

import reseau.Message;
import reseau.adresses.AdresseMac;
import reseau.ReseauLocal;

/**
 * @author martine
 */

public abstract class Liaison extends Couche {

    protected  AdresseMac adrMac;
    protected Liaison voisin ;
    protected ReseauLocal resLoc ;

    public Liaison(AdresseMac am) {
        super( );
        adrMac = am ;
    }

    /**
     * Fixer le support physique connecté à cette couche
     * @param rl liaison physique
     */
    public void setVoisin(Liaison rl) {
        this.voisin = rl;
    }

    public void setReseau(ReseauLocal rl){
        this.resLoc = rl ;
    }

    public abstract void sendMessage(AdresseMac dest, Message message); 

    protected abstract Message getEntete(AdresseMac des, Message message) ;

    public abstract void receiveMessage(Message message) ;
}
