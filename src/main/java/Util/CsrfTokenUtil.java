package Util;

import java.security.SecureRandom;
import java.util.Base64;

public class CsrfTokenUtil {
	public static String generateCsrfToken() {
	    byte[] bytes = new byte[16];
	    SecureRandom random = new SecureRandom();
	    random.nextBytes(bytes);
	    return Base64.getEncoder().encodeToString(bytes);
	  }
}


