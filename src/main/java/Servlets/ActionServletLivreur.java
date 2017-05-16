package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Actions.FaireCommandeAction;
import Actions.*;
import Formatage.*;
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Long.parseLong;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;
import metier.modele.Restaurant;

/**
 *
 * @author Gheorghe
 */
@WebServlet(urlPatterns = {"/ActionServletLivreur"})
public class ActionServletLivreur extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action=request.getParameter("action");             
            if ("login".equals(action)){
                Action a = new LoginLivreurAction();
                a.execute(request);
                if (request.getAttribute("client")!=null){
                    session.setAttribute("user",request.getAttribute("client"));
                    out.println(Formatage.printSucces());
                }else{
                    out.println(Formatage.printFail());
                }
            }else if ("inscriptionLivreur".equals(action)){
                Action a = new InscriptionLivreurAction();
                a.execute(request);
                boolean result=(boolean) request.getAttribute("result");
                if (result){
                    out.println(Formatage.printSucces());
                }else{
                    out.println(Formatage.printFail());
                }
            }else{
                
                //session verification
                Livreur sessionUser = (Livreur) session.getAttribute("user");
                
                if (sessionUser==null){
                    //redirection to login page
                    this.getServletContext().getRequestDispatcher("/index.html").forward(request, response);
                }else{
                    switch (action){
                        case "getUser":
                            out.println(Formatage.getLivreur(sessionUser));
                            break;
                        case "getUserInfo":
                            out.println(Formatage.getLivreurInfo(sessionUser));
                            break;                        
                        case "listeCommandeEnCours":
                            String mail = ((LivreurHumain) sessionUser).getMail();
                            out.println(InfoCommande.printListeCommandesByLivreurEnCours(mail));
                            break;
                        case "listeCommandeCloturee":
                            String mail2 = ((LivreurHumain) sessionUser).getMail();
                            out.println(InfoCommande.printListeCommandesByLivreurCloturee(mail2));
                            break;
                    }
                            
                }
                
            }
        }
            
            
    }

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        JpaUtil.init();
    }

    @Override
    public void destroy() {
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
        JpaUtil.destroy();
    }
    
    

    

}
