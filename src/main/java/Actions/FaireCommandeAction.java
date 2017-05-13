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

/**
 *
 * @author Gheorghe
 */
public class FaireCommandeAction extends Action {
    public class Item{
        private long id;
        private String quantity;        
    }    
    public FaireCommandeAction() {
    }

    @Override
    public void execute(HttpServletRequest request) {
        System.out.println("newCommande");        
        System.out.println(request.getParameter("data"));
        String jsonData=(request.getParameter("data"));        
        try{
            Item[] data = new Gson().fromJson(jsonData, Item[].class);
            System.out.println(data[0]);
        }catch(JsonSyntaxException e){
            System.out.println(e);
        }
        
    }
    
}
