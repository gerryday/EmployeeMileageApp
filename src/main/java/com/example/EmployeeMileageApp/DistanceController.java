/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EmployeeMileageApp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DistanceController {

    @Autowired
    private DistanceDAO distance_dao;
    
    @Autowired
    private LocationDAO location_dao;
    
    @GetMapping("/configuration")
    public String viewConfigurationPage(Model model) {
        Distance distance = new Distance();
        List<Location> listLocation= location_dao.list();
        List<Distance> listDistances = distance_dao.list();
        model.addAttribute("listDistances", listDistances);
        model.addAttribute("listLocation", listLocation);
        model.addAttribute("distance", distance);
        return "add-mileagerate.html";
    }
    @PostMapping(value = "/save/distance")
    public String save(@ModelAttribute("Distance") Distance distance) {           
        distance_dao.save(distance);

        return "redirect:/configuration";
    }
    
    @GetMapping("/edit/distance/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        List<Location> listLocation= location_dao.list();
        ModelAndView mav = new ModelAndView("edit-distance");
        Distance distance = distance_dao.get(id);
        mav.addObject("distance", distance);
        mav.addObject("listLocation", listLocation);

        return mav;
    }
    
    @GetMapping(value = "/delete/distance/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        distance_dao.delete(id);
        return "redirect:/configuration";
    }
    @PostMapping(value = "/update/distance")
    public String update(@ModelAttribute("distance") Distance distance) {
        distance_dao.update(distance);

        return "redirect:/configuration";
    }
}
