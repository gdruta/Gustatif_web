package Actions;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Commande;
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
public class PrintCommandeAction extends Action {
    @Override
    public void execute(HttpServletRequest request) {
        Commande c=service.trouverCommandeParId(Long.parseLong(request.getParameter("id")));          
        request.setAttribute("commande", c);
    }    
}
