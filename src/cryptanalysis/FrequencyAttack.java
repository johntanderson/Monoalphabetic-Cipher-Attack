package cryptanalysis;

import ciphers.MonoAlphabeticCipher;
import cryptanalysis.utils.Frequency;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

public class FrequencyAttack {
    TreeSet<Frequency> frequencies = new TreeSet<>();
    File frequency_file;

    public FrequencyAttack(File frequency_file) throws IOException {
        this.frequency_file = frequency_file;
    }

    private void getFrequencies() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(frequency_file));
        for(String line; (line = br.readLine()) != null; ) {
            String[] data = line.split(" ");
            frequencies.add(new Frequency(Character.toUpperCase(data[0].charAt(0)), Double.parseDouble(data[1])));
        }
    }

    public String attack(MonoAlphabeticCipher cipher) throws IOException {
        getFrequencies();
        int total = 0;
        double[] frequencies = new double[26];
        char[] key = new char[26];
        StringBuilder builder = new StringBuilder();

        if (this.frequencies.isEmpty()) return null;

        for (Character c: cipher) {
            if (MonoAlphabeticCipher.isLetter(c)) {
                total++;
                frequencies[c-'A']++;
            }
        }

        for (int i = 0; i < frequencies.length; i++) {
            Frequency current = new Frequency((char) (i + 'A'), (frequencies[i]/total)*100);
            Frequency floor = this.frequencies.floor(current);
            Frequency ceiling = this.frequencies.ceiling(current);
            Frequency closest;
            if (floor != null && ceiling != null){
                closest = Math.abs(current.getFrequency() - floor.getFrequency()) < Math.abs(current.getFrequency() - ceiling.getFrequency())
                        ? floor
                        : ceiling;
            } else {
                closest = floor != null ? floor : ceiling;
            }
            assert closest != null;
            key[closest.toInteger()] = current.toChar();
            this.frequencies.remove(closest);
        }
        for (Character c: key) builder.append(c);
        return builder.toString();
    }

}
