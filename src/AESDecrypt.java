import javax.imageio.event.IIOWriteProgressListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.System;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Timer;

/**
 * Created by kyle on 10/24/14.
 */
public class AESDecrypt {

    private String key;
    private BufferedReader encryptedFile;
    private BufferedWriter decryptedFile;


    final static int[][] sBox = {

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

    final static int[][]invSbox = {


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

        final static int[] LogTable = {
            0,   0,  25,   1,  50,   2,  26, 198,  75, 199,  27, 104,  51, 238, 223,   3,
            100,   4, 224,  14,  52, 141, 129, 239,  76, 113,   8, 200, 248, 105,  28, 193,
            125, 194,  29, 181, 249, 185,  39, 106,  77, 228, 166, 114, 154, 201,   9, 120,
            101,  47, 138,   5,  33,  15, 225,  36,  18, 240, 130,  69,  53, 147, 218, 142,
            150, 143, 219, 189,  54, 208, 206, 148,  19,  92, 210, 241,  64,  70, 131,  56,
            102, 221, 253,  48, 191,   6, 139,  98, 179,  37, 226, 152,  34, 136, 145,  16,
            126, 110,  72, 195, 163, 182,  30,  66,  58, 107,  40,  84, 250, 133,  61, 186,
            43, 121,  10,  21, 155, 159,  94, 202,  78, 212, 172, 229, 243, 115, 167,  87,
            175,  88, 168,  80, 244, 234, 214, 116,  79, 174, 233, 213, 231, 230, 173, 232,
            44, 215, 117, 122, 235,  22,  11, 245,  89, 203,  95, 176, 156, 169,  81, 160,
            127,  12, 246, 111,  23, 196,  73, 236, 216,  67,  31,  45, 164, 118, 123, 183,
            204, 187,  62,  90, 251,  96, 177, 134,  59,  82, 161, 108, 170,  85,  41, 157,
            151, 178, 135, 144,  97, 190, 220, 252, 188, 149, 207, 205,  55,  63,  91, 209,
            83,  57, 132,  60,  65, 162, 109,  71,  20,  42, 158,  93,  86, 242, 211, 171,
            68,  17, 146, 217,  35,  32,  46, 137, 180, 124, 184,  38, 119, 153, 227, 165,
            103,  74, 237, 222, 197,  49, 254,  24,  13,  99, 140, 128, 192, 247, 112,   7};

    final static int[] AlogTable = {
            1,   3,   5,  15,  17,  51,  85, 255,  26,  46, 114, 150, 161, 248,  19,  53,
            95, 225,  56,  72, 216, 115, 149, 164, 247,   2,   6,  10,  30,  34, 102, 170,
            229,  52,  92, 228,  55,  89, 235,  38, 106, 190, 217, 112, 144, 171, 230,  49,
            83, 245,   4,  12,  20,  60,  68, 204,  79, 209, 104, 184, 211, 110, 178, 205,
            76, 212, 103, 169, 224,  59,  77, 215,  98, 166, 241,   8,  24,  40, 120, 136,
            131, 158, 185, 208, 107, 189, 220, 127, 129, 152, 179, 206,  73, 219, 118, 154,
            181, 196,  87, 249,  16,  48,  80, 240,  11,  29,  39, 105, 187, 214,  97, 163,
            254,  25,  43, 125, 135, 146, 173, 236,  47, 113, 147, 174, 233,  32,  96, 160,
            251,  22,  58,  78, 210, 109, 183, 194,  93, 231,  50,  86, 250,  21,  63,  65,
            195,  94, 226,  61,  71, 201,  64, 192,  91, 237,  44, 116, 156, 191, 218, 117,
            159, 186, 213, 100, 172, 239,  42, 126, 130, 157, 188, 223, 122, 142, 137, 128,
            155, 182, 193,  88, 232,  35, 101, 175, 234,  37, 111, 177, 200,  67, 197,  84,
            252,  31,  33,  99, 165, 244,   7,   9,  27,  45, 119, 153, 176, 203,  70, 202,
            69, 207,  74, 222, 121, 139, 134, 145, 168, 227,  62,  66, 198,  81, 243,  14,
            18,  54,  90, 238,  41, 123, 141, 140, 143, 138, 133, 148, 167, 242,  13,  23,
            57,  75, 221, 124, 132, 151, 162, 253,  28,  36, 108, 180, 199,  82, 246,   1};


    final static int[][] Rcon = {

            {0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80,0x1B,0x36},
            {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00},
            {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00},
            {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00},
    };

    private int[][] state;
    private int[][] expandedKey;

    public AESDecrypt(BufferedReader key, BufferedReader inputFile, BufferedWriter outputFile){
        try{
            this.key = getLine(key.readLine());
        } catch (IOException e){
            e.printStackTrace();
        }
        encryptedFile = inputFile;
        decryptedFile = outputFile;
    }

    public void decrypt() throws IOException{

        String line;
        int count = 0;
        long startTime = System.currentTimeMillis();
        while((line = encryptedFile.readLine()) != null) {
            count++;
            state = createGrid(getLine(line), new int[4][4]);
            printState();
            expandedKey = keyExpansion();
            System.out.println("KEY EXPANSION");
            for(int i = 0; i < 4; i++)
            {
                for(int j = 0; j < 44; j++)
                {
                    System.out.print(Integer.toHexString(expandedKey[i][j]) + " ");

                }
                System.out.println();
            }

            int round = 10;
            addRoundKey(round, state);
            System.out.println("After addRoundKey(" + round+ ")");
            System.out.println(getStringFromState());
            for (round = 9; round >= 1; round--) {
                invShiftRows(state);
                System.out.println("After invShiftRows");
                System.out.println(getStringFromState());
                invsubBytes(state);
                System.out.println("After insubBytes");
                System.out.println(getStringFromState());
                addRoundKey(round, state);
                System.out.println("After addRoundKey(" + round+ ")");
                System.out.println(getStringFromState());
                invMixColumn(state);
                System.out.println("After inMixcolumn");
                System.out.println(getStringFromState());
            }

            invShiftRows(state);
            System.out.println("After invShiftRows");
            System.out.println(getStringFromState());
            invsubBytes(state);
            System.out.println("After insubBytes");
            System.out.println(getStringFromState());
            addRoundKey(round, state);
            System.out.println("After addRoundKey(" + round+ ")");
            System.out.println(getStringFromState());

            String decryptedLine = getStringFromState();
            System.out.println(decryptedLine);
            decryptedFile.write(decryptedLine + "\n");
        }
        decryptedFile.close();
        long endTime = System.currentTimeMillis();
        System.out.println("Number of lines of plaintext: " + count);
        System.out.println("Number of Bytes: " + count * 8);
        System.out.println("Time Elapsed: " + (endTime - startTime) * 0.001);
        System.out.println("Bytes per Second: " + (count * 8) / ((endTime - startTime) * 0.001));
    }

    private int[][] createGrid(String line, int[][] grid){
        int lineIndex = 0;
        for (int column = 0; column < 4; column++){
            for ( int row = 0; row < 4; row++){
                grid[row][column] = Integer.parseInt(line.substring(lineIndex++, lineIndex), 16);
                grid[row][column] = (grid[row][column] << 4) + Integer.parseInt(line.substring(lineIndex++, lineIndex), 16);
            }
        }

        return grid;
    }

    private String getStringFromState(){
        String x = "";
        for(int column = 0; column < 4; column++){
            for(int row = 0; row < 4; row++){
                String temp = Integer.toHexString(state[row][column]);
                if (temp.length() == 1){
                    temp = "0" + temp;
                }
                x += temp;
            }
        }
        return x;
    }

    private void invShiftRows(int[][] state){
        /*  shift block by rows
        *   a b c d ->(0 shifts) a b c d
        *   e f g h ->(1 shifts) h e f g
        *   i j k l ->(2 shifts) k l i j
        *   m n o p ->(3 shifts) n o p m
        */
     //   printState();
        int[] row;
        int temp;
        int index;
        for(int i = 1; i < 4; i++){
            row = state[i];
            index = 0;
            while(index < i){
                temp = row[0];
                row[0] = row[3];
                row[3] = row[2];
                row[2] = row[1];
                row[1] = temp;
                index++;
            }
            state[i] = row;
        }
       // System.out.println("after shift rows");
       //printState();
    }

    private void invsubBytes(int[][] state){
        for(int row = 0; row < 4; row++){
            int[] temp = state[row];
            temp = invSubWord(temp);
            state[row] = temp;
            //printState();
        }

    }

    private int[] invSubWord(int[] eKey){
        int[] temp = new int[4];
        //hex to subox example: ca -> c:rows, a:columns
        for (int i = 0; i < 4; i++){
            String hex = Integer.toHexString(eKey[i]);
            if (hex.length() == 1){
                hex = "0" + hex;
            }
            String row = hex.substring(0, 1);
            String column = hex.substring(1, 2);

            temp[i] = invSbox[Integer.parseInt(row, 16)][Integer.parseInt(column, 16)];
        }
        return temp;
    }   

    //dont need to change this function
    private void addRoundKey(int block, int[][] state){
        /*
        *   xor with key
        *
        */
        for(int column = 0; column < 4; column++){
            for(int row = 0; row < 4; row++){
                String hexone = Integer.toHexString(state[row][column]);
                String hextwo = Integer.toHexString(expandedKey[row][column + (block * 4)]);
                //System.out.print(hexone + " ^ " + hextwo + " = ");
                //System.out.println(Integer.toHexString((Integer.parseInt(hexone, 16)) ^ (Integer.parseInt(hextwo, 16))));
                state[row][column] = (Integer.parseInt(hexone, 16)) ^ (Integer.parseInt(hextwo, 16));
            }
        }
    }

    private int[] xor(int[] one, int[] two){
        int[] temp = new int[one.length];
        for(int i = 0; i < one.length; i++){
            String hexone = Integer.toHexString(one[i]);
            String hextwo = Integer.toHexString(two[i]);
            temp[i] = (Integer.parseInt(hexone, 16)) ^ (Integer.parseInt(hextwo, 16));
        }
        return temp;
    }

    // Inverse Mix columns
    private byte mul(int a, byte b){
        int inda = (a < 0) ? (a + 256) : a;
        int indb = (b < 0) ? (b + 256) : b;

        if ( (a != 0) && (b != 0) ) {
            int index = (LogTable[inda] + LogTable[indb]);
            byte val = (byte)(AlogTable[ index % 255 ] );
            return val;
        }
        else
            return 0;
    }

    public void invMixColumn (int[][] state) {
        for(int column = 0; column < 4; column++){
            int[] temp = new int[4];
            for(int row = 0; row < 4; row++){
                temp[row] = state[row][column];
            }

            //covert back to int with unsigned bytes using & 0xFFs
            state[0][column] = (mul(0xE, (byte)temp[0]) ^ mul(0xB, (byte)temp[1]) ^ mul(0xD, (byte)temp[2]) ^ mul(0x9, (byte)temp[3])) & 0xFF;
            state[1][column] = (mul(0xE, (byte)temp[1]) ^ mul(0xB, (byte)temp[2]) ^ mul(0xD, (byte)temp[3]) ^ mul(0x9, (byte)temp[0])) & 0xFF;
            state[2][column] = (mul(0xE, (byte)temp[2]) ^ mul(0xB, (byte)temp[3]) ^ mul(0xD, (byte)temp[0]) ^ mul(0x9, (byte)temp[1])) & 0xFF;
            state[3][column] = (mul(0xE, (byte)temp[3]) ^ mul(0xB, (byte)temp[0]) ^ mul(0xD, (byte)temp[1]) ^ mul(0x9, (byte)temp[2])) & 0xFF;
        }
     }

    private int[][] keyExpansion() throws IOException{
        int[][] expandedkey = new int[4][44];
        createGrid(getLine(key), expandedkey);
        
        int[] temp = new int[4];
        for(int column = 4; column < 44; column++){
            //get W(column-1)
            temp = EK(expandedkey, column-1);
            if(column % 4 == 0){
                //rotate and substitute if first column of block
                temp = rotWord(temp);
                temp = subWord(temp);
            }
            // xor_1 : W(column - 4) ^ W(column-1)
            temp = xor(EK(expandedkey, column-4), temp);

            if(column % 4 == 0){
                int[] rcon = {Rcon[0][(column/4) - 1], Rcon[1][(column/4) - 1], Rcon[2][(column/4) - 1], Rcon[3][(column/4) - 1]};
                temp = xor(temp, rcon);
            }

            expandedkey[0][column] = temp[0];
            expandedkey[1][column] = temp[1];
            expandedkey[2][column] = temp[2];
            expandedkey[3][column] = temp[3];
        }
        
        return expandedkey;
    }

    private int[] rotWord(int[] word){
        int[] temp = {word[1], word[2], word[3], word[0]};
        return temp;
    }

    private int[] subWord(int[] eKey){
        int[] temp = new int[4];
        //hex to subox example: ca -> c:rows, a:columns
        for (int i = 0; i < 4; i++){
            String hex = Integer.toHexString(eKey[i]);
            if (hex.length() == 1){
                hex = "0" + hex;
            }
            String row = hex.substring(0, 1);
            String column = hex.substring(1, 2);

            temp[i] = sBox[Integer.parseInt(row, 16)][Integer.parseInt(column, 16)];
        }
        return temp;
    }

    private int[] EK(int[][] expandedKey, int column){
        // System.out.print(Integer.toHexString(expandedKey[0][column]) + " ");
        // System.out.print(Integer.toHexString(expandedKey[1][column]) + " ");
        // System.out.print(Integer.toHexString(expandedKey[2][column]) + " ");
        // System.out.print(Integer.toHexString(expandedKey[3][column]) + " \n");
        int[] temp = {expandedKey[0][column], expandedKey[1][column], expandedKey[2][column], expandedKey[3][column]};
        return temp;
    }


    private void printState(){
        System.out.println("STATE");
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                System.out.print(Integer.toHexString(state[i][j]) + " ");
            }
            System.out.println();
        }
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
        if (line.length() < 32){
            do {
                line=line+"0";
            } while (line.length() < 32);

        }else if(line.length() > 32){
            line = line.substring(0, 32);
        }
        return line;
    }

    private boolean validLine(String line){
        line = line.toLowerCase();
        Pattern hexPattern = Pattern.compile("([abcdef]|\\d)*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = hexPattern.matcher(line);
        matcher.find();
        if (matcher.group().length() < 32)
            return false;
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
