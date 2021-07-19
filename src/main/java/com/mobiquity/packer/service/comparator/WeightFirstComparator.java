package com.mobiquity.packer.service.comparator;

import com.mobiquity.packer.persistance.Item;

/**
 * Comparator to sort item by weight ascending.
 */
public class WeightFirstComparator implements ItemComparator {

  @Override
  public int compare(Item item1, Item item2) {
    return Double.compare(item2.getWeight(), item1.getWeight());
  }
}
