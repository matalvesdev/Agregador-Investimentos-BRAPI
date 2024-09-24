package agregadorinvestimento.AgregadorDeInvestimentos.repository;

import agregadorinvestimento.AgregadorDeInvestimentos.entity.BillingAddress;
import agregadorinvestimento.AgregadorDeInvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
