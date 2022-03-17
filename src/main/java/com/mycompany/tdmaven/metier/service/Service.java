/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tdmaven.metier.service;

import com.mycompany.tdmaven.dao.ClientDAO;
import com.mycompany.tdmaven.dao.JpaUtil;
import com.mycompany.tdmaven.metier.modele.Client;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bbbbb
 */
public class Service {
    protected ClientDAO clientDao = new ClientDAO();

    public Long inscrireClient(Client client) {
        Long resultat = null;
        JpaUtil.creerContextePersistance();
        try {
            JpaUtil.ouvrirTransaction();
            clientDao.creer(client);
            JpaUtil.validerTransaction();
            resultat = client.getId();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service inscrireClient(client)", ex);
            JpaUtil.annulerTransaction();
            resultat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }    
    
    public Client rechercherClientParId(Long unId) {
        Client c = null;
        JpaUtil.creerContextePersistance();
        try {
            c = clientDao.rechercheClient(unId);
        } catch(Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel "
                    + "au Service rechercherClientParId(id)", ex);
            c = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c;
    }
    
    public List<Client> listerClients() {
        List<Client> uneListe = null;
        JpaUtil.creerContextePersistance();
        try {
            uneListe = clientDao.listerClients();
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service listerClients()", ex);
            uneListe = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return uneListe;
    }
    
    public Client authentifierClient(String mail, String motDePasse) {
        Client res = null;
        JpaUtil.creerContextePersistance();
        try {
            Client c = clientDao.chercherClientParMail(mail);
            if (c != null) {
                if (c.getmotDePasse().compareTo(motDePasse) == 0) {
                    res = c;
                }
            }
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception lors de l'appel au Service authentifierClient(mail,motDePasse)", ex);
            res = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }
}
