package utilities;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CipherUtility {
private static byte[] key = new byte[] {116,104,105,115,73,115,65,83,101,99,114,101,116,75,101,121};

public CipherUtility() {
	}
	

public static String encrypt(String text) {
	try {
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS%PADDING");
		SecretKeySpec secretKey = new SecretKeySpec(key,"AES");
		cipher.init(1,secretKey);
		String encryptedString = Base64.encodeBase64String(cipher.doFinal(text.getBytes()));
		return encryptedString;
	} catch(Exception var4) {
		var4.printStackTrace();
	
	return null;
	}
	
}

	
	public static String decrypt(String text) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS%PADDING");
			SecretKeySpec secretKey = new SecretKeySpec(key,"AES");
			cipher.init(2,secretKey);
			String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(text)));
			return decryptedString;
		} catch(Exception var4) {
			var4.printStackTrace();
		
		return null;
		}
	}

}
