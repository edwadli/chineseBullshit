
import corebullshit.*;
import java.util.*;
import java.io.*;

public class TerminalBullshit
{

    public static void println(String string){
        System.out.println(string);
    }
    public static void print(String string){
        System.out.print(string);
    }

    public static boolean askUserPlayAgain(){
        Scanner reader = new Scanner(System.in);
        print("\nPlay again?\n0 - No\n1 - Yes\n=> ");
        int response = reader.nextInt();
        while (response != 0 && response != 1){
            print("\nInvalid Response.\nPlay again?\n0 - No\n1 - Yes\n=> ");
            response = reader.nextInt();
        }
        if (response == 1){
            return true;
        }
        else{
            return false;
        }
    }

    public static void printWinners(ArrayList<BullshitPlayer> winners){
        println("====================");
        print("Game Over!\nRankings:\n");
        println("====================");
        for (int i=0; i<winners.size(); i++){
            println((i+1)+": "+winners.get(i));
        }
        println("====================");
    }

    public static void printNCards(int numCards){
        for (int i=0; i<numCards; i++){
            print("[]");
        }
    }

    public static void printGameState(BullshitTable game, BullshitPlayer player){
            // TODO: use custom iterator of Table.java
        List<Player> players = game.getPlayers();
        int playerIndex = players.indexOf(player);
        BullshitPlayer currentPlayer = game.getNextPlayer();
        if (playerIndex != -1){
            // default view is of current player
            playerIndex = players.indexOf(currentPlayer);
        }
        
        // PRINT GAME STATE
        print("\n");
        for(int i=playerIndex+1; i<playerIndex+game.numPlayers(); i++){
            int index = i%game.numPlayers();
            // print number of cards of each player
            if (i==playerIndex+1){
                print("  ");
            }
            else {
                print("\t//\t");
            }
            BullshitPlayer thisPlayer = (BullshitPlayer) game.getPlayer(index);
            print("p"+thisPlayer.hashCode()+" ");
            if (thisPlayer == currentPlayer){
                // indicate turn
                print("<turn>>");
            }
            printNCards(thisPlayer.sizeHand());
        }
        // print pile size and current call
        int pileSize = game.getSizePile();
        Rank rankCalled = game.getRankCalled();
        int sizeCalled = game.getSizeCalled();
        print("\n");
        print("Pile:");
        printNCards(pileSize);
        print(" ("+pileSize+" cards)\n");
        if (rankCalled == null){
            print("Call: None\n");
        }
        else {
            print("Call: "+sizeCalled+" "+rankCalled.name()+"s");
        }
        
        // print player's own hand
        print("\n");
        BullshitPlayer mainPlayer = (BullshitPlayer) game.getPlayer(playerIndex);
        print("p"+mainPlayer.hashCode());
        if (mainPlayer == currentPlayer){
            // indicate turn
            print("<turn>>");
        }
        List<Card> hand = mainPlayer.getHand();
        List<Integer> selectedCardIndices = mainPlayer.getSelectedIndices();
        for (int i=0; i<hand.size(); i++){
            if (selectedCardIndices.contains(i)){
                print(" "+i+"{{"+hand.get(i)+"}}");
            }
            else{
                print(" "+i+"["+hand.get(i)+"]");
            }
        }
        print("\n");
    }

    public static boolean isNumeric(String str){
        for (char c : str.toCharArray()){
            if (!('0'<=c && c<='9')) return false;
        }
        return true;
    }

    public static void printUserActionMenu(){
        println("\n(int),(p)lay,(c)all,(r)ank? ");
        /*
        println("\nWhat do you want to do?");
        println("(You can chain the options by separating by a space)");
        //index(int),p,c,r->A,2-9,T,J,Q,K
        println("Select a Card (Index): (int)");
        println("Play Selected Cards: p");
        println("Call Bullshit: c");
        println("Call a Rank: r followed by A,2,3,4,5,6,7,8,9,T,J,Q,or K");
        */
    }

