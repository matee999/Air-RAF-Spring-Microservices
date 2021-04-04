package raf.petrovicpleskonjic.rafairlinesuserservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import raf.petrovicpleskonjic.rafairlinesuserservice.models.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
	
	Administrator findByUsername(String username);
	
	boolean existsByUsername(String username);
}
