/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tdmaven.ihm;

import com.mycompany.tdmaven.dao.JpaUtil;
import com.mycompany.tdmaven.metier.modele.Client;
import com.mycompany.tdmaven.metier.service.Service;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author bbbbb
 */
public class Main {

    public static void main(String[] args) {

        // TODO : Pensez à créer une unité de persistance "DASI-PU" et à vérifier son nom dans la classe JpaUtil
        // Contrôlez l'affichage du log de JpaUtil grâce à la méthode log de la classe JpaUtil
        //JpaUtil.init();
        //initialiserClients();            // Question 3
        //testerInscriptionClient();       // Question 4 & 5
        //testerRechercheClient();         // Question 6
        //testerListeClients();            // Question 7
        //testerAuthentificationClient();  // Question 8
        //saisirInscriptionClient();       // Question 9
        //saisirRechercheClient();
        //JpaUtil.destroy();
        
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("DASI-TD1");
        //EntityManager em = emf.createEntityManager();
        //EntityTransaction tx = em.getTransaction();
        //tx.begin();
        //Long i = Long.valueOf(1);
        //Client c1 = em.find(Client.class, i);
        //afficherClient(c1);
        //em.merge(c1);
        //tx.commit();
        //em.close();
    }

    public static void initialiserClients() {

        System.out.println();
        System.out.println("**** initialiserClients() ****");
        System.out.println();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DASI-TD1");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Client ada = new Client("Lovelace", "Ada", "ada.lovelace@insa-lyon.fr", "Ada1012");

        System.out.println();
        System.out.println("** Clients avant persistance: ");

        afficherClient(ada);
        System.out.println();

        try {
            tx.begin();
            em.persist(ada);
            tx.commit();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service", ex);
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }

        System.out.println();
        System.out.println("** Clients après persistance: ");

        afficherClient(ada);
        System.out.println();
    }

    public static void testerInscriptionClient() {

        System.out.println();
        System.out.println("**** testerInscriptionClient() ****");
        System.out.println();

        Service service = new Service();
        Client minh = new Client("Ngo", "Minh", "ngoc-minh.ngo@insa-lyon.fr", "123");
        Long idClaude = service.inscrireClient(minh);
        if (idClaude != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherClient(minh);
        System.out.println();
    }
    
    public static void testerRechercheClient() {
        System.out.println();
        System.out.println("**** testerRechercheClient() ****");
        System.out.println();     
        
        Service service = new Service();
        Client client = service.rechercherClientParId(Long.valueOf(1));
        if (client != null) {
            System.out.println("> Succès recherche client");
        } else {
            System.out.println("> Échec recherche client");
        }
        afficherClient(client);
        System.out.println();
    }
    
    public static void testerListeClients() {
        System.out.println();
        System.out.println("**** testerListeClients() ****");
        System.out.println();    
        
        Service service = new Service();
        List<Client> uneListe = service.listerClients();
        if (uneListe != null) {
            System.out.println("> Succès lister client");
        } else {
            System.out.println("> Échec lister client");
        }
        for (Client i : uneListe) {
            afficherClient(i);
        }
        System.out.println();
    }   
    
    public static void testerAuthentificationClient() {
        System.out.println();
        System.out.println("**** testerAuthentificationClient() ****");
        System.out.println();   
        
        Service service = new Service();
        Client client = service.authentifierClient("blaise.pascal@insa-lyon.fr", "Blaise1906");
        if (client != null) {
            System.out.println("> Succès authentifier client");
            afficherClient(client);
        } else {
            System.out.println("> Échec authentifier client");
        }
        System.out.println();
    }
    
    public static void saisirInscriptionClient() {
        Service service = new Service();

        System.out.println();
        System.out.println("Appuyer sur Entrée pour passer la pause...");
        Saisie.pause();

        System.out.println();
        System.out.println("**************************");
        System.out.println("** NOUVELLE INSCRIPTION **");
        System.out.println("**************************");
        System.out.println();

        String nom = Saisie.lireChaine("Nom (de famille) ? ");
        String prenom = Saisie.lireChaine("Prénom ? ");
        String mail = Saisie.lireChaine("Mail ? ");
        String motDePasse = Saisie.lireChaine("Mot de passe ? ");

        Client client = new Client(nom, prenom, mail, motDePasse);
        Long idClient = service.inscrireClient(client);

        if (idClient != null) {
            System.out.println("> Succès inscription");
        } else {
            System.out.println("> Échec inscription");
        }
        afficherClient(client);
        System.out.println();

    }

    public static void saisirRechercheClient() {
        Service service = new Service();

        System.out.println();
        System.out.println("*********************");
        System.out.println("** MENU INTERACTIF **");
        System.out.println("*********************");
        System.out.println();

        Saisie.pause();

        System.out.println();
        System.out.println("**************************");
        System.out.println("** RECHERCHE de CLIENTS **");
        System.out.println("**************************");
        System.out.println();
        System.out.println();
        System.out.println("** Recherche par Identifiant:");
        System.out.println();

        Integer idClient = Saisie.lireInteger("Identifiant ? [0 pour quitter] ");
        while (idClient != 0) {
            Client client = service.rechercherClientParId(idClient.longValue());
            if (client != null) {
                afficherClient(client);
            } else {
                System.out.println("=> Client #" + idClient + " non-trouvé");
            }
            System.out.println();
            idClient = Saisie.lireInteger("Identifiant ? [0 pour quitter] ");
        }

        System.out.println();
        System.out.println("********************************");
        System.out.println("** AUTHENTIFICATION de CLIENT **");
        System.out.println("********************************");
        System.out.println();
        System.out.println();
        System.out.println("** Authentifier Client:");
        System.out.println();

        String clientMail = Saisie.lireChaine("Mail ? [0 pour quitter] ");

        while (clientMail.compareTo("0") != 0) {
            String clientMotDePasse = Saisie.lireChaine("Mot de passe ? ");
            Client client = service.authentifierClient(clientMail, clientMotDePasse);
            if (client != null) {
                afficherClient(client);
            } else {
                System.out.println("=> Client non-authentifié");
            }
            clientMail = Saisie.lireChaine("Mail ? [0 pour quitter] ");
        }

        System.out.println();
        System.out.println("*****************");
        System.out.println("** AU REVOIR ! **");
        System.out.println("*****************");
        System.out.println();

    }

    public static void afficherClient(Client client) {
        System.out.println("-> Client: id=" + client.getId() + ";nom=" + client.getNom()
                            + ";prenom=" + client.getPrenom() + ";mail=" + client.getMail()
                            + ";motDePasse=" + client.getmotDePasse());
    }
    
}
