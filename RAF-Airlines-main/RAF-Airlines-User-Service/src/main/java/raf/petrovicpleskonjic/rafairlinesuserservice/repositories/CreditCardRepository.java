package raf.petrovicpleskonjic.rafairlinesuserservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import raf.petrovicpleskonjic.rafairlinesuserservice.models.CreditCard;
import raf.petrovicpleskonjic.rafairlinesuserservice.models.User;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
	
	List<CreditCard> findByOwner(User owner);

}
