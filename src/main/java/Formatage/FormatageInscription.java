package Formatage;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gheorghe
 */
public class FormatageInscription {
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
}
