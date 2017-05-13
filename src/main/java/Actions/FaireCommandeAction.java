/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import Actions.Action;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Restaurant;

/**
 *
 * @author Gheorghe
 */
public class FaireCommandeAction extends Action {
    public class Item{
         long id;
         String quantity;   
         
    }    
    public FaireCommandeAction() {
    }

    @Override
    public void execute(HttpServletRequest request) {
        System.out.println("newCommande");        
        System.out.println(request.getParameter("data"));
        String jsonData=(request.getParameter("data"));        
        Item[] data = new Gson().fromJson(jsonData, Item[].class);            
        Commande c= new Commande();
        c.setClient((Client)request.getSession(false).getAttribute("user"));
        c.setRestaurant((Restaurant)service.trouverRestaurantParId(Long.parseLong(request.getParameter("id"))));
        for (Item i:data)
        {
            c.AjouterProduit(service.trouverProduitParId(i.id), Integer.parseInt(i.quantity));           
        }
        service.AttribuerUnLivreurAUneCommandeEtEnregistrer(c);        
    }
    
}
