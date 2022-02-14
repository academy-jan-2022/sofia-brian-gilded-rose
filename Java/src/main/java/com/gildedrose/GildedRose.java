package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            decreaseSellIn(item);
            if (isBackstagePass(item) || isAgedBrie(item)) {
                item.quality =  increaseQuality(item);
            }
            item.quality =  downgradeQuality(item);
        }
    }

    private boolean isBackstagePass(Item item) {
       return item.name.equals("Backstage passes to a TAFKAL80ETC concert");
    }

    private boolean isAgedBrie(Item item) {
       return item.name.equals("Aged Brie");
    }

    private boolean isSulfuras(Item item) {
       return item.name.equals("Sulfuras, Hand of Ragnaros");
    }

    private void decreaseSellIn(Item item) {
        if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
            item.sellIn = item.sellIn - 1;
        }
    }

    private int increaseQuality(Item item) {
        boolean brieIsAged = isAgedBrie(item) && item.sellIn < 0;
        boolean ticketsAreInDemand = isBackstagePass(item) && item.sellIn < 11;
        boolean tickersAreInSuperDemand = isBackstagePass(item) && item.sellIn < 6 && item.sellIn > 0;


        int value = 1;
        if (brieIsAged || ticketsAreInDemand) {
            value = 2;
        }
        if (tickersAreInSuperDemand) {
            value = 3;
        }

       int qualityMaximum = isSulfuras(item) ? 80 : 50;

       return Integer.min(qualityMaximum, item.quality + value);
    }

    private int downgradeQuality(Item item) {
        int lossOfQuality = item.sellIn < 0 ? 2 : 1;

        if (isSulfuras(item) || isAgedBrie(item)) {
            return item.quality;
        }

        if (isBackstagePass(item)) {
            return item.sellIn < 0 ? 0 : item.quality;
        }

        return Integer.max(0, item.quality - lossOfQuality);
    }
}
