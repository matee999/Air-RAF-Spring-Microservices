package raf.petrovicpleskonjic.rafairlinesflightservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import raf.petrovicpleskonjic.rafairlinesflightservice.models.Airplane;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {}
