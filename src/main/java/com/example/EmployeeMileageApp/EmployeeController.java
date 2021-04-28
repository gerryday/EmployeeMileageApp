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
public class EmployeeController {

    @Autowired
    private EmployeeDAO employee_dao;
    
    @Autowired
    private DepartmentDAO department_dao;
    
    @GetMapping("/employee")
    public String viewEmployeePage(Model model) {
        Employee employee = new Employee();
        List<Department> listDepartments = department_dao.list();
        List<Employee> listEmployees = employee_dao.list();
        model.addAttribute("listEmployees", listEmployees);
        model.addAttribute("listDepartments", listDepartments);
        model.addAttribute("employee", employee);
        return "add-employee.html";
    }
    @PostMapping(value = "/save/employee")
    public String save(@ModelAttribute("Employee") Employee employee) {
        employee_dao.save(employee);
        return "redirect:/employee";
    }
    
    @GetMapping("/edit/employee/{employeenumber}")
    public ModelAndView showEditForm(@PathVariable(name = "employeenumber") String employeenumber) {
        ModelAndView mav = new ModelAndView("edit-employee");
        List<Department> listDepartments = department_dao.list();
        Employee employee = employee_dao.get(employeenumber);
        mav.addObject("listDepartments", listDepartments);
        mav.addObject("employee", employee);

        return mav;
    }
    
    @GetMapping(value = "/delete/employee/{employeenumber}")
    public String delete(@PathVariable(name = "employeenumber") String employeenumber) {
        employee_dao.delete(employeenumber);
        return "redirect:/employee";
    }
    
    @PostMapping(value = "/update/employee")
    public String update(@ModelAttribute("employee") Employee employee) {
        employee_dao.update(employee);

        return "redirect:/employee";
    }
}
