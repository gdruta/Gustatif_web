package Actions;


import javax.servlet.http.HttpServletRequest;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gheorghe
 */
public class InscriptionLivreurAction extends Action {

    public InscriptionLivreurAction() {
    }

    @Override
    public void execute(HttpServletRequest request) {
        Livreur livreur= new LivreurHumain(request.getParameter("nom"),
                                  request.getParameter("prenom"),
                                  request.getParameter("mail"),
                                  request.getParameter("adresse"),
                                  Integer.parseInt(request.getParameter("poid") ) );
        boolean result=this.service.enregistrerUnLivreur(livreur);
        request.setAttribute("result",result);
    }
    
}
