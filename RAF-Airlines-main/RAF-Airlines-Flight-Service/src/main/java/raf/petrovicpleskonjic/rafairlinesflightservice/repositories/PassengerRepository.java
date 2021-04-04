package raf.petrovicpleskonjic.rafairlinesflightservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import raf.petrovicpleskonjic.rafairlinesflightservice.models.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {}
