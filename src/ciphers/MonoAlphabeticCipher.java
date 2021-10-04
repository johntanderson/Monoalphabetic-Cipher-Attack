package ciphers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class MonoAlphabeticCipher implements Iterable<Character> {
    private ArrayList<Character> ciphertext = new ArrayList<>();

    public MonoAlphabeticCipher(String plaintext, String key) throws InvalidKeyException {
        key = validateKey(key);
        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            if(isLetter(c)){
                c = Character.toUpperCase(c);
                c = key.charAt((c-'A'));
            }
            this.ciphertext.add(c);
        }
    }

    public MonoAlphabeticCipher(File file, String key) throws IOException, InvalidKeyException {
        key = validateKey(key);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file),
                        StandardCharsets.UTF_8));
        int c;
        while((c = reader.read()) != -1) {
            char character = (char) c;
            if (isLetter(character)){
                character = Character.toUpperCase(character);
                character = key.charAt((character-'A'));
            }
            ciphertext.add(character);
        }
    }

    public MonoAlphabeticCipher(String ciphertext){
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            if (isLetter(c)) c = Character.toUpperCase(c);
            this.ciphertext.add(c);
        }
    }

    public MonoAlphabeticCipher(File file) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file),
                        StandardCharsets.UTF_8));
        int c;
        while((c = reader.read()) != -1) {
            char character = (char) c;
            if (isLetter(character)) character = Character.toUpperCase(character);
            ciphertext.add(character);
        }
    }

    public MonoAlphabeticCipher reduce(){
        StringBuilder builder = new StringBuilder();
        for (Character c: this.ciphertext) {
            if(isLetter(c)) {
                builder.append(c);
            }
        }
        return new MonoAlphabeticCipher(builder.toString());
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (Character c: ciphertext) {
            builder.append(c);
        }
        return builder.toString();
    }

    public String decrypt(String key) throws InvalidKeyException {
        StringBuilder builder = new StringBuilder();
        key = validateKey(key);
        HashMap<Character, Character> map = new HashMap<>();
        for (int i = 0; i < key.length(); i++) {
            map.put(key.charAt(i), (char)(i+'A'));
        }
        for (Character c: ciphertext) {
            if (isLetter(c)) c = map.get(c);
            builder.append(c);
        }
        return builder.toString();
    }

    public String decrypt(char[] key) throws InvalidKeyException {
        return this.decrypt(String.valueOf(key));
    }

    public String validateKey(String  key) throws InvalidKeyException {
        StringBuilder builder = new StringBuilder();
        HashSet<Character> chars = new HashSet<>();
        if (key.length() != 26) throw new InvalidKeyException("INVALID KEY: key is not of length 26.");
        for (int i = 0; i < key.length(); i++) {
            char c = Character.toUpperCase(key.charAt(i));
            if (!isLetter(c)) throw new InvalidKeyException("INVALID KEY: key contains non-letter values.");
            if (chars.contains(c)) throw new InvalidKeyException("INVALID KEY: key contains duplicates.");
            builder.append(c);
            chars.add(c);
        }
        return builder.toString();
    }

    public static boolean isLetter(Character c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    @Override
    public Iterator<Character> iterator() {
        return this.ciphertext.iterator();
    }
}
