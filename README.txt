UTEID: lmh2742;krm965;

FIRSTNAME: Lauren; Kyle;

LASTNAME: Hunter; Martinez;

CSACCOUNT: hunter7;kmart;

EMAIL: hunter7@cs.utexas.edu;kylemartinez10@yahoo.com;

[Program 4]
[Description]
There are 3 files: AES.java, AESEncrypt.java, AESDecrypt.java

AES.java: This file reads in the arguments and either calls encrypt or decrypt based on the flag in the arguments

AESEncrypt.java: This file contains the sBox, LogTable, AlogTable,and Rcon which are used to encrypt. Encrypt reads in the plaintext and key and converts them into the right form. The is expanded by keyExpansion(), this funciton reads in the key and converts it into a 2D array. Then that key is expanded by performing rotWord, subWord, and xor. rotWord rotates the column, subWord substitutes the hex values, and xor...xors. Once we have the full key, we begin to encrypt. The encryption process follows AES 128, there are 11 rounds in which we perform multiple altercations to the state.
EK(): grabs the correct column of the key 
addRoundKey(): xors the state and the key togehter
subBytes(): substitues the hex values in with sBox
shiftRows(): shifts the rows of the state accoring to AES 128
mixColumns(): (we used the funciton provided by the professor)
At the end of the rounds, we write the encrpted state/message to a file. 

AESDecrypt.java: This file contains the InvsBox, LogTable, AlogTable, and Rcon which are used to decrypt. Decrpyt is very similar to encrypt but with the inverse functions. Decrypt reads in the plaintext and key and converts them into the right form. Then the key is expanded, in the same way as encrpyt. Then the decryption process begings, which follows AES 128, there are 11 rounds of decryption in which we peform multiple altercation to the state to extract the message. 
EK(): grabs the correct column of the key 
addRoundKey(): xors the key and the state together
invShiftRows(): the inverse of shiftRows()
invsubBytes(): the inverse of subBytes() aka x,y -> y,x
invMixColumns(): (we used the funciton provided by the professor)
At the end of the rounds we write the decrpted message to a file

How to run program:
To compile: javac *.java in AES/src
To run: java AES option keyfile inputfile

[Finished]
We finished all of the assignment.

[Test Cases]
We have 2 test cases
[input of test 1]
 