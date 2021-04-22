/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.archil.spreadmodel.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import me.archil.spreadmodel.model.Person;
import me.archil.spreadmodel.model.Simulation;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Achiko
 */
@Controller
public class DataController {

    @RequestMapping(value = "/data.json", method = RequestMethod.GET)
    public @ResponseBody
    Simulation getSim(HttpSession session, HttpServletRequest request) {
        Simulation sim = null;
        int peopleNum = 1000;

        if (request.getParameter("people") != null) {
            peopleNum = Integer.parseInt(request.getParameter("people"));
            sim = new Simulation(peopleNum, 2000, 2000);
            session.setAttribute("sim", sim);
        }

        if (request.getParameter("clear") != null) {
            session.removeAttribute("sim");
        }

        if (session.getAttribute("sim") == null) {
            sim = new Simulation(peopleNum, 2000, 2000);
            session.setAttribute("sim", sim);
        } else {
            sim = (Simulation) session.getAttribute("sim");
        }

        if (request.getParameter("steps") != null) {
            int steps = Integer.parseInt(request.getParameter("steps"));
            if (sim != null) {
                sim.play(steps);
            }
        }

        return sim;
    }

}
