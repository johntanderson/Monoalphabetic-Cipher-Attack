package cryptanalysis.utils;

import java.io.*;
import java.util.HashMap;

public class FitnessTest {
    HashMap<String, Double> ngrams = new HashMap<>();
    long N = 0;
    int L = 0;
    double floor;

    public FitnessTest(File ngram_file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ngram_file));
        String key = "";
        for(String line; (line = br.readLine()) != null; ) {
            String[] ngram = line.split(" ");
            key = ngram[0];
            double count = Double.parseDouble(ngram[1]);
            this.N += count;
            ngrams.put(key, count);
        }
        this.L = key.length();
        for (String k : ngrams.keySet()) {
            this.ngrams.replace(k, Math.log10(this.ngrams.get(k)/this.N));
        }
        this.floor = Math.log10(0.01/this.N);
    }

    public double score(String text){
        double score = 0;
        for (int i = 0; i <= text.length()-this.L; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = i; j < i+this.L; j++) {
                builder.append(text.charAt(j));
            }
            String ngram = builder.toString();
            if (this.ngrams.containsKey(ngram)) score += ngrams.get(ngram);
            else score+= this.floor;
        }
        return score;
    }
}
