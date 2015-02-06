
package corebullshit;

import java.util.*;

public class Table
{

        // TODO: use a different structure for list of players
        //          --> hash table: keys are players, values are
        //                      nodes with next and prev players
   

    // fields
    private ArrayList<Player> players;
    private Deck deck;
    private int numDecks;

    // constructors
    public Table(int numDecks){
        this.numDecks = numDecks;
        this.deck = new Deck(this.numDecks);
        this.players = new ArrayList<Player>();
    }

    public Table(int numPlayers, int numDecks){
        this.numDecks = numDecks;
        this.deck = new Deck(this.numDecks);
        this.players = new ArrayList<Player>();
        for (int i=0; i<numPlayers; i++){
            // add player to the table
            this.players.add(new Player());
        }
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public Player removePlayer(int index){
        return this.players.remove(index);
    }

    public void dealCards(int numberCards){
        if (players.size() == 0){
            // TODO: maybe change this to exception?
            return;
        }
        int numPlayers = this.players.size();
        int cardsPerPlayer = numberCards/numPlayers;
        for (int i=0; i<numPlayers; i++){
            Hand hand = new Hand(this.deck.deal(cardsPerPlayer));
            // also give first few players the left over cards
            if (i < numberCards%numPlayers){
                hand.addCard(this.deck.deal());
            }
            // give the player this hand
            this.players.get(i).setHand(hand);
        }
    }

    public void dealCards(){
        // default deal is the entire deck
        this.dealCards(this.deck.getNumCards());
    }

    public void shuffleDeck(){
        this.deck.shuffle();
    }

    public void resetDeck(){
        this.deck = new Deck(this.numDecks);
        for (Player player : this.players){
            player.clearHand();
        }
    }

    public void resetDeck(int numDecks){
        this.numDecks = numDecks;
        this.resetDeck();
    }

    public int getNumDecks(){
        return this.numDecks;
    }

    public int numCardsLeftDeck(){
        return this.deck.getNumCards();
    }

    public Player getPlayer(int index){
        return this.players.get(index);
    }

    public List<Player> getPlayers(){
        return Collections.unmodifiableList(this.players);
    }

    public int numPlayers(){
        return this.players.size();
    }

}
