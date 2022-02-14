package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            if (!item.name.equals("Aged Brie")
                && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (item.quality > 0) {
                    if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                        item.quality = downgradeQuality(item);
                    }
                }
            } else {
                    item.quality = increaseQuality(item);

                    if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (item.sellIn < 11) {
                                item.quality = increaseQuality(item);
                        }
                    }
            }

            decreaseSellIn(item);

            if (item.sellIn < 0) {
                if (item.name.equals("Aged Brie")) {
                    item.quality = increaseQuality(item);
                }

                    if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        item.quality = downgradeQuality(item);
                    } else {
                        item.quality = 0;
                }
            }
        }
    }

    private void decreaseSellIn(Item item) {
        if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
            item.sellIn = item.sellIn - 1;
        }
    }

    private int increaseQuality(Item item) {
       int qualityMaximum = item.name.equals("Sulfuras, Hand of Ragnaros") ? 80 : 50;

       return Integer.min(qualityMaximum, item.quality + 1);
    }

    private int downgradeQuality(Item item) {
        if (item.name.equals("Sulfuras, Hand of Ragnaros") || item.name.equals("Aged Brie")) {
            return item.quality;
        }
        return Integer.max(0, item.quality - 1);
    }
}
