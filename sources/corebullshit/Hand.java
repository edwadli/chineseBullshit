
package corebullshit;

import java.util.*;

public class Hand
{
    // fields
    private LinkedList<Card> cards;

    // constructors
    public Hand(){
        this.cards = new LinkedList<Card>();
    }

    public Hand(Iterable<Card> cards){
        // copy the cards 
        this.cards = new LinkedList<Card>();
        for (Card card : cards){
            this.cards.add(new Card(card.getSuit(), card.getRank()));
        }
    }

    // mutators
    public void addCard(Card card){
        this.cards.add(card);
    }

    public void addCard(Card card, int index){
        if (index > this.cards.size()) {
            this.cards.add(card);
        }
        else {
            this.cards.add(index, card);
        }
    }

    public boolean removeCard(Card card){
        //find the card and remove it
        return this.cards.remove(card);
    }

    public Card removeCard(int index){
        // does not check for index
        return this.cards.remove(index);
    }
   
    // accessors
    public int getNumCards(){
        return this.cards.size();
    }

    public List<Card> getCards(){
        return Collections.unmodifiableList(this.cards);
    }

    // mutators - sorting 
    public void switchCards(int i, int j){
        //switch cards at positions i and j
        Card temp = this.cards.get(i);
        this.cards.set(i, this.cards.get(j));
        this.cards.set(j, temp);
    }
    
    public void sortByRank(){
        // orders the cards by rank, then by suit within each rank
        Collections.sort(this.cards, new Comparator<Card>(){
            public int compare(Card card1, Card card2){
                int rank1 = card1.getRank().ordinal();
                int rank2 = card2.getRank().ordinal();
                int suit1 = card1.getSuit().ordinal();
                int suit2 = card2.getSuit().ordinal();

                if (rank1 > rank2) return 1;
                else if (rank1 < rank2) return -1;
                else if (suit1 > suit2) return 1;
                else if (suit1 < suit2) return -1;
                else return 0;
            }
        });
    }

    public void sortBySuit(){
        // orders the cards by suit, then by rank within each suit
        Collections.sort(this.cards, new Comparator<Card>(){
            public int compare(Card card1, Card card2){
                int rank1 = card1.getRank().ordinal();
                int rank2 = card2.getRank().ordinal();
                int suit1 = card1.getSuit().ordinal();
                int suit2 = card2.getSuit().ordinal();

                if (suit1 > suit2) return 1;
                else if (suit1 < suit2) return -1;
                else if (rank1 > rank2) return 1;
                else if (rank1 < rank2) return -1;
                else return 0;
            }
        });
    }

    // mutators - removing complete sets
    public ArrayList<Rank> removeCompleteRanks(int maxRepeats){
        int[] multiplicity = new int[Rank.values().length];
        ArrayList<Rank> ranksRemoved = new ArrayList<Rank>();
        for (Card card : this.cards){
            multiplicity[card.getRank().ordinal()] += 1;
            if (multiplicity[card.getRank().ordinal()] == maxRepeats){
                ranksRemoved.add(card.getRank());
            }
        }

        for (Iterator<Card> iterator=this.cards.iterator();
                iterator.hasNext();){
            Card card = iterator.next();
            if (multiplicity[card.getRank().ordinal()] == maxRepeats){
                iterator.remove();
            }
        }

        return ranksRemoved;
    }

}