    public static void userAction(BullshitTable game, BullshitPlayer player){
        printUserActionMenu();
        // get user response
        BufferedReader bReader = new BufferedReader( new InputStreamReader(System.in));

        String[] userInputs;
        try {
            userInputs = bReader.readLine().split("\\s+");
        } catch (Exception ex) {
            return;
        }

        // parse user response, if anything fails, stop parsing
        for(int i=0; i<userInputs.length; i++){
            String action = userInputs[i];
            if (isNumeric(action)){
                // touch a card
                // dont do anything about complete sets
                // (let them quietly disappear)
                player.touchCard(Integer.parseInt(action));
            }
            else if (action.charAt(0)=='p'){
                // play selected cards
                if (!player.submitCards()){
                    break;
                }
                else if (player.endTurn()){
                    // attempt to end turn after successful play of cards
                    // failure means that rank needs to be called
                    // so we want to continue parsing in that case
                    break;
                }
            }
            else if (action.charAt(0)=='c'){
                // call bullshit
                player.issueChallenge();
                break;
            }
            else if (action.charAt(0)=='r'){
                // call a rank - get next action
                i++;
                if (i<userInputs.length){
                    // TODO: make this a method in the Enum Rank
                    String rank = userInputs[i];
                    int ordinal;
                    if (isNumeric(rank)){
                        int desiredRank = Integer.parseInt(rank);
                        if (!(2<desiredRank && desiredRank<9)){
                            // invalid rank number
                            break;
                        }
                        ordinal = desiredRank-1;
                    }
                    else {
                        char c = rank.charAt(0);
                        if (c=='A') ordinal = 0;
                        else if (c=='T') ordinal = 9;
                        else if (c=='J') ordinal = 10;
                        else if (c=='Q') ordinal = 11;
                        else if (c=='K') ordinal = 12;
                        else break;
                        //break if incorrect action
                        // follows 'call (r)ank' action
                    }
                    player.submitRank(Rank.values()[ordinal]);
                    player.endTurn();
                    break;
                }
                else{
                    // incorrect action follows 'call (r)ank' action
                    break;
                }
            }
            else {
                break;
            }
        }
    }

    public static void main(String[] args){
        // TODO: fix turn mechanics in BullshitTable.java
        // TODO: fix number of winners
        println("Terminal Bullshit!!! (4 Player, 1 Deck)");
        println("Note this version only allows BS calls on your own turn");

        // set up game
        int numPlayers = 4;
        int numDecks = 1;
        BullshitTable game = new BullshitTable(numPlayers, numDecks);

        // shuffle and deal
        println("Dealing "+game.numCardsLeftDeck()+" cards");
        game.shuffleDeck();
        game.dealCards(4);

        // multiple games loop, can keep track of win/loss record
        boolean isAgain = true;
        while (isAgain == true){
            println("Game Start!");
            
            // main bullshit game loop
            BullshitPlayer currentPlayer = game.getNextPlayer();
            // PRINT GAME STATE (from view of current player)
            printGameState(game, currentPlayer);
            while (game.numWinners() < numPlayers-1){
                // PLAYER TURN
                while (currentPlayer == game.getNextPlayer()){
                    userAction(game, currentPlayer);
                    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    // PRINT GAME STATE (from view of current player)
                    printGameState(game, currentPlayer);
                }
                // NEXT PLAYER
                currentPlayer = game.getNextPlayer();
            }

            // game over, print winner rankings
            ArrayList<BullshitPlayer> winners = game.getWinners();
            printWinners(winners);

            // game restart?
            isAgain = askUserPlayAgain();
            if (isAgain){
                game.resetTable();
            }
        }
        /* game start.
         *
         * PRINT game state
         *      get current player and current player hand and touched cards
         *          game.getNextPlayer()
         *          player.getHand()
         *      get all players hand size
         *      get pile size
         *          game.getSizePile()
         *      get current call
         *          game.getSizeCalled()
         *          game.getRankCalled().name()
         *
         * PLAYER Turn (options of possible actions):
         *  player.touchCard(int index) --> reprint current player hand
         *                                      player.getSelectedIndices()
         *                              --> print any cards (completed ranks) removed
         *  player.submitCards() --> reprint current player hand
         *                       --> reprint current call size
         *  player.submitRank(Rank rank) [Rank.ACE] --> reprint current call rank
         *                                          --> clear current call size
         *  player.endTurn();
         *  player.issueChallenge(); --> reveal top cards in pile
         *                                  game.peekPile();
         *                           --> reprint current player hand
         *                           --> reprint previous player hand
         *                                  game.getLastPlayer()
         *                           --> clear current call
         * 
         * CHECK how many winners there are:
         *      if game.numWinners() >= numPlayers-1:
         *          end game, display winner rankings
         *      else:
         *          repeat
         *
         * game end (or reset game)
         *  game.resetTable()

        */
    }
}
