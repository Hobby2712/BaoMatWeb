package Util;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES {
    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        // Khởi tạo Cipher với thuật toán mã hóa và chế độ ECB (Electronic Codebook)
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // Khởi tạo SecretKeySpec từ SecretKey
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), SECRET_KEY_ALGORITHM);

        // Đặt Cipher vào chế độ mã hóa
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // Mã hóa dữ liệu bằng secret key
       byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // Chuyển đổi kết quả mã hóa thành chuỗi Base64
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

        // Trả về kết quả mã hóa
        return encryptedText;
    }

    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        // Khởi tạo Cipher với thuật toán mã hóa và chế độ ECB (Electronic Codebook)
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // Khởi tạo SecretKeySpec từ SecretKey
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), SECRET_KEY_ALGORITHM);

        // Đặt Cipher vào chế độ giải mã
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        // Giải mã dữ liệu đã được mã hóa bằng secret key
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

        // Chuyển đổi kết quả giải mã thành chuỗi ký tự
        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

        // Trả về kết quả giải mã
        return decryptedText;
    }
}