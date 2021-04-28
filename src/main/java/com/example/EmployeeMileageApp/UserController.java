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
public class UserController {

    @Autowired
    private UserDAO user_dao;
    
    @GetMapping("/user")
    public String viewEmployeePage(Model model) {
        List<User> listUserTypes = user_dao.listTypes();
        List<User> listUsers= user_dao.list();
        User user = new User();
        model.addAttribute("listUserTypes", listUserTypes);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("user", user);
        return "add-user.html";
    }
    @PostMapping(value = "/save/user")
    public String save(@ModelAttribute("User") User user) {
        user_dao.save(user);
        return "redirect:/user";
    }
    
    @GetMapping("/edit/user/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit-user");
        List<User> listUserTypes = user_dao.listTypes();
        User user = user_dao.get(id);
        mav.addObject  ("listUserTypes", listUserTypes);
        mav.addObject("user", user);

        return mav;
    }
    
    @GetMapping(value = "/delete/user/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        user_dao.delete(id);
        return "redirect:/user";
    }
    
    @PostMapping(value = "/update/user")
    public String update(@ModelAttribute("employee") User user) {
        user_dao.update(user);

        return "redirect:/user";
    }
}
