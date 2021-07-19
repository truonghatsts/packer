package com.mobiquity.packer.service.comparator;

import com.mobiquity.packer.persistance.Item;

/**
 * Comparator to sort by cost descending order.
 */
public class CostFirstComparator implements ItemComparator {

  @Override
  public int compare(Item item1, Item item2) {
    return item2.getCost().compareTo(item1.getCost());
  }
}
