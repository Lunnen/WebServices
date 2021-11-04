package assign3be.shared;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@Component
public class Util {

    private static final String ALGO = "AES";

    private final static String mySalt = "EcUtbildningIsGo"; // must be length of 16,24,32 ....

    private static final byte[] keyValue = mySalt.getBytes(StandardCharsets.UTF_8);

    public static String encrypt(String pwd) {
        String encodedPwd = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(pwd.getBytes());
            encodedPwd = Base64.getEncoder().encodeToString(encVal);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return encodedPwd;

    }

    public static String decrypt(String encryptedData) {
        String decodedPWD = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            decodedPWD = new String(decValue);

        } catch (Exception e) {

        }
        return decodedPWD;
    }

    private static Key generateKey() {
        return new SecretKeySpec(keyValue, ALGO);
    }

}
