package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Actions.Action;
import Actions.InfoClient;
import Actions.InfoLivreur;
import Actions.InfoRestaurant;
import Actions.InfoCommande;
import Actions.InfoRestaurantAction;
import Actions.PrintRestoAction;
import Formatage.Formatage;
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
import metier.modele.Restaurant;

/**
 *
 * @author Gheorghe
 */
@WebServlet(urlPatterns = {"/AdminActionServlet"})
public class AdminActionServlet extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action=request.getParameter("action");
            long id;
            switch(action){
                case "getListeRestaurants":                    
                    Action a1 = new PrintRestoAction();
                    a1.execute(request);                            
                    Formatage.sendListRestaurants(out, (List<Restaurant>) request.getAttribute("restaurants"));
                    break;
                case "restaurant":
                    Action a= new InfoRestaurantAction();
                    a.execute(request);
                    Formatage.sendInfoRestaurant(out,(Restaurant)request.getAttribute("restaurant"));
                    break;
                case "listeCommande":
                    out.println(InfoCommande.printListeCommandes());
                    break;
                case "commande":
                    id = parseLong(request.getParameter("id"));
                    out.println(InfoCommande.printListeCommandesById(id));
                    break;
                case "cloture":
                    id = parseLong(request.getParameter("id"));
                    InfoCommande.cloturer(id);
                    break;
                case "ListeLivreur":
                    out.println(InfoLivreur.printListeCommandes());
                    break;
                case "livreur":
                    id = parseLong(request.getParameter("id"));
                    out.println(InfoLivreur.printLivreurById(id));
                    break;
                case "listeCommandeEnCours":
                            String mail = request.getParameter("mail");
                            out.println(InfoCommande.printListeCommandesByLivreurEnCours(mail));
                            break;
                case "listeCommandeCloturee":
                            String mail2 = request.getParameter("mail");
                            out.println(InfoCommande.printListeCommandesByLivreurEnCours(mail2));
                            break;
                case "listeClients":
                    out.println(InfoClient.printListeClients());
                    break;
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
