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
[input of TEST 1]
The Plaintext is:
00 00 00 00 
00 00 00 00 
00 00 00 00 
00 00 00 00 

The CipherKey is:
00 00 00 00 
00 00 00 00 
00 00 00 00 
00 00 00 00

[output of test 1]
The plaintext is: encrpytion output
66 ef 88 ca 
e9 8a 4c 34 
4b 2c fa 2b 
d4 3b 59 2e 

Bytes/sec:
Number of lines of plaintext: 2
Number of Bytes: 16
Time Elapsed: 0.045
Bytes per Second: 355.55555555555554

The plaintext is: decryption output
00000000000000000000000000000000
00000000000000000000000000000000

Bytes/sec:
Number of lines of plaintext: 2
Number of Bytes: 16
Time Elapsed: 0.023
Bytes per Second: 695.6521739130435


[input of TEST 2]
The plaintext is:
0A935D11496532BC1004865ABDCA4295
00112233445566778899AABBCCDDEEFF
0A935D11496532BC1004865ABDCA4295
C6E4E48BA48787E8C6E4E48BA48787E8
B417699B4969173DB417699B4969173D
B8BAC794039F49DFB8BAC794039F49DF
340D605A022FD91CD6600D3F30D8445C
7C6363637C6363637C6363637C636363
00000000000000000000000000000000
00000000000000000000000000000000
45D41785B030A0C8253EED720B0B8474

The CipherKey is:
00 00 00 00 
00 00 00 00 
00 00 00 00 
00 00 00 00

[output of test 2]
The plaintext is: encryption output 
7d9aeeb02ee9b22893f3f276ac10fd19
c8a331ff8edd3db175e1545dbefb760b
7d9aeeb02ee9b22893f3f276ac10fd19
79fdf939b68bbdce0796ae282ca6f7fa
8582b313b6f81eee30d8510305d2332e
b35374947bb9300a9557ff8a6a1cced8
fbf5ad8aaf77f4d181c765724c742b63
c0831787b115b26c57291f17ece80bb3
66e94bd4ef8a2c3b884cfa59ca342b2e
66e94bd4ef8a2c3b884cfa59ca342b2e
d4048035162b1e5b518a4e00b7bd0069

Bytes/sec: 
Number of Bytes: 88
Time Elapsed: 0.146
Bytes per Second: 602.7397260273973

The plaintext is: decrption output
0a935d11496532bc1004865abdca4295
00112233445566778899aabbccddeeff
0a935d11496532bc1004865abdca4295
c6e4e48ba48787e8c6e4e48ba48787e8
b417699b4969173db417699b4969173d
b8bac794039f49dfb8bac794039f49df
340d605a022fd91cd6600d3f30d8445c
7c6363637c6363637c6363637c636363
00000000000000000000000000000000
00000000000000000000000000000000
45d41785b030a0c8253eed720b0b8474

Bytes/sec: 
Number of Bytes: 88
Time Elapsed: 0.08
Bytes per Second: 1100.0
 