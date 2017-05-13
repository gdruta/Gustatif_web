package Actions;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.List;
import metier.modele.Commande;
import metier.modele.Livreur;
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
public class InfoCommande {
    static public String printListeCommandes(){
        JsonArray jsonList = new JsonArray();
        ServiceMetier sm = new ServiceMetier();        
        List<Commande> commandes = sm.listerLesCommandes();
        for (Commande c : commandes){
            JsonObject jsonCmd = new JsonObject();
            
            jsonCmd.addProperty("id",c.getId());
            
            SimpleDateFormat dateSimple = new SimpleDateFormat("dd MMM yyyy");
            
            jsonCmd.addProperty("date", dateSimple.format(c.getDateCommande()));
            
            if(c.getEstLivree()){
                jsonCmd.addProperty("statut","Cloturée");
            }else{
                jsonCmd.addProperty("statut","En cours");
            }
            jsonCmd.addProperty("restaurant", c.getRestaurant().getDenomination());
            jsonList.add(jsonCmd);
        }
        JsonObject container = new JsonObject();
        container.add("commandes",jsonList);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json= gson.toJson(container);
        return json;
    }
    
    static public String printListeCommandesById(long id){
        JsonArray jsonList = new JsonArray();
        ServiceMetier sm = new ServiceMetier();        
        Commande c = sm.trouverCommandeParId(id);
        JsonObject jsonCmd = new JsonObject();
            
        jsonCmd.addProperty("id",c.getId());
        SimpleDateFormat dateSimple1 = new SimpleDateFormat("dd MMM YYYY");
        
        jsonCmd.addProperty("date", dateSimple1.format(c.getDateCommande()));
        SimpleDateFormat heure = new SimpleDateFormat("HH");
        jsonCmd.addProperty("heure", heure.format(c.getDateCommande()));
        SimpleDateFormat minute = new SimpleDateFormat("MM");
        jsonCmd.addProperty("minute", minute.format(c.getDateCommande()));
        jsonCmd.addProperty("duree", c.getDureeEstimee());
        jsonCmd.addProperty("poidsTT", c.getPoidsTotal());
        jsonCmd.addProperty("prixTT", c.getPrixTotal());
        
        
        if(c.getEstLivree()){
            jsonCmd.addProperty("statut","Livrée le " + dateSimple1.format(c.getDateLivraison()));
        }else{
            jsonCmd.addProperty("statut","En cours de livraison");
        }
        jsonCmd.addProperty("denomR", c.getRestaurant().getDenomination());
        
        jsonCmd.addProperty("adrR", c.getRestaurant().getAdresse());
        Livreur l = sm.trouverLivreurParId(c.getLivreur().getId());

        if(l.estHumain()){
            jsonCmd.addProperty("type","Humain");
            jsonCmd.addProperty("nom", "Jean paul");
        }else{
            jsonCmd.addProperty("type","Drone");
            jsonCmd.addProperty("nom", l.getVersion());
        }
        
        
        jsonCmd.addProperty("idL", l.getId());
        
        List<ProduitDeCommande> contenu = c.getContenu();
        
        jsonList.add(jsonCmd);
        JsonArray jsonListProd = new JsonArray();
        for (ProduitDeCommande p : contenu){
            JsonObject jsonProd = new JsonObject();
            
            jsonProd.addProperty("quantite",p.getQuantite());
            jsonProd.addProperty("denomP",p.getProduit().getDenomination());
            jsonProd.addProperty("prix",p.getProduit().getPrix());
            
            jsonListProd.add(jsonProd);
        }

        JsonObject container = new JsonObject();
        container.add("commande",jsonList);
        container.add("produits", jsonListProd);
        
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json= gson.toJson(container);
        return json;
    }
    
    static public void cloturer(Long id){
        ServiceMetier sm = new ServiceMetier();
        sm.cloturerCommandeLivree(sm.trouverCommandeParId(id));
    }
}
