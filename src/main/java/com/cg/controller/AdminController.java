package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bean.CreditCard;
import com.cg.bean.Customer;
import com.cg.service.IAdminService;
import com.cg.service.ICreditCardService;
import com.cg.service.ICustomerService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ICustomerService custService;

	@Autowired
	private IAdminService adminService;

	@Autowired
	private ICreditCardService cardService;

	// Admin can add Customer
	@PostMapping("/addCustomer")
	public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {

		return custService.addCustomer(customer);
	}

	// Admin can remove a customer
	@DeleteMapping("/removeCustomer/{custId}")
	public String removeCustomer(@PathVariable("custId") long custId) {

		return custService.removeCustomer(custId);
	}

	// Admin can also view customer information
	@GetMapping("/getCustomer/{custId}")
	public Customer getCustomer(@PathVariable("custId") long custId) {

		return custService.getCustomer(custId);
	}

	@GetMapping("/getAllCustomers")
	public List<Customer> getAllCustomers() {

		return custService.getAllCustomers();
	}

	// Admin can update customer details
	@PatchMapping("/updateCustomer/{custId}")
	public String updateCustomer(@PathVariable("custId") long custId, @RequestBody Customer customer) {

		return custService.updateCustomer(custId, customer);
	}

	// Create Statement for user
	@PatchMapping("/addStatement/{custId}/{cardNumber}")
	public ResponseEntity<String> addStatement(@PathVariable("custId") long id,
			@PathVariable("cardNumber") String cardNumber) {

		return adminService.addStatement(id, cardNumber);
	}

	// Add Credit card for a customer
	@PatchMapping("/addCreditCard/{custId}")
	public ResponseEntity<String> addCreditCard(@PathVariable("custId") long id, @RequestBody CreditCard newCreditCard) {

		return cardService.addCreditCard(id, newCreditCard);

	}

}
