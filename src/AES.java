import java.io.*;

/**
 * Created by kyle on 10/24/14.
 */
public class AES {

    public static void main(String []args){

        try{
            if (args[0].equals("e")){
                encrypt(args);
            } else if(args[0].equals("d")){
                decrypt(args);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void encrypt(String []args) throws IOException{
            BufferedReader keyFile = new BufferedReader(new FileReader(args[1]));
            BufferedReader plainText = new BufferedReader(new FileReader(args[2]));
            BufferedWriter encryptedFile = new BufferedWriter(new FileWriter(args[2] + ".enc"));
            AESEncrypt encrypt = new AESEncrypt(keyFile, plainText, encryptedFile);
            encrypt.encrypt();
    }

    private static void decrypt(String []args) throws IOException{
        BufferedReader keyFile = new BufferedReader(new FileReader(args[1]));
        BufferedReader encryptedFile = new BufferedReader(new FileReader(args[2]));
        BufferedWriter plainText = new BufferedWriter(new FileWriter(args[2] + ".dec"));
        AESDecrypt decrypt = new AESDecrypt(keyFile, encryptedFile, plainText);
        decrypt.decrypt();
    }
}
