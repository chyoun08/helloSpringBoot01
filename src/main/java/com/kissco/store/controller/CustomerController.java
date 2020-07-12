package com.kissco.store.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kissco.store.model.Customer;
import com.kissco.store.repo.CustomerRepository;

@RestController
@RequestMapping("/api")
public class CustomerController {
	
	static Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CustomerRepository repository;

	// 안붙여도 josn형태로 가는것 같다 이 말고 findBy~~도 그냥 json으로 왔다.
	@GetMapping(value="/customers", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> getAllCustomers() {
		logger.debug("Calling getALLCustomers호출!");

		List<Customer> list = new ArrayList<>();
		Iterable<Customer> customers = repository.findAll();
		//crudRepository안에 findAll이란 method가있다 이를 활용해서 이처럼 사용가능하다.
		customers.forEach(list::add);
		try {
			
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(list, HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println("INTERNAL서버에러!!!----");
			System.out.println(e.getLocalizedMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) {
		logger.debug("Calling getCustomerById");
		Optional<Customer> customerData = repository.findById(id);

		if (customerData.isPresent()) {
			return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/customers")
	public ResponseEntity<Customer> postCustomer(@RequestBody Customer customer) {
		logger.debug("Calling postCustomer");
		try {
			Customer _customer = repository.save(new Customer(customer.getFirstName(), customer.getLastName(), customer.getAge()));
			return new ResponseEntity<>(_customer, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
		logger.debug("Calling deleteCustomer");
		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/customers")
	public ResponseEntity<HttpStatus> deleteAllCustomers() {
		logger.debug("Calling deleteAllCustomers");
		try {
			repository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

	@GetMapping(value = "customers/age/{age}")
	public ResponseEntity<List<Customer>> findByAge(@PathVariable int age) {
		logger.debug("Calling findByAge");
		try {
			List<Customer> customers = repository.findByAge(age);

			if (customers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping("/customers/firstname/{firstname}")
	public ResponseEntity<List<Customer>> findByFirstName(@PathVariable String firstname){
		logger.debug("Calling findByFirstName");
		try {
			List<Customer> customers = repository.findByFirstName(firstname);
			if(customers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customers, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	
	@GetMapping("/customers/lastname/{lastname}")
	public ResponseEntity<List<Customer>> findByLastName(@PathVariable String lastname){
		logger.debug("Calling findByLastName");
		try {
			List<Customer> customers = repository.findByLastName(lastname);
			if(customers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(customers,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
		logger.debug("Calling updateCustomer");
		Optional<Customer> customerData = repository.findById(id);

		if (customerData.isPresent()) {
			Customer _customer = customerData.get();
			_customer.setFirstName(customer.getFirstName());
			_customer.setLastName(customer.getLastName());
			_customer.setAge(customer.getAge());
			return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
