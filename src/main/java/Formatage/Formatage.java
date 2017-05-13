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
import metier.modele.Produit;
import metier.modele.Restaurant;
import metier.service.ServiceMetier;

/**
 *
 * @author Gheorghe
 */
public class Formatage {
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
    public static String getClientInfo(Client client) {
        JsonObject jsonClient = new JsonObject();

        if (client != null) { 
            jsonClient.addProperty("id", client.getId());
            jsonClient.addProperty("mail", client.getMail());
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
}
