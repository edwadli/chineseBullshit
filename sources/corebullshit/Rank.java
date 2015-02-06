
package corebullshit;

public enum Rank {
    ACE("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"),
    SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("T"),
    JACK("J"), QUEEN("Q"), KING("K");

    private String string;
    private Rank(String string) {this.string = string;}

    @Override
    public String toString(){
        return this.string;
    }
}

