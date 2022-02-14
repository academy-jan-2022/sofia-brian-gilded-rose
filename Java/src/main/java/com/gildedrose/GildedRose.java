package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (!isAgedBrie(item)
                && !isBackstagePass(item)) {
                if (item.quality > 0) {
                    if (!isSulfuras(item)) {
                        item.quality = downgradeQuality(item);
                    }
                }
            } else {
                    item.quality = increaseQuality(item);

                    if (isBackstagePass(item)) {
                        if (item.sellIn < 11) {
                                item.quality = increaseQuality(item);
                        }
                    }
            }

            decreaseSellIn(item);

            if (item.sellIn < 0) {
                if (isAgedBrie(item)) {
                    item.quality = increaseQuality(item);
                }

                item.quality = downgradeQuality(item);
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
       int qualityMaximum = isSulfuras(item) ? 80 : 50;

       return Integer.min(qualityMaximum, item.quality + 1);
    }

    private int downgradeQuality(Item item) {
        if (isSulfuras(item) || isAgedBrie(item)) {
            return item.quality;
        }

        if (isBackstagePass(item) && item.sellIn == 0) {
            return 0;
        }

        return Integer.max(0, item.quality - 1);
    }
}
