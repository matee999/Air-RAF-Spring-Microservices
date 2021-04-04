package raf.petrovicpleskonjic.rafairlinesflightservice.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import raf.petrovicpleskonjic.rafairlinesflightservice.forms.requests.NewAirplaneRequest;
import raf.petrovicpleskonjic.rafairlinesflightservice.models.Airplane;
import raf.petrovicpleskonjic.rafairlinesflightservice.repositories.AirplaneRepository;
import raf.petrovicpleskonjic.rafairlinesflightservice.utils.UtilityMethods;

@RestController
@RequestMapping("/airplane")
public class AirplaneController {

	private AirplaneRepository airplaneRepo;

	@Autowired
	public AirplaneController(AirplaneRepository airplaneRepo) {
		this.airplaneRepo = airplaneRepo;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Airplane>> allAirplanes(@RequestHeader(value = "Authorization") String token) {
		try {
			List<Airplane> airplanes = airplaneRepo.findAll();
			
			return new ResponseEntity<>(airplanes, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Airplane> addAirplane(@RequestBody NewAirplaneRequest request,
			@RequestHeader(value = "Authorization") String token) {
		try {
			ResponseEntity<Boolean> isAdmin = UtilityMethods.sendGet(Boolean.class,
					UtilityMethods.USER_SERVICE_URL + "admin-verification", token);

			if (!isAdmin.getBody())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			Airplane airplane = new Airplane(request.getName(), request.getCapacity());
			airplaneRepo.saveAndFlush(airplane);

			return new ResponseEntity<>(airplane, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/remove")
	public ResponseEntity<Void> removeAirplane(@RequestParam Long airplaneId,
			@RequestHeader(value = "Authorization") String token) {
		try {
			ResponseEntity<Boolean> isAdmin = UtilityMethods.sendGet(Boolean.class,
					UtilityMethods.USER_SERVICE_URL + "admin-verification", token);

			if (!isAdmin.getBody())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
			Optional<Airplane> airplane = airplaneRepo.findById(airplaneId);
			if (!airplane.isPresent() || !airplane.get().getFlights().isEmpty())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			airplaneRepo.deleteById(airplaneId);

			return new ResponseEntity<>(HttpStatus.ACCEPTED);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
