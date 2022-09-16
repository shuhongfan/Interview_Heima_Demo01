package day01.sort;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StableVsUnstable {

    public static void main(String[] args) {
        System.out.println("=================不稳定================");
        Card[] cards = getStaticCards();
        System.out.println(Arrays.toString(cards));
        selection(cards, Comparator.comparingInt((Card a) -> a.sharpOrder).reversed());
        System.out.println(Arrays.toString(cards));
        selection(cards, Comparator.comparingInt((Card a) -> a.numberOrder).reversed());
        System.out.println(Arrays.toString(cards));

        System.out.println("=================稳定=================");
        cards = getStaticCards();
        System.out.println(Arrays.toString(cards));
        bubble(cards, Comparator.comparingInt((Card a) -> a.sharpOrder).reversed());
        System.out.println(Arrays.toString(cards));
        bubble(cards, Comparator.comparingInt((Card a) -> a.numberOrder).reversed());
        System.out.println(Arrays.toString(cards));
    }

    public static void bubble(Card[] a, Comparator<Card> comparator) {
        int n = a.length - 1;
        while (true) {
            int last = 0; // 表示最后一次交换索引位置
            for (int i = 0; i < n; i++) {
                if (comparator.compare(a[i], a[i + 1]) > 0) {
                    swap(a, i, i + 1);
                    last = i;
                }
            }
            n = last;
            if (n == 0) {
                break;
            }
        }
    }

    private static void selection(Card[] a, Comparator<Card> comparator) {
        for (int i = 0; i < a.length - 1; i++) {
            // i 代表每轮选择最小元素要交换到的目标索引
            int s = i; // 代表最小元素的索引
            for (int j = s + 1; j < a.length; j++) {
                if (comparator.compare(a[s], a[j]) > 0) {
                    s = j;
                }
            }
            if (s != i) {
                swap(a, s, i);
            }
        }
    }

    public static void swap(Card[] a, int i, int j) {
        Card t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    enum Sharp {
        diamond, club, heart, spade, black, red
    }
    static Card[] getStaticCards() {
        List<Card> list = new ArrayList<>();
        Card[] copy = Arrays.copyOfRange(Card.cards, 2, 13 * 4 + 2);
        list.add(copy[7]);
        list.add(copy[12]);
        list.add(copy[12+13]);
        list.add(copy[10]);
        list.add(copy[9]);
        list.add(copy[9+13]);
        return list.toArray(new Card[0]);
    }


    static Map<String, Integer> map = Map.ofEntries(
            Map.entry("RJ", 16),
            Map.entry("BJ", 15),
            Map.entry("A", 14),
            Map.entry("K", 13),
            Map.entry("Q", 12),
            Map.entry("J", 11),
            Map.entry("10", 10),
            Map.entry("9", 9),
            Map.entry("8", 8),
            Map.entry("7", 7),
            Map.entry("6", 6),
            Map.entry("5", 5),
            Map.entry("4", 4),
            Map.entry("3", 3),
            Map.entry("2", 2)
    );

    static class Card {
        private Sharp sharp;
        private final String number;
        private final int numberOrder;
        private final int sharpOrder;

        public Card(Sharp sharp, String number) {
            this.sharp = sharp;
            this.number = number;
            this.numberOrder = map.get(number);
            this.sharpOrder = sharp.ordinal();
        }

        private static final Card[] cards;

        static {
            cards = new Card[54];
            Sharp[] sharps = {Sharp.spade, Sharp.heart, Sharp.club, Sharp.diamond};
            String[] numbers = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
            int idx = 2;
            for (Sharp sharp : sharps) {
                for (String number : numbers) {
                    cards[idx++] = new Card(sharp, number);
                }
            }
            cards[0] = new Card(Sharp.red, "RJ");
            cards[1] = new Card(Sharp.black, "BJ");
        }

        @Override
        public String toString() {
            return switch (sharp) {
                case heart -> "[\033[31m" + "♥" + number + "\033[0m]";
                case diamond -> "[\033[31m" + "♦" + number + "\033[0m]";
                case spade -> "[\033[30m" + "♠" + number + "\033[0m]";
                case club -> "[\033[30m" + "♣" + number + "\033[0m]";
                case red -> "[\033[31m" + "\uD83C\uDFAD" + "\033[0m]";
                case black -> "[\033[30m" + "\uD83C\uDFAD" + "\033[0m]";
            };
        }
    }
}
