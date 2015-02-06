
package corebullshit;

import java.util.*;

public class Player
{
    // fields
    private Hand hand;
    private boolean isOrganized;
    private LinkedList<Integer> selectedCards;

    // constructors
    public Player(){
        this.hand = new Hand();
        this.selectedCards = new LinkedList<Integer>();
    }
    public Player(Hand hand) {
        this.hand = hand;
        this.selectedCards = new LinkedList<Integer>();
    }

    public List<Card> getHand(){
        return this.hand.getCards();
    }

    public int sizeHand(){
        return this.hand.getNumCards();
    }

    public List<Integer> getSelectedIndices(){
        return Collections.unmodifiableList(this.selectedCards);
    }

    public int numCardsSelected(){
        return this.selectedCards.size();
    }

    // hand mutators
    public Hand setHand(Hand hand){
        Hand oldHand = this.hand;
        this.hand = hand;
        this.isOrganized = false;
        return oldHand;
    }

    public void takeCards(Iterable<Card> cards){
        for (Card card : cards){
            this.hand.addCard(card);
        }
        this.isOrganized = false;
    }

    public ArrayList<Rank> organizeHand(int maxRepeats){
        this.hand.sortByRank();
        return this.hand.removeCompleteRanks(maxRepeats);
    }

    public Hand clearHand(){
        Hand oldHand = this.hand;
        this.hand = new Hand();
        return oldHand;
    }

    // Gameplay actions
    public LinkedList<Card> playCards()
        throws InvalidMoveException, IndexOutOfBoundsException{

        if (this.selectedCards.size() > 0) {
            LinkedList<Card> playedCards = new LinkedList<Card>();
            // sort list so that we can remove items from the back
            //  so that multiple removals won't effect other indices
            Collections.sort(this.selectedCards);
            while (this.selectedCards.size() > 0){
                int index = this.selectedCards.removeLast();
                playedCards.add(this.hand.removeCard(index));
            }
            return playedCards;
        }
        else {
            throw new InvalidMoveException("No cards selected to play!");
        }
    }
    
    public ArrayList<Rank> touchCardMultiple(int index, int max)
        throws IndexOutOfBoundsException{
        
        if (this.isOrganized == false){
            // just organize cards and clear selections
            this.isOrganized = true;
            this.selectedCards.clear();
            return this.organizeHand(max);
        }

        if (index >= this.hand.getNumCards()){
            throw new IndexOutOfBoundsException();
        }
        // select another card, or cancel a previous selection
        int sameIndex = this.selectedCards.indexOf(index);
        if (sameIndex == -1){
            if (this.selectedCards.size() >= max){
                return new ArrayList<Rank>();
            }
            // not already in list, so add it
            this.selectedCards.add(index);
        }
        else {
            this.selectedCards.remove(sameIndex);
        }
        return new ArrayList<Rank>();
    }

    public ArrayList<Rank> touchCardMultiple(int index) throws IndexOutOfBoundsException{
        int max = this.hand.getNumCards();
        return this.touchCardMultiple(index, max);
    }

    // some alternate card selection methods
    public ArrayList<Rank> touchCardSingle(int index, int max) throws IndexOutOfBoundsException{
        
        if (this.isOrganized == false){
            // just organize cards and clear selections
            this.isOrganized = true;
            this.selectedCards.clear();
            return this.organizeHand(max);
        }
        
        if (index >= this.hand.getNumCards()){
            throw new IndexOutOfBoundsException();
        }
        // clean up
        while(this.selectedCards.size() > 1){this.selectedCards.pop();}

        // select a single card, or cancel current selection
        if (this.selectedCards.size() > 0){
            if (this.selectedCards.pop() != index){
                this.selectedCards.push(index);
            }
        }
        else {
            this.selectedCards.push(index);
        }

        return new ArrayList<Rank>();
    }

    public ArrayList<Rank> selectCard(int index, int max) throws IndexOutOfBoundsException{
        
        if (this.isOrganized == false){
            // just organize cards and clear selections
            this.isOrganized = true;
            this.selectedCards.clear();
            return this.organizeHand(max);
        }
        
        if (index >= this.hand.getNumCards()){
            throw new IndexOutOfBoundsException();
        }
        // select a single card
        if (!this.selectedCards.contains(index) && this.selectedCards.size()<max){
            this.selectedCards.add(index);
        }

        return new ArrayList<Rank>();
    }

    public ArrayList<Rank> selectCard(int index) throws IndexOutOfBoundsException{
        int max = this.hand.getNumCards();
        return this.selectCard(index, max);
    }

}


