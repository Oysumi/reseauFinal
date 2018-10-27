package reseau ;

import reseau.couches.* ;
import reseau.adresses.* ;
import java.util.ArrayList ;

public class Machine{

	protected String nom ;

	protected Transport transp ;
	protected Reseau network ;
	protected Liaison link ;
	protected ArrayList<Application> app ;

	public Machine(String name, Adresse ip, AdresseMac mac, Adresse masque)
	{
		this.nom = name ;

		this.transp = new UDP() ;
		this.network = new IP(ip, masque) ;
		this.link = new Ethernet(mac) ;
		this.app = new ArrayList<>(100) ;
	}

	public void ajouter(int port, Application appli)
	{
		this.transp.ajouter(port, appli) ;
		appli.setCouches(null, this.transp);
        this.transp.setCouches(appli, this.network);
        this.network.setCouches(this.transp, this.link);
        this.link.setCouches(this.network, null);
        this.app.add(appli) ;
	}

	public Liaison getCoucheLiaison()
	{
		return this.link ;
	}
}