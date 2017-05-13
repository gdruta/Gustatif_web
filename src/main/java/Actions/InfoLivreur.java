package Actions;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.List;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurDrone;
import metier.modele.LivreurHumain;
import metier.modele.Produit;
import metier.modele.ProduitDeCommande;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gheorghe
 */
public class InfoLivreur{
    
    static public String printListeCommandes(){
        JsonArray jsonList = new JsonArray();
        ServiceMetier sm = new ServiceMetier();        
        List<Livreur> livreurs = sm.listerLesLivreurs();
        for (Livreur l : livreurs){
            JsonObject jsonLiv = new JsonObject();
            if(l instanceof LivreurDrone){
                LivreurDrone ld = (LivreurDrone)l;   
                jsonLiv.addProperty("type", "Drone");
                jsonLiv.addProperty("nom", ld.getNumeroDeSerie());
                jsonLiv.addProperty("id", ld.getId());
            }else{
                LivreurHumain lh = (LivreurHumain)l;   
                jsonLiv.addProperty("type", "Humain");
                jsonLiv.addProperty("nom", lh.getNom()+" "+lh.getPrenom());
                jsonLiv.addProperty("id", lh.getId());
            }
            jsonList.add(jsonLiv);
        }
        JsonObject container = new JsonObject();
        container.add("livreurs",jsonList);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json= gson.toJson(container);
        return json;
    }
    
    static public String printLivreurById(long id){
        JsonArray jsonList = new JsonArray();
        ServiceMetier sm = new ServiceMetier();        
        Livreur l = sm.trouverLivreurParId(id);
        JsonObject jsonLiv = new JsonObject();
            
        jsonLiv.addProperty("id",l.getId());
        
        jsonLiv.addProperty("adresse", l.getAdresseBase());        
        
        if(l instanceof LivreurDrone){
            LivreurDrone ld = (LivreurDrone)l;
            jsonLiv.addProperty("type", "Drone");
            jsonLiv.addProperty("capacite", ld.getChargeMax());
            jsonLiv.addProperty("vitesse", ld.getVitesseMoyenne());
            jsonLiv.addProperty("numero", ld.getNumeroDeSerie());
        }else{
            LivreurHumain lh = (LivreurHumain)l;
            jsonLiv.addProperty("type", "Humain");
            jsonLiv.addProperty("nom", lh.getNom());
            jsonLiv.addProperty("prenom", lh.getPrenom());
            jsonLiv.addProperty("mail", lh.getMail());
            jsonLiv.addProperty("capacite", lh.getChargeMax());
            
        }

        jsonList.add(jsonLiv);
        List<Commande> historique = sm.listerLesCommandes();
        
        
        JsonArray jsonListCmd = new JsonArray();
        for (Commande c : historique){
            if(c.getLivreur().getId() == id){
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("id", c.getId());
                jsonCmd.addProperty("prix", c.getPrixTotal());
                if(c.getEstLivree()){
                    jsonCmd.addProperty("statut", "Cloturée");
                }else{
                    jsonCmd.addProperty("statut", "En cours");
                }
                SimpleDateFormat dateSimple1 = new SimpleDateFormat("dd MMM YYYY");
                jsonCmd.addProperty("date", dateSimple1.format(c.getDateCommande()));
                jsonListCmd.add(jsonCmd);
            }
        }

        JsonObject container = new JsonObject();
        container.add("livreur",jsonList);
        container.add("commandes", jsonListCmd);
        
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json= gson.toJson(container);
        return json;
    }
    
}
