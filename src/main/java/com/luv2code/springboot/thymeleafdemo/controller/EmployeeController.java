package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeRepository employeeRepository;

	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping("/list")
	public String listEmployees(Model theModel) {
		List<Employee> theEmployees = employeeRepository.findAllByOrderByFirstNameAsc();
		theModel.addAttribute("employees", theEmployees);
		return "employees/list-employees";
	}

	@GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Employee theEmployee = new Employee();
		theModel.addAttribute("employee",theEmployee);
        return "employees/employee-form";
    }

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
		Optional<Employee> theEmployee = employeeRepository.findById(theId);
		if (theEmployee.isPresent()) {
			theModel.addAttribute("employee", theEmployee.get());
		} else {
			return "redirect:/employees/list"; // or some error page
		}
		return "employees/employee-form";
	}

	@PostMapping("/save")
	public String saveEmployee (@ModelAttribute("employee") Employee theEmployee) {
		employeeRepository.save(theEmployee);
		return "redirect:/employees/list";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		employeeRepository.deleteById(theId);
		return "redirect:/employees/list";
	}
}