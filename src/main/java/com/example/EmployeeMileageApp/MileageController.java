/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.EmployeeMileageApp;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;
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
public class MileageController {

    @Autowired
    private MileageDAO mileage_dao;
    
    @Autowired
    private LocationDAO location_dao;
    
    @GetMapping("/")
    public String viewAddMileagePage(Model model) {
        Mileage mileage = new Mileage();
        List<Location> listLocation= location_dao.list();
        model.addAttribute("mileage", mileage);
        model.addAttribute("listLocation", listLocation);
        return "add-mileage.html";
    }
    
    @PostMapping(value = "/save/mileage")
    public String save(@ModelAttribute("Mileage")Mileage mileage,@Valid Mileage mileage2,BindingResult bindingResult) { 
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        
        mileage_dao.save(mileage);

        return "redirect:/";
    }
    
    @GetMapping("/mileage")
    public String viewManageMileagePage(Model model) {
        List<Mileage> listMileage = mileage_dao.list(1);
        double totalMiles = mileage_dao.getMiles(1);
        String reimbursement = mileage_dao.getReimbursement(1);
        
        model.addAttribute("totalMiles", totalMiles);
        model.addAttribute("reimbursement", reimbursement);
        if(listMileage != null){
            model.addAttribute("listMileage", listMileage);
        }
        return "manage-mileage.html";
    }
    
    @GetMapping("/edit/mileage/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        List<Location> listLocation= location_dao.list();
        ModelAndView mav = new ModelAndView("edit-mileage");
        Mileage mileage = mileage_dao.get(id);
        mav.addObject("mileage", mileage);
        mav.addObject("listLocation", listLocation);

        return mav;
    }
    @GetMapping(value = "/delete/mileage/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        mileage_dao.delete(id);
        return "redirect:/mileage";
    }
    @PostMapping(value = "/update/mileage")
    public String update(@ModelAttribute("mileage") Mileage mileage) {
        mileage_dao.update(mileage);

        return "redirect:/mileage";
    }
}
