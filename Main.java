package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class Main {

    public static class Card {

        private final List<String> suits = Arrays.asList("♠", "♥", "♦", "♣");
        private final List<String> values = IntStream.rangeClosed(1, 10).boxed().map(String::valueOf).collect(toList());

        private final String cardSuit;
        private final String cardValue;

        public Card(final Random random) {
            values.addAll(Arrays.asList("J", "Q", "K", "A"));
            this.cardSuit = suits.get(random.nextInt(4));
            this.cardValue = values.get(random.nextInt(14));
        }

        public String getCardSuit() {
            return cardSuit;
        }

        public String getCardValue() {
            return cardValue;
        }

    }


    public static void main(String[] args) {

        final Random random = new Random();
        final Card dealersFirst = new Card(random);
        final Card dealersSecond = new Card(random);
        final Card playersFirst = new Card(random);
        final Card playersSecond = new Card(random);
        int playerTotal = getCardValue(playersFirst.getCardValue());
        playerTotal += getCardValue(playersSecond.getCardValue());

        int dealerTotal = getCardValue(dealersFirst.getCardValue());
        dealerTotal += getCardValue(dealersSecond.getCardValue());

        System.out.println("***** SIMPLE BLACKJACK GAME *****");
        System.out.println(format("***** DEALER IS DELT: (%s %s, * *) *****", dealersFirst.getCardValue(), dealersFirst.getCardSuit()));
        System.out.println(format("***** PLAYER IS DELT: (%s %s, %s %s) TOTAL: %s *****", playersFirst.getCardValue(), playersFirst.getCardSuit(),
                playersSecond.getCardValue(), playersSecond.getCardSuit(), playerTotal));

        if(playerTotal == 21 && dealerTotal == 21) {
            System.out.println("***** STOP ME SMEE! DONT STOP ME SMEE! ITS A DRAW SMEE!! ******");
            return;
        }

        if(playerTotal == 21) {
            System.out.println("***** BLACKJACK!!! PLAYER WINS!! ******");
            return;
        }
       
        if(dealerTotal == 21) {
            System.out.println(format("***** DEALERS HAND IS REVEALED: (%s %s, %s %s) TOTAL: %s *****",
                    dealersFirst.getCardValue(), dealersFirst.getCardSuit(), dealersSecond.getCardValue(), dealersSecond.getCardSuit(), dealerTotal));
            System.out.println("***** DEALER GOT BLACKJACK!!! PLAYER LOSES :( UNLUCKY!! ******");
            return;
        }

        final Scanner scanner = new Scanner(System.in);
        while (playerTotal <= 21) {
            final String playerOption = scanner.next();
            if (("h".equalsIgnoreCase(playerOption) || "hit".equalsIgnoreCase(playerOption))) {
                final Card additionalCard = new Card(random);
                playerTotal += getCardValue(additionalCard.getCardValue());
                System.out.println(format("***** PLAYER RECEIVES: (%s %s) TOTAL: %s *****",
                        additionalCard.getCardValue(), additionalCard.getCardSuit(), playerTotal));
                System.out.println("***** HIT (H/h) or STICK (S/s) ? *****");
            } else if ("s".equalsIgnoreCase(playerOption) || "stick".equalsIgnoreCase(playerOption)) {
                System.out.println(format("***** DEALERS HAND IS REVEALED: (%s %s, %s %s) TOTAL: %s *****",
                        dealersFirst.getCardValue(), dealersFirst.getCardSuit(), dealersSecond.getCardValue(), dealersSecond.getCardSuit(), dealerTotal));

                while (dealerTotal <= 21) {
                    if(dealerTotal <= playerTotal) {
                        final Card additionalCard = new Card(random);
                        dealerTotal += getCardValue(additionalCard.getCardValue());
                        System.out.println(format("***** DEALER RECEIVES: (%s %s) TOTAL: %s *****", additionalCard.getCardValue(),
                                additionalCard.getCardSuit(), dealerTotal));
                    } else {
                        System.out.println("***** GAME OVER DEALER WINS *****");
                        return;
                    }
                }
                System.out.println("***** YOU WIN DEALER BUST *****");
                return;
            }
        }
        System.out.println("***** GAME OVER PLAYER BUST *****");

    }


    private static int getCardValue(final String cardValue) {
        if ("K Q J".contains(cardValue)) {
            return 10;
        } else if ("A".equalsIgnoreCase(cardValue)) {
            return 11;
        } else {
            return Integer.parseInt(cardValue);
        }
    }
}
