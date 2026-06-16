package vallegrande.luSanchezMiranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vallegrande.luSanchezMiranda.model.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByStatus(Boolean status);
    List<Customer> findByCustomerTypeIgnoreCase(String customerType);
    List<Customer> findByStatusAndCustomerTypeIgnoreCase(Boolean status, String customerType);

}