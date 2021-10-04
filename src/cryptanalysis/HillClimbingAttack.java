package cryptanalysis;

import ciphers.InvalidKeyException;
import ciphers.MonoAlphabeticCipher;
import cryptanalysis.utils.FitnessTest;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class HillClimbingAttack {
    private FitnessTest fitness_test;
    private int limit;

    public HillClimbingAttack(File ngram_file, int limit) throws IOException {
        this.fitness_test = new FitnessTest(ngram_file);
        this.limit = limit;
    }

    public void setLimit(int limit){
        this.limit = limit;
    }

    public void setFitnessTest(File ngram_file) throws IOException {
        this.fitness_test = new FitnessTest(ngram_file);
    }

    public String attack(MonoAlphabeticCipher cipher, String parent) throws InvalidKeyException {
        Random random = new Random();
        cipher = cipher.reduce();
        if (cipher.toString().length() > 10000) cipher = new MonoAlphabeticCipher(cipher.toString().substring(0, 10000));
        char[] parent_array = parent.toCharArray();
        String plaintext = cipher.decrypt(parent);
        double parent_fitness = this.fitness_test.score(plaintext);
        int count = 0;
        while (count < this.limit){
            char[] child_array = parent_array.clone();
            int i = random.nextInt(child_array.length);
            int j = random.nextInt(child_array.length);
            char temp = child_array[i];
            child_array[i] = child_array[j];
            child_array[j] = temp;
            plaintext = cipher.decrypt(child_array);
            double child_fitness = this.fitness_test.score(plaintext);
            if (child_fitness > parent_fitness) {
                parent_array = child_array.clone();
                parent_fitness = child_fitness;
                count = 0;
                System.out.println("\tbetter key found:\t"+String.valueOf(parent_array));
            } else {
                count++;
            }
        }
        return String.valueOf(parent_array);
    }
}
