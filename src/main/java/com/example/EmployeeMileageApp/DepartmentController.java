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
public class DepartmentController {

    @Autowired
    private DepartmentDAO dao;
    
    @GetMapping("/department")
    public String viewDepartmentPage(Model model) {
        Department department = new Department();
        List<Department> listDepartments = dao.list();
        model.addAttribute("listDepartments", listDepartments);
        model.addAttribute("department", department);
        return "add-department.html";
    }
    @PostMapping(value = "/save/department")
    public String save(@ModelAttribute("Department") Department department) {           
        dao.save(department);

        return "redirect:/department";
    }
    
    @GetMapping("/edit/department/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit-department");
        Department department = dao.get(id);
        mav.addObject("department", department);

        return mav;
    }
    @GetMapping(value = "/delete/department/{id}")
    public String delete(@PathVariable(name = "id") int id) {
        dao.delete(id);
        return "redirect:/department";
    }
    
    @PostMapping(value = "/update/department")
    public String update(@ModelAttribute("department") Department department) {
        dao.update(department);

        return "redirect:/department";
    }
}
