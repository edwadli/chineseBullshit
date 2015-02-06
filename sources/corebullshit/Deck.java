
package corebullshit;

import java.util.*;

public class Deck
{
    private ArrayList<Card> cards;
    
    // constructor
    public Deck(int num_decks){
        // construct one of each type of card: suit and rank
        this.cards = new ArrayList<Card>();
        for (int i=0; i<num_decks; i++){
            for (Suit s: Suit.values()){
                for (Rank r: Rank.values()){
                    this.cards.add(new Card(s,r));
                }
            }
        }
    }

    // methods
    public void shuffle(){
        Collections.shuffle(this.cards, new Random());
    }

    public Card deal(){
        return this.cards.remove(cards.size()-1);
    }

    public ArrayList<Card> deal(int amount){
        ArrayList<Card> dealtCards = new ArrayList<Card>();
        for (int i=0; i<amount; i++){
            dealtCards.add(this.cards.remove(cards.size()-1));
        }
        return dealtCards;
    }

    public int getNumCards(){
        return this.cards.size();
    }

}
