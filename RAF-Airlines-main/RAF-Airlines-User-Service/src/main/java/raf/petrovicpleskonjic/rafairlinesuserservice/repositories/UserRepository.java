package raf.petrovicpleskonjic.rafairlinesuserservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import raf.petrovicpleskonjic.rafairlinesuserservice.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);

	boolean existsByEmail(String email);
}
