
import corebullshit.*;
import java.util.*;

public class TerminalTest
{

    public static void printCards(Collection<Card> cards){
        for (Card card : cards){
            System.out.print("// "+card+" ");
        }
        System.out.println("//");
    }

    public static void main(String[] args){
        System.out.println("Terminal Bullshit Test");

        int numPlayers = 2;
        int numDecks = 1;
        BullshitPlayer player;
        Collection<Card> cards;
        // set up game
        BullshitTable game = new BullshitTable(numPlayers, numDecks);
        System.out.println(game.numCardsLeftDeck()+" cards to deal");
        game.shuffleDeck();
        game.dealCards();
        System.out.println(game.numCardsLeftDeck()+" cards left in deck");

        // simulate gameplay
        for (int k=0; k<2; k++){
        System.out.println("Next Turn");
        player = game.getNextPlayer();
        printCards(player.getHand());
        // organize cards
        player.touchCard(0);
        printCards(player.getHand());

        player.touchCard(0);
        for(int i=0; i<6; i++){
            player.touchCard(i);
        }
        player.submitCards();
        printCards(player.getHand());
        player.submitRank(Rank.ACE);//this will quietly fail on second pass
        player.endTurn();
        System.out.println("Player put "+game.getSizeCalled()+
                " cards and called "+game.getRankCalled().name());
        System.out.println("There are "+game.getSizePile()+" cards in the pile.");
        }
        
        System.out.println("Next Turn");
        player = game.getNextPlayer();
        printCards(player.getHand());
        player.issueChallenge();
        System.out.println("Challenge Issued");

        BullshitPlayer loser = game.getLastPlayer();
        System.out.println("previous player's new hand");
        printCards(loser.getHand());
        System.out.println("challenger player's new hand");
        printCards(player.getHand());
        

        // reset for another game, this time with two decks
        System.out.println("New Game!");
        numDecks = 2;
        game.resetDeck(numDecks);
        System.out.println(game.numCardsLeftDeck()+" cards to deal");
        game.shuffleDeck();
        game.dealCards(81);
        System.out.println(game.numCardsLeftDeck()+" cards left in deck");

        // simulate more gameplay
        player = game.getNextPlayer();
        cards = player.getHand();
        printCards(cards);
    }
}


