
package corebullshit;

public enum Suit {
    SPADE("S"), HEART("H"), DIAMOND("D"), CLUB("C");

    private String string;
    private Suit(String string) {this.string = string;}

    @Override
    public String toString(){
        return this.string;
    }
}
