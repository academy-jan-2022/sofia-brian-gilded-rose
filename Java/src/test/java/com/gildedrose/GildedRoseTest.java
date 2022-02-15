package com.gildedrose;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void should_add_new_item_to_list() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    @Test
    void should_lower_quality_and_sell_in_of_item_after_one_day() {
        Item[] items = new Item[] { new Item("chocolate bar", 2, 2) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].quality);
        assertEquals(1, app.items[0].sellIn);
    }

    @Test
    void not_allow_quality_to_go_below_zero() {
        Item[] items = new Item[] { new Item("chocolate bar", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void should_degrade_twice_as_fast_when_sell_in_is_below_zero() {
        Item[] items = new Item[] { new Item("flowers", 1, 5) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        app.updateQuality();
        assertEquals(2, app.items[0].quality);
    }

    @Test
    void acceptence_test() {
        Item[] items = new Item[] {
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Aged Brie", 2, 0), //
            new Item("Elixir of the Mongoose", 5, 7), //
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 0, 49),
            new Item("Conjured Mana Cake", 3, 6)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        Item regularItem = app.items[0];
        assertEquals(9, regularItem.sellIn);
        assertEquals(19, regularItem.quality);

        Item agedBrie = app.items[1];
        assertEquals(1, agedBrie.sellIn);
        assertEquals(1, agedBrie.quality);

        Item sulfuras = app.items[3];
        assertEquals(0, sulfuras.sellIn);
        assertEquals(80, sulfuras.quality);

        Item expiredSulfuras = app.items[4];
        assertEquals(-1, expiredSulfuras.sellIn);
        assertEquals(80, expiredSulfuras.quality);

        Item backstagePass = app.items[5];
        Item lessThanTenDaysBackstagePass = app.items[6];
        Item lessThanFiveDaysBackstagePass = app.items[7];

        assertEquals(14, backstagePass.sellIn);
        assertEquals(21, backstagePass.quality);
        assertEquals(9, lessThanTenDaysBackstagePass.sellIn);
        assertEquals(50, lessThanTenDaysBackstagePass.quality);
        assertEquals(-1, lessThanFiveDaysBackstagePass.sellIn);
        assertEquals(0, lessThanFiveDaysBackstagePass.quality);

        Item conjured = app.items[8];
        assertEquals(2, conjured.sellIn);
        assertEquals(4, conjured.quality);
    }

    @Test
    void snapshot_test() {
        Item[] items = new Item[] {
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Aged Brie", 2, 0), //
            new Item("Elixir of the Mongoose", 5, 7), //
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 1, 49),
            new Item("Conjured Mana Cake", 3, 6) };

        GildedRose app = new GildedRose(items);

        StringBuilder result = new StringBuilder();

        IntStream.range(0, 3).forEach(i -> {
            result.append("-------- day ").append(i).append(" --------");
            result.append("\n");
            result.append("name, sellIn, quality");
            result.append("\n");
            for (Item item : items) {
                result.append(item);
                result.append("\n");
            }
            result.append("\n");
            app.updateQuality();
        });

        String expected = "-------- day 0 --------\n" +
            "name, sellIn, quality\n" +
            "+5 Dexterity Vest, 10, 20\n" +
            "Aged Brie, 2, 0\n" +
            "Elixir of the Mongoose, 5, 7\n" +
            "Sulfuras, Hand of Ragnaros, 0, 80\n" +
            "Sulfuras, Hand of Ragnaros, -1, 80\n" +
            "Backstage passes to a TAFKAL80ETC concert, 15, 20\n" +
            "Backstage passes to a TAFKAL80ETC concert, 10, 49\n" +
            "Backstage passes to a TAFKAL80ETC concert, 1, 49\n" +
            "Conjured Mana Cake, 3, 6\n" +
            "\n" +
            "-------- day 1 --------\n" +
            "name, sellIn, quality\n" +
            "+5 Dexterity Vest, 9, 19\n" +
            "Aged Brie, 1, 1\n" +
            "Elixir of the Mongoose, 4, 6\n" +
            "Sulfuras, Hand of Ragnaros, 0, 80\n" +
            "Sulfuras, Hand of Ragnaros, -1, 80\n" +
            "Backstage passes to a TAFKAL80ETC concert, 14, 21\n" +
            "Backstage passes to a TAFKAL80ETC concert, 9, 50\n" +
            "Backstage passes to a TAFKAL80ETC concert, 0, 50\n" +
            "Conjured Mana Cake, 2, 4\n" +
            "\n" +
            "-------- day 2 --------\n" +
            "name, sellIn, quality\n" +
            "+5 Dexterity Vest, 8, 18\n" +
            "Aged Brie, 0, 2\n" +
            "Elixir of the Mongoose, 3, 5\n" +
            "Sulfuras, Hand of Ragnaros, 0, 80\n" +
            "Sulfuras, Hand of Ragnaros, -1, 80\n" +
            "Backstage passes to a TAFKAL80ETC concert, 13, 22\n" +
            "Backstage passes to a TAFKAL80ETC concert, 8, 50\n" +
            "Backstage passes to a TAFKAL80ETC concert, -1, 0\n" +
            "Conjured Mana Cake, 1, 2";

        assertEquals(expected, result.toString().trim());
    }
}
