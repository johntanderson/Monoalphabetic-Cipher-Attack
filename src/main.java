import ciphers.InvalidKeyException;
import ciphers.MonoAlphabeticCipher;
import cryptanalysis.FrequencyAttack;
import cryptanalysis.HillClimbingAttack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class main {
    public static void main(String[] args) throws InvalidKeyException, IOException {
        MonoAlphabeticCipher original_cipher = new MonoAlphabeticCipher(new File("./resources/ciphertext.txt"));
        System.out.println("LOADING CIPHERTEXT FROM FILE...");
        MonoAlphabeticCipher reduced_cipher = original_cipher.reduce();
        System.out.println("REDUCING CIPHERTEXT SIZE...");
        System.out.println();
        FrequencyAttack fa = new FrequencyAttack(new File("./resources/frequencies.txt"));
        HillClimbingAttack hc = new HillClimbingAttack(new File("./resources/quadgrams.txt"), 1000);
        System.out.println("INITIATING SINGLE LETTER FREQUENCY ATTACK...");
        String parent = fa.attack(reduced_cipher);
        System.out.println("\tInitial Key Found:\t"+parent);
        System.out.println();
        System.out.println("INITIATING HILL-CLIMB ATTACK...\t(please wait)");
        String key = hc.attack(reduced_cipher, parent);
        System.out.println("HILL-CLIMB ATTACK TERMINATED.");
        System.out.println();
        System.out.println("BEST KEY FOUND:\t"+key);
        System.out.println();
        System.out.println("DECRYPTING ORIGINAL CIPHER...");
        String original_plaintext = original_cipher.decrypt(key);
        bufferedWriter(new File("./resources/plaintext.txt"), original_plaintext);
        System.out.println("PLAINTEXT SAVED IN ./resources/plaintext.txt");
    }

    public static void bufferedWriter(File file, String text) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            FileWriter writer = new FileWriter(file, false);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }


}
