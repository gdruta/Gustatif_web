package Actions;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.List;
import metier.modele.Client;
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
public class InfoClient{
    
    static public String printListeClients(){
        JsonArray jsonList = new JsonArray();
        ServiceMetier sm = new ServiceMetier();        
        List<Client> clients = sm.listerLesClients();
        for (Client c : clients){
            JsonObject jsonCl = new JsonObject(); 
            
            jsonCl.addProperty("nom", c.getNom());
            jsonCl.addProperty("prenom", c.getPrenom());
            jsonCl.addProperty("id", c.getId());
            jsonCl.addProperty("mail", c.getMail());
            jsonList.add(jsonCl);
        }
        JsonObject container = new JsonObject();
        container.add("clients",jsonList);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json= gson.toJson(container);
        return json;
    }
    
}