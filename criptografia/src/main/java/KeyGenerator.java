import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGenerator {
    public static final String ALGORITHM = "RSA";
    public static final String DIRETORIO = "/chaves/";
    public static final String PATH_CHAVE_PRIVADA = "private.key";
    public static final String PATH_CHAVE_PUBLICA = "public.key";

    public static KeyPair criaArquivosDeChaves() {
        try {
            KeyPairGenerator geradorDeChave = KeyPairGenerator.getInstance(ALGORITHM);
            geradorDeChave.initialize(1024);
            KeyPair chave = geradorDeChave.generateKeyPair();

            File arquivoChavePrivada = new File(DIRETORIO + PATH_CHAVE_PRIVADA);
            File arquivoChavePublica = new File(DIRETORIO + PATH_CHAVE_PUBLICA);
            if (arquivoChavePrivada.getParentFile() != null) {
                arquivoChavePrivada.getParentFile().mkdirs();
            }

            arquivoChavePrivada.createNewFile();
            if (arquivoChavePublica.getParentFile() != null) {
                arquivoChavePublica.getParentFile().mkdirs();
            }

            arquivoChavePublica.createNewFile();
            //Escreve chave em um arquivo
            ObjectOutputStream chavePublicaOS = new ObjectOutputStream(new FileOutputStream(arquivoChavePublica));
            chavePublicaOS.writeObject(chave.getPublic());
            chavePublicaOS.close();
            //Escreve chave em um arquivo
            ObjectOutputStream chavePrivadaOS = new ObjectOutputStream(new FileOutputStream(arquivoChavePrivada));
            chavePrivadaOS.writeObject(chave.getPrivate());
            chavePrivadaOS.close();

            System.out.println("Path chave publica: " + PATH_CHAVE_PUBLICA);
            System.out.println("Path chave privada: " + PATH_CHAVE_PRIVADA);

            return chave;
        } catch (Exception var6) {
            var6.printStackTrace();
        }
        return null;
    }

}
