package com.kissco.store.repo;

import java.util.List;
//CrudRepo >>> PagingAndSortingRepo >>> JpaRepo 즉 CrudRepo만써도된다.
import org.springframework.data.repository.CrudRepository;

import com.kissco.store.model.Customer;

//해당 Customer와 Long(객체 기본키 ID)값을 인자로 받는다.
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	List<Customer> findByFirstName(String fisrtName);
	
	List<Customer> findByLastName(String lastName);
	
	List<Customer> findByAge(int age);
}
