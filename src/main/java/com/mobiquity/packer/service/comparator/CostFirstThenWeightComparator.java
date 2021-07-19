package com.mobiquity.packer.service.comparator;

import com.mobiquity.packer.persistance.Item;

/**
 * Comparator to sort by cost descending and weight ascending.
 */
public class CostFirstThenWeightComparator implements ItemComparator {

  @Override
  public int compare(Item item1, Item item2) {
    var result = item2.getCost().compareTo(item1.getCost());
    if (result == 0) {
      if (item1.getWeight() <= item2.getWeight()) {
        return -1;
      } else {
        return 1;
      }
    } else {
      return result;
    }
  }
}
