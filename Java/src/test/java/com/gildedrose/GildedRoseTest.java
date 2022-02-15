package com.gildedrose;

import org.junit.jupiter.api.Test;

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
    void golden_master_test() {
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
        assertEquals(2, lessThanFiveDaysBackstagePass.sellIn);
        assertEquals(4, lessThanFiveDaysBackstagePass.quality);
    }
}
