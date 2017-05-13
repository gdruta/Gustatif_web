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
public class LoginAction extends Action{

    @Override
    public void execute(HttpServletRequest request) {
        String mail = request.getParameter("mail");        
        Client client=this.service.trouverClientParEMail(mail);
        request.setAttribute("client",client);        
    }
    
}
