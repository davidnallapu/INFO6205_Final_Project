/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.archil.spreadmodel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import me.archil.spreadmodel.model.Simulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Achiko
 */

@Controller
public class MainController {
    
    @Autowired
    ApplicationContext context;
    
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        
        
//        MessageUtility msgUtil = context.getBean("messageUtility", MessageUtility.class);
//        
//        RandomMessage message = context.getBean("randomMessage", RandomMessage.class);
        
//        request.setAttribute("messageUtil", msgUtil);
//         request.setAttribute("message", message);
//                
//        HttpSession session = (HttpSession) request.getSession(true);
//        
//        if (session.getAttribute("sim") == null)
//        {
//            Simulation sim = new Simulation(100, 2000, 2000);
//            session.setAttribute("sim", sim);
//        }
        return new ModelAndView("index");

    }
    
}
