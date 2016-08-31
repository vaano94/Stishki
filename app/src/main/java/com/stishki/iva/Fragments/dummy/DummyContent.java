package com.stishki.iva.Fragments.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    public static ArrayList<String> poems = new ArrayList<>();

    public static ArrayList<String> poemDescriptions = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {

        // Add 3 sample items.
        addItem(new DummyItem("1", "Item 1"));
        addItem(new DummyItem("2", "Item 2"));
        addItem(new DummyItem("3", "Item 3"));

        poems.add("Пирожок");
        poems.add("Порошок");
        poems.add("Своё");


        poemDescriptions.add(
                "Что такое пирожок?\n" +
                        "\n" +
                        "Это четверостишие, написанное четырёхстопным ямбом, строчными буквами," +
                        "без знаков препинания, с отсутствием явных рифм, но воистину со смыслом! \n" +
                        "Пример: \n" +
                        "зимой дороги убирают \n" +
                        "под грязный снег и гололед \n" +
                        "ну а когда зима проходит\n" +
                        "дороги снова достают");

        poemDescriptions.add("Что такое пирожок?\n" +
                        "\n" +
                        "Порошки — все более завоёвывающая популярность разновидность «пирожков», " +
                        "отличающаяся от основного жанра тем, " +
                        "что 2 и 4 строки в этих четверостишиях рифмуются, " +
                        "размер по слогам — 9, 8, 9, 2, " +
                        "и самая короткая, последняя двусложная строчка, " +
                        "нередко является самой важной. \n" +
                        "Пример: \n" +
                        "а если б я была царица\n" +
                        "то я б для батюшки царя\n" +
                        "царь не дослушал и женился\n" +
                        "а зря"
                            );
        poemDescriptions.add("Не стоит быть ограниченными рамками какого-то жанра,\n пишите что хотите!");

    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String content;

        public DummyItem(String id, String content) {
            this.id = id;
            this.content = content;


        }

        @Override
        public String toString() {
            return content;
        }
    }
}
