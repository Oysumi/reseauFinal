package reseau.tests;

import reseau.couches.*;
import reseau.* ;
import reseau.adresses.*; 
import reseau.clientsServeurs.*;
 
/**
 * @author martine
 */
public class TestReseau {

    public TestReseau() {

        String nom ;
        Adresse adr ;
        AdresseMac adrm ;
        Adresse masque ;

        //---------------------------------
        // Une machine
        nom = "zebulon" ;
        adr = new Adresse ("192.23.23.23") ;
        adrm = new AdresseMac("00.01.02.03.04.05") ;
        masque = new Adresse("255.255.0.0");
        Machine first = new Machine(nom, adr, adrm, masque) ;
        
        // Les couches  
        int portNum = 45;
        Application appliClientNum = new ClientNumerique(portNum, first) ;
        first.ajouter(portNum, appliClientNum);

        //---------------------------------
        // Une autre machine d'adresse IP "192.23.12.12"
        String nom2; 
        Adresse adr2; 
        AdresseMac adrm2; 
        
        nom2 = "zeus";
        adr2 = new Adresse("192.23.12.12");
        adrm2 = new AdresseMac("24.88.90.00.FF.AB") ;
        Machine sec = new Machine(nom2, adr2, adrm2, masque) ;
        
        // Les couches
        int portFoisDeux = 888;
        Application appliFoisDeux = new ServeurFoisDeux(portFoisDeux, sec);

        // Ajout d'une deuxième application pour la seconde machine
        int portMaj = 555 ;
        Application appliMaj = new ServeurMaj(portMaj, sec);

        // Création du réseau local
        ReseauLocal rl = new ReseauLocal(first, sec) ;

        // Envoi d'un message
        int valeur ;
        Message mess ;
        Adresse dest ;
        Message res ;
        String mot ;

        valeur = 10;
        mess = new Message(valeur) ;
        dest = new Adresse("192.23.12.12");
        System.out.println("Je voudrais le *2 de l'entier "+valeur+"\n");
        appliClientNum.sendMessage(dest, portFoisDeux, mess) ;
        res = appliClientNum.getResultat() ;
        System.out.println("Et j'obtiens "+res.extraireEntier(0)+"\n");
        System.out.println("\n===========================================================================\n") ;

        // Envoi d'un message
        mess = new Message("facultenancy");
        dest = new Adresse("192.23.12.12");
        System.out.println("Je voudrais transformer \"" + mess.extraireChaine() +"\" en majuscules.\n");
        appliClientNum.sendMessage(dest, portMaj, mess) ;
        res = appliClientNum.getResultat() ;
        mot = res.extraireChaine() ;
        System.out.println("Et j'obtiens : \""+mot+"\"\n") ;

    }

    public static void main(String[] args) {
        new TestReseau();
    }
    
}