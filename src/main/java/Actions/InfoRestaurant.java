package Actions;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
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
public class InfoRestaurant {
    static public String printInfo(long id){
        ServiceMetier sm = new ServiceMetier();        
        Restaurant restaurant = sm.trouverRestaurantParId(id);
        
        JsonObject jsonResto = new JsonObject();

        jsonResto.addProperty("id",restaurant.getId());
        jsonResto.addProperty("denomination",restaurant.getDenomination()); 
        jsonResto.addProperty("description",restaurant.getDescription());
        jsonResto.addProperty("adresse",restaurant.getAdresse());

        
        
        JsonObject container = new JsonObject();
        container.add("restaurant",jsonResto);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json= gson.toJson(container);
        return json;
    }
}
