package com.company.system_zarzadzania_dla_agencji_pracy.controller;

import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Administrator;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employee;
import com.company.system_zarzadzania_dla_agencji_pracy.model.entity.Employer;
import com.company.system_zarzadzania_dla_agencji_pracy.repository.AdministratorRepository;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployeeService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.EmployerService;
import com.company.system_zarzadzania_dla_agencji_pracy.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
//@RequestMapping("/register")
public class RegisterController {

    private UserDetailServiceImpl userDetailService;

    private EmployeeService employeeService;
    private EmployerService employerService;
    private AdministratorRepository administratorRepository;

    @Autowired
    public RegisterController(UserDetailServiceImpl userDetailService, EmployeeService employeeService, EmployerService employerService, AdministratorRepository administratorRepository) {
        this.userDetailService = userDetailService;
        this.employeeService = employeeService;
        this.employerService = employerService;
        this.administratorRepository = administratorRepository;
    }


    @GetMapping("/register")
    public String getRegisterForm(){
        return "register";
    }

    @GetMapping("/register/pracownik")
    public String getEmployeeForm(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee",employee);
        return "register-ea";
    }

    @PostMapping("/register/pracownik")
    public String newEmployee(@ModelAttribute("employee") Employee employee){
        Optional<Administrator> administratorOpt = administratorRepository.findAdministratorByMail("michal@admin.com");
        Administrator administrator = administratorOpt.get();
        employee.setAdministrator(administrator);
        employee.setStudentStatus(true); //ręcznie na daną chwilę, bo nie wiem jak to zrobić w froncie
        employeeService.addEmployee(employee);
        return "redirect:/login";
    }

    //odKrystiana
//    @PostMapping("/pracownik")
//    public String newEmployee(@RequestBody Employee employee){
//        employeeService.addEmployee(employee);
//        return "login-form";
//    }


    @GetMapping("/register/pracodawca")
    public String getEmployerForm(Model model){
        Employer employer = new Employer();
        model.addAttribute("employer",employer);
        return "register-employer";
    }

    @PostMapping("/register/pracodawca")
    public String newEmployer(@ModelAttribute("employer") Employer employer){
        Optional<Administrator> administratorOpt = administratorRepository.findAdministratorByMail("michal@admin.com"); //znalezienie admina o takim username(mailu)
        Administrator administrator = administratorOpt.get();
        employer.setAdministrator(administrator);
        employer.setCurrentCosts(0.0);
        employerService.addEmployer(employer);
        return "redirect:/login";
    }



//    @GetMapping("/register/employee")
//    public
//
//    @PostMapping("/register/create")



//    @GetMapping("/register")
//    public String getRegisterPage(){
//        User user = new User();
//        user.setMail("dom");
//        user.setPassword("dom");
//        user.setRole(Role.PRACOWNIK);
//        userDetailService.addUser(user);
//        return "register";
//    }

//    @PostMapping("/register")
//    public String createUser(@RequestBody ){
//
//        return null;
//    }
}
