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
public class LocationController {

    @Autowired
    private LocationDAO dao;
    
    @GetMapping("/location")
    public String viewLocationPage(Model model) {
        Location location = new Location();
        List<Location> listLocations = dao.list();
        model.addAttribute("listLocations", listLocations);
        model.addAttribute("location", location);
        return "add-location.html";
    }
    @PostMapping(value = "/save/location")
    public String save(@ModelAttribute("Location") Location location) {           
        dao.save(location);

        return "redirect:/location";
    }
    
    @GetMapping("/edit/location/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit-location");
        Location location = dao.get(id);
        mav.addObject("location", location);

        return mav;
    }
    
    @GetMapping(value = "/delete/location/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        dao.delete(id);
        return "redirect:/location";
    }
    
    @PostMapping(value = "/update/location")
    public String update(@ModelAttribute("location") Location location) {
        dao.update(location);

        return "redirect:/location";
    }
}
