/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formatage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;
import metier.modele.Produit;
import metier.modele.ProduitDeCommande;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;

/**
 *
 * @author Gheorghe
 */
public class Formatage {
    static public String printFail(){       
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("result","fail");
        JsonObject container = new JsonObject();
        container.add("return",jsonObj);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json= gson.toJson(container);
        return json;
    }    
    static public String printSucces(){       
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("result","succes");
        JsonObject container = new JsonObject();
        container.add("return",jsonObj);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json= gson.toJson(container);
        return json;
    }
    public static String getClient(Client client) {
        JsonObject jsonClient = new JsonObject();

        if (client != null) { //Si on a bien un restaurant
            jsonClient.addProperty("nom", client.getNom());
            jsonClient.addProperty("prenom", client.getPrenom());            
        }

        JsonObject container = new JsonObject();
        container.add("user",jsonClient);        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String json= gson.toJson(container);
        return json;
    }
    public static String getLivreur(Livreur livreur) {
        JsonObject jsonClient = new JsonObject();

        if (livreur != null) { //Si on a bien un restaurant
            jsonClient.addProperty("nom", ((LivreurHumain) livreur).getNom());
            jsonClient.addProperty("prenom", ((LivreurHumain) livreur).getPrenom());            
        }

        JsonObject container = new JsonObject();
        container.add("user",jsonClient);        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String json= gson.toJson(container);
        return json;
    }
    public static String getClientInfo(Client client) {
        JsonObject jsonClient = new JsonObject();

        if (client != null) { 
            jsonClient.addProperty("id", client.getId());
            jsonClient.addProperty("mail",client.getMail());
            jsonClient.addProperty("nom", client.getNom());
            jsonClient.addProperty("prenom", client.getPrenom());
            jsonClient.addProperty("adresse", client.getAdresse());            
        }

        JsonObject container = new JsonObject();
        container.add("user",jsonClient);        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String json= gson.toJson(container);
        return json;
    }
    public static String getLivreurInfo(Livreur livreur) {
        JsonObject jsonClient = new JsonObject();

        if (livreur != null) { 
            jsonClient.addProperty("id", livreur.getId());
            jsonClient.addProperty("mail",((LivreurHumain) livreur).getMail());
            jsonClient.addProperty("nom", ((LivreurHumain) livreur).getNom());
            jsonClient.addProperty("prenom", ((LivreurHumain) livreur).getPrenom());
            jsonClient.addProperty("adresse", ((LivreurHumain) livreur).getAdresse()); 
            jsonClient.addProperty("poid", livreur.getChargeMax()); 
        }

        JsonObject container = new JsonObject();
        container.add("user",jsonClient);        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String json= gson.toJson(container);
        System.out.println(json);
        return json;
    }

    public static void sendListRestaurants(PrintWriter out, List<Restaurant> restaurants) {
        JsonArray jsonList = new JsonArray();       
        
        for (Restaurant r : restaurants){
            JsonObject jsonResto = new JsonObject();
            
            jsonResto.addProperty("id",r.getId());
            jsonResto.addProperty("denomination",r.getDenomination());  
            jsonResto.addProperty("description",r.getDescription());  
            jsonResto.addProperty("adresse",r.getAdresse());  
            jsonResto.addProperty("latitude",r.getLatitude());
            jsonResto.addProperty("longitude",r.getLongitude());
            jsonList.add(jsonResto);
        }
        JsonObject container = new JsonObject();
        container.add("restaurants",jsonList);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json= gson.toJson(container);
        
        out.println(json);
    }    

    public static void sendInfoRestaurant(PrintWriter out, Restaurant r) {
        JsonObject jsonResto = new JsonObject();
        jsonResto.addProperty("denomination",r.getDenomination());  
        
        JsonArray jsonList = new JsonArray();       
        List<Produit> produits=r.getProduits();
        for (Produit p : produits){
            JsonObject jsonProduit = new JsonObject();
            
            jsonProduit.addProperty("id",p.getId());
            jsonProduit.addProperty("denomination",p.getDenomination());  
            jsonProduit.addProperty("description",p.getDescription()); 
            jsonProduit.addProperty("prix",p.getPrix());              
            jsonList.add(jsonProduit);
        }                
        jsonResto.add("produits",jsonList);
        
        JsonObject container = new JsonObject();
        container.add("restaurant",jsonResto);        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        String json= gson.toJson(container);
        
        out.println(json);
    }
    
    public static void sendListCommandesClient(PrintWriter out,List<Commande> commandes){
        SimpleDateFormat dateSimple = new SimpleDateFormat("dd MMM yyyy");
        JsonArray jsonList = new JsonArray();
        ServiceMetier sm = new ServiceMetier();                
        for (Commande c : commandes){
            JsonObject jsonCmd = new JsonObject();            
            jsonCmd.addProperty("id",c.getId());            
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
        out.println(json);
    }
    
    public static void sendInfoCommande(PrintWriter out, Commande c) {             
        JsonObject jsonCmd = new JsonObject();
            System.out.println(c.getDateCommande());
        jsonCmd.addProperty("id",c.getId());
        SimpleDateFormat dateSimple1 = new SimpleDateFormat("dd MMM YYYY");
        
        jsonCmd.addProperty("date", dateSimple1.format(c.getDateCommande()));
        SimpleDateFormat heure = new SimpleDateFormat("HH");
        jsonCmd.addProperty("heure", heure.format(c.getDateCommande()));
        SimpleDateFormat minute = new SimpleDateFormat("mm");
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
        Livreur l = c.getLivreur();

        if(l.estHumain()){
            jsonCmd.addProperty("type","Humain");
            jsonCmd.addProperty("nom", "Jean paul");
        }else{
            jsonCmd.addProperty("type","Drone");
            jsonCmd.addProperty("nom", l.getVersion());
        }
        
        
        jsonCmd.addProperty("idL", l.getId());
        
        List<ProduitDeCommande> contenu = c.getContenu();        
        
        JsonArray jsonListProd = new JsonArray();
        for (ProduitDeCommande p : contenu){
            JsonObject jsonProd = new JsonObject();
            
            jsonProd.addProperty("quantite",p.getQuantite());
            jsonProd.addProperty("denomP",p.getProduit().getDenomination());
            jsonProd.addProperty("prix",p.getProduit().getPrix());
            
            jsonListProd.add(jsonProd);
        }

        JsonObject container = new JsonObject();
        container.add("commande",jsonCmd);
        container.add("produits", jsonListProd);
        
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json= gson.toJson(container);
        
        out.println(json);
    }
}
