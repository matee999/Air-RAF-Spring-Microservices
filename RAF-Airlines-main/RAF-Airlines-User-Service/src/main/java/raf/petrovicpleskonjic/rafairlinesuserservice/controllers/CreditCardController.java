package raf.petrovicpleskonjic.rafairlinesuserservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import raf.petrovicpleskonjic.rafairlinesuserservice.UtilityMethods;
import raf.petrovicpleskonjic.rafairlinesuserservice.forms.requests.credit_card.NewCreditCardRequest;
import raf.petrovicpleskonjic.rafairlinesuserservice.forms.requests.credit_card.PassengerPurchaseRequest;
import raf.petrovicpleskonjic.rafairlinesuserservice.forms.responses.UserPurchaseInformationResponse;
import raf.petrovicpleskonjic.rafairlinesuserservice.models.CreditCard;
import raf.petrovicpleskonjic.rafairlinesuserservice.models.User;
import raf.petrovicpleskonjic.rafairlinesuserservice.repositories.CreditCardRepository;
import raf.petrovicpleskonjic.rafairlinesuserservice.repositories.UserRepository;

import static raf.petrovicpleskonjic.rafairlinesuserservice.security.SecurityConstants.HEADER_STRING;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/credit-card")
public class CreditCardController {

	private UserRepository userRepo;
	private CreditCardRepository creditCardRepo;

	@Autowired
	public CreditCardController(UserRepository userRepo, CreditCardRepository creditCardRepo) {
		this.userRepo = userRepo;
		this.creditCardRepo = creditCardRepo;
	}

	@PostMapping("/add")
	public ResponseEntity<CreditCard> addCreditCard(@RequestBody NewCreditCardRequest request,
			@RequestHeader(value = HEADER_STRING) String token) {

		try {
			User user = userRepo.findByEmail(UtilityMethods.getUsernameFromToken(token));

			if (user == null || request.getNumber() == null || request.getCcv() == null
					|| creditCardRepo.existsById(request.getNumber()))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			CreditCard creditCard = new CreditCard(request.getNumber(), request.getCcv(), user);
			creditCard = creditCardRepo.saveAndFlush(creditCard);

			return new ResponseEntity<>(creditCard, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/remove")
	public ResponseEntity<Void> removeCreditCard(@RequestParam String number,
			@RequestHeader(value = HEADER_STRING) String token) {

		try {
			User user = userRepo.findByEmail(UtilityMethods.getUsernameFromToken(token));

			if (user == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			Optional<CreditCard> card = creditCardRepo.findById(number);
			if (!card.isPresent() || card.get().getOwner().getUserId() != user.getUserId())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			creditCardRepo.delete(card.get());

			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/get-all")
	public ResponseEntity<List<CreditCard>> getCreditCards(@RequestHeader(value = HEADER_STRING) String token) {

		try {
			User user = userRepo.findByEmail(UtilityMethods.getUsernameFromToken(token));

			if (user == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			List<CreditCard> cards = creditCardRepo.findByOwner(user);

			return new ResponseEntity<>(cards, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/get-purchase-information")
	public ResponseEntity<UserPurchaseInformationResponse> getPurchaseInformation(Authentication authentication,
			@RequestBody PassengerPurchaseRequest request) {
		try {
			User user = userRepo.findByEmail(authentication.getName());
			if (user == null)
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			Optional<CreditCard> creditCard = creditCardRepo.findById(request.getCreditCardNumber());
			if (!creditCard.isPresent() || creditCard.get().getOwner().getUserId() != user.getUserId())
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

			return new ResponseEntity<>(new UserPurchaseInformationResponse(user.getUserId(),
					user.getTier().getSalePercentage(), creditCard.get().getNumber()), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

//	@PostMapping("/make-flight-payment")
//	public ResponseEntity<Boolean> makeFlightPayment(Authentication authentication, @RequestBody PaymentRequest request) {
//		try {
//			User user = userRepo.findByEmail(authentication.getName());
//			if (user == null)
//				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//			
//			Optional<CreditCard> creditCard = creditCardRepo.findById(request.getCreditCardNumber());
//			if (!creditCard.isPresent() || creditCard.get().getOwner().getUserId() != user.getUserId())
//				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//						
//			Float totalAmountToPay = request.getAmount() - (user.getTier().getSalePercentage() / 100 * request.getAmount());
//			System.out.println("Transaction: " + totalAmountToPay);
//			
//			user.setMiles(user.getMiles() + request.getMiles());
//			
//			List<Tier> tiers = tierRepo.findAll();
//			Tier newTier = null;
//			for (Tier tier : tiers) {
//				if (user.getMiles() > tier.getThreshold())
//					if (newTier == null || newTier.getThreshold() < tier.getThreshold())
//						newTier = tier;
//			}
//			user.setTier(newTier);
//			
//			userRepo.save(user);
//			
//			return new ResponseEntity<>(true, HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}

}
