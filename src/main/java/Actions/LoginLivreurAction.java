package Actions;


import javax.servlet.http.HttpServletRequest;
import metier.modele.Livreur;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gheorghe
 */
public class LoginLivreurAction extends Action{

    @Override
    public void execute(HttpServletRequest request) {
        String mail = request.getParameter("mail");        
        Livreur livreur=this.service.trouverLivreurParEMail(mail);
        request.setAttribute("client",livreur);        
    }
    
}
