package raf.petrovicpleskonjic.rafairlinesuserservice.security;

public final class SecurityConstants {

	public static final String SECRET = "raf_airlines_secret_key";

	public static final long TOKEN_EXPIRATION_TIME = 86400000;
	public static final String TOKEN_PREFIX = "Basic ";
	public static final String HEADER_STRING = "Authorization";


	public static final String LOGIN_PATH = "/login";
	public static final String REGISTRATION_PATH = "/register";

}
