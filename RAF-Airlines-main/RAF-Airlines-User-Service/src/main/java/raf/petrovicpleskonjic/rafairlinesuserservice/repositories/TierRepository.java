package raf.petrovicpleskonjic.rafairlinesuserservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import raf.petrovicpleskonjic.rafairlinesuserservice.models.Tier;

@Repository
public interface TierRepository extends JpaRepository<Tier, Long> {

}
