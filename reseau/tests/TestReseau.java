package reseau.tests;

import reseau.couches.*;
import reseau.* ;
import reseau.adresses.*; 
import reseau.clientsServeurs.*;
import reseau.tables.DNS ;

/**
 * @author martine
 */
public class TestReseau {

    public TestReseau() {

        // Création de la table DNS
        DNS dnsTable = new DNS() ;

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
        
        // Création de l'application clientDNS
        int portClientF = 745 ;
        Application clientFirst = new ClientDNS(portClientF, first, adr);

        // Création de l'application serveurDNS lié à la machine disiz
        int portDNS = 365;
        // Application appliServeurDNS = new ServeurDNS(portDNS, first, client, dnsTable);

        /*first.ajouter(portDNS, appliServeurDNS);
        first.ajouter(portClient, client);*/

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
        int portNum = 45;
        int portClientS = 452 ;
        Application clientSec = new ClientDNS(portClientS, sec, adr);
        //Application appliClientNum = new ClientNumerique(portNum, sec, client) ;
        /*sec.ajouter(portNum, appliClientNum);
        sec.ajouter(portClient, client);*/

        //----------------------------------
        // Une autre machine
        String nom3;
        Adresse adr3;
        AdresseMac adrm3;

        nom3 = "disiz";
        adr3 = new Adresse("192.23.89.41");
        adrm3 = new AdresseMac("AA.CD.EF.00.AA.54");
        Machine third = new Machine(nom3, adr3, adrm3, masque);

        // Les couches
        int portFoisDeux = 888;
        int portClientT = 378 ;
        Application clientThird = new ClientDNS(portClientT, third, adr);
        // Application appliFoisDeux = new ServeurFoisDeux(portFoisDeux, third, client);

        // Ajout d'une deuxième application pour la troisième machine
        int portMaj = 555 ;
        // Application appliMaj = new ServeurMaj(portMaj, third, client);

        /*third.ajouter(portFoisDeux, appliFoisDeux);
        third.ajouter(portMaj, appliMaj);
        third.ajouter(portClient, client);*/

        // Création du réseau local
        ReseauLocal rl = new ReseauLocal(first, sec, third) ;

        // Ajout des machines dans la table DNS
        dnsTable.ajouter(nom, adr);
        dnsTable.ajouter(nom2, adr2);
        dnsTable.ajouter(nom3, adr3);

        Application appliServeurDNS = new ServeurDNS(portDNS, first, clientFirst, dnsTable);
        Application appliClientNum = new ClientNumerique(portNum, sec, clientSec) ;
        Application appliFoisDeux = new ServeurFoisDeux(portFoisDeux, third, clientThird);
        Application appliMaj = new ServeurMaj(portMaj, third, clientThird);

        // Envoi d'un message
        int valeur ;
        Message mess ;
        Adresse dest ;
        Message res ;
        String mot ;

        valeur = 10;
        mess = new Message(valeur) ;
        dest = new Adresse("192.23.89.41");
        System.out.println("\nJe voudrais le *2 de l'entier "+valeur+"\n");
        appliClientNum.sendMessage("disiz", portFoisDeux, mess) ;
        res = appliClientNum.getResultat() ;
        System.out.println("Et j'obtiens "+res.extraireEntier(0));
        System.out.println("======================================================================================\n") ;

        // Envoi d'un message
        mess = new Message("facultenancy");
        dest = new Adresse("192.23.89.41");
        System.out.println("Je voudrais transformer \"" + mess.extraireChaine() +"\" en majuscules.\n");
        appliClientNum.sendMessage("disiz", portMaj, mess) ;
        res = appliClientNum.getResultat() ;
        mot = res.extraireChaine() ;
        System.out.println("Et j'obtiens : \""+mot+"\"") ;
        System.out.println("======================================================================================\n") ;

        // Demande d'adresse IP
        mess = new Message("disiz");
        dest = new Adresse("192.23.23.23");
        System.out.println("Je voudrais l'adresse IP de la machine "+mess.extraireChaine()+"\n");
        appliClientNum.sendMessage("zebulon", portDNS, mess);
        res = appliClientNum.getResultat() ;
        System.out.println("Et j'obtiens : \""+res+"\"") ;
        System.out.println("======================================================================================\n") ;

    }

    public static void main(String[] args) {
        new TestReseau();
    }
    
}