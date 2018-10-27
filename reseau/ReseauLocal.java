package reseau ;

import reseau.couches.* ;
import java.util.ArrayList ;

public class ReseauLocal{

	protected ArrayList<Liaison> link ;

	public ReseauLocal()
	{
		this.link = new ArrayList<Liaison>(100) ;
	}

	public ReseauLocal(Machine ... m)
	{
		super();
		this.link = new ArrayList<>(100);
		for ( Machine mPrime : m ){
			this.ajouter(mPrime);
			mPrime.getCoucheLiaison().setReseau(this);
		}
	}

	public void ajouter(Machine m)
	{
		Liaison l = m.getCoucheLiaison() ;
		link.add(l) ;
		System.out.println("ajout");
	}

	public void sendTrame(Message trame)
	{
		for (Liaison liais : this.link){
			liais.receiveMessage(trame) ;
		}
	}
}