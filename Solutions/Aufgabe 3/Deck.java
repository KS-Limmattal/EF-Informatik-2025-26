import java.util.Random;
import java.util.Arrays;

/**
 * Diese Klasse repräsentiert einen Kartenstapel mit einer variablen Anzahl
 * Karten.
 */
public class Deck {
    private Card[] cards;
    
    public Deck() {
        this(generateCards());
    }

    public Deck(Card[] cards) {
        this.cards = cards;
    }

    // Fisher–Yates shuffle
    public void shuffle() {
        Random rnd = new Random();
        for (int i = cards.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            swapCards(i, index);
        }
    }

    public void sort(Suit trump) {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < cards.length - 1; i++) {
                if (sortingPosition(cards[i], trump) > sortingPosition(cards[i + 1], trump)) {
                    sorted = false;
                    Card temp = cards[i];
                    cards[i] = cards[i + 1];
                    cards[i + 1] = temp;
                }
            }
        }
    }

    public Card[] getCards() {
        return cards;
    }

    private static Card[] generateCards() {
        Card[] cards = new Card[36];
        int i = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards[i] = new Card(suit, rank);
                i++;
            }
        }
        return cards;
    }

    public void addCard(Card card) throws Exception {
        for (Card i : this.cards) {
            if (i.equals(card)) {
                throw new Exception("The card " + card + " is already in the deck.");
            }
        }
        Card[] newCards = Arrays.copyOf(cards, cards.length + 1);
        newCards[newCards.length - 1] = card;
        cards = newCards;
    }

    public Card pop() throws Exception {
        Card card = cards[cards.length - 1];
        if (cards.length > 0) {
            cards = Arrays.copyOf(cards, cards.length - 1);
        } else {
            throw new Exception("No card to pop.");
        }
        return card;
    }

    public void multiPop(int n) throws Exception {
        for (int i = 0; i < n; i++) {
            this.pop();
        }
    }

    private Card remove(int index) throws Exception {
        Card remove = cards[index];
        for (int i = index; i < cards.length - 1; i++) {
            cards[i] = cards[i + 1];
        }
        cards[cards.length - 1] = remove;
        return pop();
    }

    public Card remove(Card card) throws Exception {
        remove(indexOf(card));
        return card;
    }

    private int sortingPosition(Card c, Suit trump) {
        int position = (c.suit == trump) ? 50 : 0;
        position += 10 * c.suit.ordinal();
        position += c.getStrength(trump);
        return position;
    }

    private void swapCards(int i, int j) {
        Card c = cards[j];
        cards[j] = cards[i];
        cards[i] = c;
    }

    public Card[] playableCards(Deck played, Suit trump) {
        if (played.getCards().length < 1) {
            return this.cards;
        }

        Suit firstSuit = played.getCards()[0].suit;
        boolean containsFirstSuit = false;
        Card[] playableCards = new Card[0];

        for (Card card : this.cards) {
            if (card.suit == firstSuit || card.suit == trump) {
                if (card.suit == firstSuit) {
                    containsFirstSuit = true;
                }
                playableCards = Arrays.copyOf(playableCards, playableCards.length + 1);
                playableCards[playableCards.length - 1] = card;
            }
        }

        if (!containsFirstSuit) {
            playableCards = this.cards;
        }

        return playableCards;
    }

    public boolean contains(Card card) {
        return indexOf(card) != -1;
    }

    private int indexOf(Card cardToFind) {
        for (int i = 0; i < getCards().length; i++) {
            if (cardToFind.equals(getCards()[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String str = "{ ";
        for (Card i : cards) {
            str += i + " ";
        }
        return str + "}";
    }
}
