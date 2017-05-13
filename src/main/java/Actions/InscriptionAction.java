package Actions;


import javax.servlet.http.HttpServletRequest;
import metier.modele.Client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gheorghe
 */
public class InscriptionAction extends Action {

    public InscriptionAction() {
    }

    @Override
    public void execute(HttpServletRequest request) {
        Client client= new Client(request.getParameter("nom"),
                                  request.getParameter("prenom"),
                                  request.getParameter("mail"),
                                  request.getParameter("adresse"));
        boolean result=this.service.enregistrerUnClient(client);
        request.setAttribute("result",result);
    }
    
}
