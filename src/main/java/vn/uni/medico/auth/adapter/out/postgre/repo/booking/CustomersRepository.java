package vn.uni.medico.auth.adapter.out.postgre.repo.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.uni.medico.auth.domain.entity.booking.Customers;


public interface CustomersRepository extends JpaRepository<Customers, Long>
        , JpaSpecificationExecutor<Customers> {

    Customers findFirstByCustomerCode(String username);
}
