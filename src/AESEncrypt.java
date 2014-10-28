import javax.imageio.event.IIOWriteProgressListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by kyle on 10/24/14.
 */
public class AESEncrypt {

    private BufferedReader key;
    private BufferedReader plainTextFile;
    private BufferedWriter encryptedFile;

    private int[][] sBox = {

            { 0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76  } ,
            { 0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0  } ,
            { 0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15  } ,
            { 0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75  } ,
            { 0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84  } ,
            { 0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF  } ,
            { 0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8  } ,
            { 0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2  } ,
            { 0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73  } ,
            { 0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB  } ,
            { 0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79  } ,
            { 0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08  } ,
            { 0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A  } ,
            { 0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E  } ,
            { 0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF  } ,
            { 0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16  }
    };

	/*
	 * Inverse sBox. Used for Decryption
	 */

    private int[][]invSbox = {


            { 0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB } ,
            { 0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB } ,
            { 0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E } ,
            { 0x08, 0x2E, 0xA1, 0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25 } ,
            { 0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92 } ,
            { 0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D, 0x84 } ,
            { 0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06 } ,
            { 0xD0, 0x2C, 0x1E, 0x8F, 0xCA, 0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B } ,
            { 0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6, 0x73 } ,
            { 0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E } ,
            { 0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B } ,
            { 0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4 } ,
            { 0x1F, 0xDD, 0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F } ,
            { 0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF } ,
            { 0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61 } ,
            { 0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D }
    };

    private int[][] Rcon = {

        {0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80,0x1B,0x36},
        {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00},
        {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00},
        {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00},
    };



    public AESEncrypt(BufferedReader key, BufferedReader inputFile, BufferedWriter outputFile){
        this.key = key;
        plainTextFile = inputFile;
        encryptedFile = outputFile;
    }

    public void encrypt() throws IOException{
        int[][] state = createState();
        int[][] keyExpansion = keyExpansion();
        System.out.println("STATE");
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                System.out.print(Integer.toHexString(state[i][j]) + " ");
            }
            System.out.println();
        }

        System.out.println("KEY EXPANSION");
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                System.out.print(Integer.toHexString(keyExpansion[i][j]) + " ");

            }
            System.out.println();
        }

    }

    private int[][] keyExpansion() throws IOException{
        int[][] expandedkey = new int[4][44];
        createGrid(getLines(key), expandedkey);
        
        // for (int column = 4; column < 44; column++){
        //     int[] temp = new int[4];
        //     if (column % 4 == 0){
        //         temp = EK(expandedkey, column-4);
        //         for(int i =0; i < 4; i++){
        //             // temp[i] = subWord(rotWord(EK(expandedkey, column-4)))[0] ^ Rcon[(column/4) - 1];
        //             System.out.print(" " + temp[i]);

        //         }
        //         temp = xor(temp, EK(expandedkey, column-4));

        //     }else {
        //         temp = xor(EK(expandedkey, column - 1), EK(expandedkey, column - 4));
        //     }
        //     
        // }
        int[] temp = new int[4];
        int[] xor_1 = new int[4];
        for(int column = 4; column < 8; column++){
            //get W(column-1)
            System.out.println("ON column: "+ column);
            temp = EK(expandedkey, column-1);
            if(column % 4 == 0){
                //rotate and substitute if first column of block
                System.out.println(" 1st block ");
                temp = rotWord(temp);
                temp = subWord(temp);
                System.out.println("Temp @ column: "+column +":  "+Integer.toHexString(temp[0]) + " " + Integer.toHexString(temp[1]) + " " + Integer.toHexString(temp[2]) + " " + Integer.toHexString(temp[3]));
            }
            
            
            // xor_1 : W(column - 4) ^ W(column)
            temp = xor(EK(expandedkey, column-4), temp);

            if(column % 4 == 0){
                int[] rcon = Rcon[(column/4) - 1];
                temp = xor(temp, rcon);
            }

            expandedkey[0][column] = temp[0];
            expandedkey[1][column] = temp[1];
            expandedkey[2][column] = temp[2];
            expandedkey[3][column] = temp[3];
            System.out.println("Temp @ column: "+column +":  "+Integer.toHexString(temp[0]) + " " + Integer.toHexString(temp[1]) + " " + Integer.toHexString(temp[2]) + " " + Integer.toHexString(temp[3]));
        }


        return expandedkey;
    }

    private int[] subWord(int[] eKey){
        int[] temp = new int[4];
        //hex to subox example: ca -> c:rows, a:columns
        System.out.println("SUB");
        for (int i = 0; i < 4; i++){
            String hex = Integer.toHexString(eKey[i]);
            // System.out.println(hex);
            if (hex.length() == 1){
                hex = "0" + hex;
            }
            String row = hex.substring(0, 1);
            String column = hex.substring(1, 2);
            // System.out.println("row: " +row + " column: "+column);

            temp[i] = sBox[Integer.parseInt(row, 16)][Integer.parseInt(column, 16)];
            // System.out.println("Sbox: "+ Integer.toHexString(temp[i]));
        }
        return temp;
    }

    private int[] xor(int[] one, int[] two){
        int[] temp = new int[one.length];
        for(int i = 0; i < one.length; i++){
            temp[i] = one[i] ^ two[i];
        }
        return temp;
    }

    private int[] rotWord(int[] word){     
        System.out.println("ROT");
        int[] temp = {word[1], word[2], word[3], word[0]};
        return temp;
    }

    private int[] EK(int[][] expandedKey, int column){
        System.out.print(Integer.toHexString(expandedKey[0][column]) + " ");
        System.out.print(Integer.toHexString(expandedKey[1][column]) + " ");
        System.out.print(Integer.toHexString(expandedKey[2][column]) + " ");
        System.out.print(Integer.toHexString(expandedKey[3][column]) + " \n");
        int[] temp = {expandedKey[0][column], expandedKey[1][column], expandedKey[2][column], expandedKey[3][column]};
        return temp;
    }

    private int[][] createState() throws IOException{
        ArrayList<String> lines = getLines(plainTextFile);
        int[][] state = new int[4][lines.size() * 4];
        return createGrid(lines, state);
    }

    private int[][] createGrid(ArrayList<String> lines, int[][] grid){
        int block = 0;
        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            int lineIndex = 0;
            for (int column = block * 4; column < block * 4 + 4; column++){
                for ( int row = 0; row < 4; row++){
                    grid[row][column] = (grid[row][column] << 4) +  hexVal(line.charAt(lineIndex++));
                    grid[row][column] = (grid[row][column] << 4) +  hexVal(line.charAt(lineIndex++));
                }
            }
            block++;
        }
        return grid;
    }

    private ArrayList<String> getLines(BufferedReader file)throws IOException{
        String line;
        ArrayList<String> lines = new ArrayList<String>();
        while ((line = file.readLine()) != null){
            if (validLine(line)){
                lines.add(getLine(line));
            }
        }
        return lines;
    }

    private String getLine(String line){
        return line;
    }

    private boolean validLine(String line){
        return true;
    }

    // Returns the integer value of a given HEX character.
    public static int hexVal(char c) {
        if (c >= '0' && c <= '9')
            return (int)(c-'0');
        else
            return (int)(c-'A'+10);
    }

    // Converts an integer(0-15) to the appropriate HEX character.
    public static char convToHex(int d) {
        if (d < 10)
            return (char)(d+'0');
        else
            return (char)(d-10+'A');
    }

    // Unsigns a byte that is stored in an integer.
    private int unsign(int c) {
        if (c >=0)
            return c;
        else
            return 256+c;
    }

}
