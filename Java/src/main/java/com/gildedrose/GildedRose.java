package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            decreaseSellIn(item);

            if (!isAgedBrie(item)
                && !isBackstagePass(item)) {
                        item.quality = downgradeQuality(item);
            } else {
                    item.quality = increaseQuality(item);

                    if (isBackstagePass(item)) {
                        if (item.sellIn < 11) {
                                item.quality = increaseQuality(item);
                        }
                    }
            }
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
        int value = isAgedBrie(item) && item.sellIn < 0 ? 2 : 1;
       int qualityMaximum = isSulfuras(item) ? 80 : 50;

       return Integer.min(qualityMaximum, item.quality + value);
    }

    private int downgradeQuality(Item item) {
        int lossOfQuality = item.sellIn < 0 ? 2 : 1;

        if (isSulfuras(item) || isAgedBrie(item)) {
            return item.quality;
        }

        if (isBackstagePass(item) && item.sellIn < 0) {
            return 0;
        }

        return Integer.max(0, item.quality - lossOfQuality);
    }
}
