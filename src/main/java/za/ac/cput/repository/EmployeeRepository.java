package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByContactEmail(String email);
}