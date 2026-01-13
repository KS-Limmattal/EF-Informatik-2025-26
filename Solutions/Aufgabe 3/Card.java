/**
 * Diese Klasse repräsentiert eine Karte (Bsp. EICHELN ASS)
 */
public class Card {
    public final Suit suit;
    public final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getFaceValue(boolean isTrump) {
        switch (this.rank) {
            case Rank.SECHS:
                return 0;
            case Rank.SIEBEN:
                return 0;
            case Rank.ACHT:
                return 0;
            case Rank.NEUN:
                return (isTrump) ? 14 : 0;
            case Rank.BANNER:
                return 10;
            case Rank.UNDER:
                return (isTrump) ? 20 : 2;
            case Rank.OBER:
                return 3;
            case Rank.KOENIG:
                return 4;
            case Rank.ASS:
                return 11;
            default:
                return 0;
        }
    }

    public int getStrength(Suit openingSuit, Suit trump) {
        if (this.suit == trump) {
            switch (this.rank) {
                case Rank.SECHS:
                    return 10;
                case Rank.SIEBEN:
                    return 11;
                case Rank.ACHT:
                    return 12;
                case Rank.BANNER:
                    return 13;
                case Rank.OBER:
                    return 14;
                case Rank.KOENIG:
                    return 15;
                case Rank.ASS:
                    return 16;
                case Rank.NEUN:
                    return 17;
                case Rank.UNDER:
                    return 18;
                default:
                    return 10;
            }
        } else if (this.suit == openingSuit) {
            switch (this.rank) {
                case Rank.SECHS:
                    return 1;
                case Rank.SIEBEN:
                    return 2;
                case Rank.ACHT:
                    return 3;
                case Rank.NEUN:
                    return 4;
                case Rank.BANNER:
                    return 5;
                case Rank.UNDER:
                    return 6;
                case Rank.OBER:
                    return 7;
                case Rank.KOENIG:
                    return 8;
                case Rank.ASS:
                    return 9;
                default:
                    return 1;
            }
        } else {
            return 0;
        }
    }

    public static Card fromString(String string) throws Exception {
        String[] stringArray = string.toUpperCase().split("[\s]");
        try {
            return new Card(Suit.valueOf(stringArray[0].strip()), Rank.valueOf(stringArray[1].strip()));
        } catch (Exception e) {
            throw new Exception(string + " ist keine Karte.");
        }
    }

    // for sorting Decks
    public int getStrength(Suit trump) {
        return getStrength(this.suit, trump);
    }

    // for determining roundWinner
    public int getStrength(Deck playedCards, Suit trump) {
        return getStrength(playedCards.getCards()[0].suit, trump);
    }

    public boolean equals(Card other) {
        return this.suit == other.suit && this.rank == other.rank;
    }

    @Override
    public String toString() {
        return "[" + this.suit + " " + this.rank + "]";
    }
}