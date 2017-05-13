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
import metier.modele.Restaurant;

/**
 *
 * @author Gheorghe
 */
@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

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
                Action a = new LoginAction();
                a.execute(request);
                if (request.getAttribute("client")!=null){
                    session.setAttribute("user",request.getAttribute("client"));
                    out.println(FormatageLogin.printSucces());
                }else{
                    out.println(FormatageLogin.printFail());
                }
            }else if ("inscriptionClient".equals(action)){
                Action a = new InscriptionAction();
                a.execute(request);
                boolean result=(boolean) request.getAttribute("result");
                if (result){
                    out.println(FormatageLogin.printSucces());
                }else{
                    out.println(FormatageLogin.printFail());
                }
            }else{
                
                //session verification
                Client sessionUser = (Client) session.getAttribute("user");
                
                if (sessionUser==null){
                    //redirection to login page
                    this.getServletContext().getRequestDispatcher("/index.html").forward(request, response);
                }else{
                    switch (action){
                        case "getUser":
                            out.println(Formatage.getClient(sessionUser));
                            break;
                        case "getUserInfo":
                            out.println(Formatage.getClientInfo(sessionUser));
                            break;
                        case "getListCommandes":
                            Action a = new PrintCommandesClientAction();
                            a.execute(request);
                            Formatage.sendListCommandesClient(out, (List<Commande>) request.getAttribute("commandes"));
                            break;
                        case "getListRestaurants":
                            Action a1 = new PrintRestoAction();
                            a1.execute(request);                            
                            Formatage.sendListRestaurants(out, (List<Restaurant>) request.getAttribute("restaurants"));
                            break;
                        case "restaurant":
                            Action a2 = new InfoRestaurantAction();                            
                            a2.execute(request);  
                            Formatage.sendInfoRestaurant(out, (Restaurant) request.getAttribute("restaurant"));
                            break;
                        case "newCommande":
                            Action a3 = new FaireCommandeAction();
                            a3.execute(request);
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
