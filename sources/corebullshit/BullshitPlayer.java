
package corebullshit;

import java.util.*;

public class BullshitPlayer extends Player
{ 
    // fields
    private BullshitTable table;

    // constructors
    public BullshitPlayer(BullshitTable table){
        super();
        this.table = table;
    }

    // Gameplay actions
    // TODO: insert action listeners for each of these actions
    
    public ArrayList<Rank> touchCard(int index){
        // return list of ranks removed (if complete set is formed)
        try {
            // do not allow selection of more than 4 cards (per deck)
            return super.touchCardMultiple(index, this.table.getNumDecks()*4);
        }
        catch (IndexOutOfBoundsException ex){
            return new ArrayList<Rank>();
        }
    }

    public boolean issueChallenge(){
        try {
            boolean challengeResult = this.table.nextTurnChallenged(this);
            return true;
        }
        catch (InvalidMoveException ex){
            return false;
        }
    }

    public boolean submitCards(){
        try {
            this.table.placeCards(this);
            return true;
        }
        catch (InvalidMoveException ex){
            return false;
        }
        catch (IndexOutOfBoundsException ex){
            return false;
        }
    }

    public boolean submitRank(Rank rank){
        try {
            this.table.chooseRank(this, rank);
            return true;
        }
        catch (InvalidMoveException ex){
            return false;
        }
    }

    public boolean endTurn(){
        try {
            this.table.nextTurnPlayed(this);
            return true;
        }
        catch (InvalidMoveException ex){
            return false;
        }
    }
}


