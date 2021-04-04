package raf.petrovicpleskonjic.rafairlinesuserservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import static raf.petrovicpleskonjic.rafairlinesuserservice.security.SecurityConstants.SECRET;
import static raf.petrovicpleskonjic.rafairlinesuserservice.security.SecurityConstants.TOKEN_PREFIX;

public class UtilityMethods {

	public static String getUsernameFromToken(String token) {
		return JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
				.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
	}
}
