package Util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncoder {
	public static String encrypt(String password) throws Exception{
		
		SecretKeySpec keySpec = new SecretKeySpec(Constant.SECRET_KEY.getBytes(), "AES");
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
	    byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
	    return Base64.getEncoder().encodeToString(encrypted);
	}
	public static String decrypt(String encryptedText) throws Exception {
		SecretKeySpec keySpec = new SecretKeySpec(Constant.SECRET_KEY.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
		return new String(decrypted, StandardCharsets.UTF_8);
	}
}
