package raf.petrovicpleskonjic.rafairlinesticketservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import raf.petrovicpleskonjic.rafairlinesticketservice.models.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {}
