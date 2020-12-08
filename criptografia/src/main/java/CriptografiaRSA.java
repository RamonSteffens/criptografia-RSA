import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CriptografiaRSA {
    public static final String ALGORITHM = "RSA";

    public static byte[] criptografa(String texto, PublicKey chave) {
        byte[] textoCriptografado = null;

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(1, chave);
            textoCriptografado = cipher.doFinal(texto.getBytes());
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return textoCriptografado;
    }

    public static String decriptografa(byte[] texto, PrivateKey chave) {
        byte[] textoDecriptografado = null;

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(2, chave);
            textoDecriptografado = cipher.doFinal(texto);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return new String(textoDecriptografado);
    }

}
