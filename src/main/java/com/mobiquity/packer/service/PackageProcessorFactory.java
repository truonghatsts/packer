package com.mobiquity.packer.service;

import com.mobiquity.exception.ProcessingException;
import com.mobiquity.packer.service.comparator.CostFirstComparator;
import com.mobiquity.packer.service.comparator.CostFirstThenWeightComparator;
import com.mobiquity.packer.service.comparator.WeightFirstComparator;

/**
 * The factory to Initialize the package processor by priority.
 */
public class PackageProcessorFactory {

  /**
   * Priority enum of packing items into the package.
   */
  public enum Priority {
    COST_FIRST, WEIGHT_FIRST, COST_FIRST_THEN_WEIGHT
  }

  private PackageProcessorFactory() {

  }

  /**
   * Initialize the package processor by priority.
   *
   * @param priority priority.
   * @return @{PackageProcessor}}
   *
   * @throws ProcessingException when can't initialize.
   */
  public static PackageProcessor getProcessor(Priority priority) throws ProcessingException {
    switch (priority) {
      case COST_FIRST:
        return new PackageProcessor(new CostFirstComparator());
      case WEIGHT_FIRST:
        return new PackageProcessor(new WeightFirstComparator());
      case COST_FIRST_THEN_WEIGHT:
        return new PackageProcessor(new CostFirstThenWeightComparator());
      default:
        throw new ProcessingException("Unsupported priority");
    }
  }
}
