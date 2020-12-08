import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

public class Main {
    public static final String DIRETORIO = "/chaves/";
    public static final String PATH_CHAVE_PRIVADA = "private.key";
    public static final String PATH_CHAVE_PUBLICA = "public.key";
    public static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            KeyPair key;
            System.out.println("Possui o conjunto de chaves?");
            System.out.println("1.Sim -- 2.Não");
            if (teclado.nextLine().equals("2")) {
                key = geraChave();
            } else {
                if (possuiChave(PATH_CHAVE_PRIVADA, PATH_CHAVE_PUBLICA)) {
                    key = obtemChave();
                } else {
                    key = geraChave();
                }
            }

            System.out.println("Digite o texto que deseja criptografar");
            String msgOriginal = teclado.nextLine();

            String textoPuro;
            byte[] textoCriptografado = null;
            boolean publicPathCorreto = false;

            //Laço de repetição para o usuario identificar a path da chave publica correto
            do{
                System.out.println("Digite o path da chave publica: ");
                if (PATH_CHAVE_PUBLICA.equals(teclado.nextLine())){
                    publicPathCorreto = true;
                    textoCriptografado = CriptografiaRSA.criptografa(msgOriginal, key.getPublic());

                    System.out.println("Mensagem Criptografada: " + textoCriptografado);
                }
            }while (!publicPathCorreto);

            System.out.println("Deseja descriptografar a mensagem?  1.Sim -- 2.Não");
            if (teclado.nextLine().equals("1")) {
                boolean privatePathCorreto = false;
                int count = 0;
                //Laço de repetição para o usuario identificar a path da chave privada correto
                do{
                    System.out.println("Digite o path da chave privada: ");
                    if (PATH_CHAVE_PRIVADA.equals(teclado.nextLine())){
                        privatePathCorreto = true;
                        textoPuro = CriptografiaRSA.decriptografa(textoCriptografado, key.getPrivate());
                        gerarMensagemDeSucesso(msgOriginal, textoCriptografado, textoPuro);
                    }else{
                        count ++;
                    }
                    if (count == 3){
                        System.out.println("3 tentativas invalidas de chave privada, operação cancelada.");
                        break;
                    }
                }while (!privatePathCorreto);
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }

    private static KeyPair obtemChave() throws IOException, ClassNotFoundException {
        ObjectInputStream chavePublica = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
        ObjectInputStream chavePrivada = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
        System.out.println("Path chave publica: " + PATH_CHAVE_PUBLICA);
        System.out.println("Path chave privada: " + PATH_CHAVE_PRIVADA);
        return new KeyPair((PublicKey) chavePublica.readObject(), (PrivateKey) chavePrivada.readObject());
    }

    private static KeyPair geraChave() {
        System.out.println("Gerando chaves");
        KeyPair chave = KeyGenerator.criaArquivosDeChaves();
        System.out.println("Chave privada: " + chave.getPrivate());
        System.out.println("Chave publica: " + chave.getPublic());
        return chave;
    }

    private static boolean possuiChave(String pathChavePrivada, String pathChavePublica) {
        File chavePrivada = new File(DIRETORIO + pathChavePrivada);
        File chavePublica = new File(DIRETORIO + pathChavePublica);
        return chavePrivada.exists() && chavePublica.exists();
    }

    private static void gerarMensagemDeSucesso(String msgOriginal, byte[] textoCriptografado, String textoPuro) {
        System.out.println("--------------------------------------------");
        System.out.println("Mensagem Original: " + msgOriginal);
        System.out.println("--------------------------------------------");
        System.out.println("Mensagem Criptografada: " + textoCriptografado);
        System.out.println("--------------------------------------------");
        System.out.println("Mensagem Decriptografada: " + textoPuro);
    }
}
