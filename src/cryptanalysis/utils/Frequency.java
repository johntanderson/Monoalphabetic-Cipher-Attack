package cryptanalysis.utils;

public class Frequency implements Comparable<Frequency> {
    private Character character;
    private Double frequency;

    public Frequency(Character character, Double frequency){
        this.character = Character.toUpperCase(character);
        this.frequency = frequency;
    }

    public double getFrequency(){
        return this.frequency;
    }

    public void setFrequency(double frequency){
        this.frequency = frequency;
    }

    public void setCharacter(char character){
        this.character = Character.toUpperCase(character);
    }

    public String toString(){
        return String.valueOf(this.character);
    }

    public int toInteger(){
        return this.character-'A';
    }

    public char toChar(){
        return this.character;
    }

    @Override
    public int compareTo(Frequency frequency) {
        if (this == frequency) return 0;
        if (frequency == null) return 1;
        return this.frequency.compareTo(frequency.getFrequency());
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        return this.frequency.compareTo(((Frequency) o).getFrequency()) == 0;
    }

    public int hashCode() {
        return this.frequency.hashCode();
    }
}
