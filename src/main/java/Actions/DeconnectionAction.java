/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gheorghe
 */
public class DeconnectionAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        session.removeAttribute("user");
        session.invalidate();
    }
    
}
