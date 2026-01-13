import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Jass {
    private Random random = new Random();
    private Scanner inputScanner = new Scanner(System.in);

    private Deck deck, playedCards;
    private int numberOfPlayers;
    private Deck[] hands;
    private int[] points;
    private boolean player1IsHuman;

    private Suit trump;
    private int roundPoints, roundWinner, highestStrength;
    
    public Jass(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.deck = new Deck();

        // shuffle and deal out cards
        deck.shuffle();
        this.hands = new Deck[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            hands[i] = createHand(numberOfPlayers);
        }

        // prepare table and counting board
        this.playedCards = deck; // now empty
        this.points = new int[numberOfPlayers];
    }
    
    private void setTrump(Suit trump) throws Exception {
        if (this.trump == null) {
            this.trump = trump;
        } else {
            throw new Exception("Trumpf kann nicht verändert werden.");
        }
    }
    
    private Deck createHand(int numberOfPlayers) {
        Deck hand = new Deck(new Card[0]);
        int bound = deck.getCards().length;
        for (int i = bound; i > bound - (36 / numberOfPlayers); i--) {
            try {
                hand.addCard(deck.getCards()[i - 1]);
                deck.pop();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        return hand;
    }

    private void playCard(int player, Card card) throws Exception{
        hands[player].remove(card);
        playedCards.addCard(card);
        roundPoints += card.getFaceValue(card.suit.equals(trump));
        if (card.getStrength(playedCards, trump) > highestStrength) {
            highestStrength = card.getStrength(playedCards, trump);
            roundWinner = player;
        }
    }

    // By IntroSpecter
    private void botPlay(int activePlayer) throws Exception {
        int handIndex = hands[activePlayer].getCards().length - 1;
        Card cardToPlay;
        if (playedCards.getCards().length == 0) {
            handIndex = random.nextInt(hands[activePlayer].getCards().length);
            cardToPlay = hands[activePlayer].getCards()[handIndex];
        } else {
            Card[] playableCards = hands[activePlayer].playableCards(playedCards, trump);
            Card[] trumpCards = new Card[0];
            for (Card card : playableCards) {
                if (card.suit == trump) {
                    trumpCards = Arrays.copyOf(trumpCards, trumpCards.length + 1);
                    trumpCards[trumpCards.length - 1] = card;
                }
            }
            if (trumpCards.length > 0) {
                handIndex = random.nextInt(trumpCards.length);
                cardToPlay = trumpCards[handIndex];
            } else {
                handIndex = random.nextInt(playableCards.length);
                cardToPlay = playableCards[handIndex];
            }
        }
            playCard(activePlayer, cardToPlay);
            System.out.println("Bot spielt  " + cardToPlay + ".");
    }

    private void humanPlay() {
        System.out.println("Ihre Hand: " + hands[0]);
        Deck playableCards = new Deck (hands[0].playableCards(playedCards, trump));
        // System.out.println("davon ausspielbar: " + new Deck(playableCards));
        wloop: while (true) {
            System.out.println("Geben sie eine Karte (z.B. 'schellen acht') ein:");
            String userResponse = inputScanner.nextLine().toUpperCase();
            if (userResponse.equals("Q")) {
                inputScanner.close();
                return;
            }
            try {
                Card card = Card.fromString(userResponse);
                if (!playableCards.contains(card)){
                    throw new Exception(card + " ist kein gültiger Zug.");
                }
                playCard(0, card);
                eraseLines(3);
                System.out.println("Spieler 1: Sie spielen " + card);
                break wloop;
            } catch (Exception e) {
                eraseLines(2);
                System.out.print("Ungültige Eingabe: " + e + " ");
            }
        }
    }


    private int playRound(int round, int startingPlayer) throws Exception {
        System.out.println("Runde " + (round + 1));
        System.out.println("-------");

        roundPoints = 0;
        roundWinner = 0;
        highestStrength = 0;

        // players playing cards
        for (int i = startingPlayer; i < startingPlayer + numberOfPlayers; i++) {
            int activePlayer = i % numberOfPlayers;
            if (activePlayer == 0 && player1IsHuman) {
                humanPlay();
            } else {
                System.out.print("Spieler " + (activePlayer + 1) + ": ");
                botPlay(activePlayer);
            }
        }

        // counting points and determining the round winner
        points[roundWinner] += roundPoints;
        System.out.println("Spieler " + (roundWinner + 1) + " gewinnt die Runde und macht " + roundPoints + " Punkte.");
        waitForEnter();
        playedCards.multiPop(numberOfPlayers);

        return roundWinner;
    }

    public void play() throws Exception {
        System.out.print("\nWILLKOMMEN ZUM DIENSTAGS-JASS!\nWollen Sie mitspielen? (y/n): ");
        player1IsHuman = inputScanner.nextLine().toUpperCase().strip().equals("Y");

        // Set trump Suit
        if (player1IsHuman) {
            hands[0].sort(trump);
            System.out.println("\nIhre Karten: " + hands[0]);
            String trumpString;
            do {
                System.out.println("Wählen Sie die Trumpf-Farbe: ");
                trumpString = inputScanner.nextLine().toUpperCase().strip();
                try {
                    setTrump(Suit.valueOf(trumpString));
                } catch (Exception e) {
                    System.out.println("Ungültige Farbe");
                    trumpString = "error";
                }
            } while (trumpString == "error");
            eraseLines(2);
        } else {
            System.out.println("\nDas Spiel findet ohne menschliche Spieler statt.");
            try {
                setTrump(Suit.values()[random.nextInt(4)]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nTrumpf ist " + trump + ".");
        waitForEnter();
        hands[0].sort(trump);

        // play rounds
        int startingPlayer = 0;
        for (int i = 0; i < (36 / numberOfPlayers); i++) {
            startingPlayer = playRound(i, startingPlayer);
        }

        // end
        System.out.println("Spielende");
        System.out.println("---------");
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Spieler " + (i + 1) + " hat " + points[i] + " Punkte gemacht.");
        }
        inputScanner.close();
    }


    private void waitForEnter() {
        System.out.println("(weiter mit enter)");
        inputScanner.nextLine();
        eraseLines(2);
        System.out.println();
    }

    private static void eraseLines(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(String.format("\033[%dA", 1)); // Move up
            System.out.print("\033[2K"); // Erase line content
        }
    }


    public static void main(String[] args) throws Exception {
        Jass jass = new Jass(4);
        jass.play();
    }

}
