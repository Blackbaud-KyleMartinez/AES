import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Created by kyle on 10/24/14.
 */
public class AESDecrypt {

    private BufferedReader key;
    private BufferedReader encryptedFile;
    private BufferedWriter decryptedFile;

    public AESDecrypt(BufferedReader key, BufferedReader inputFile, BufferedWriter outputFile){
        this.key = key;
        encryptedFile = inputFile;
        decryptedFile = outputFile;
    }

    public void decrypt(){

    }
}
