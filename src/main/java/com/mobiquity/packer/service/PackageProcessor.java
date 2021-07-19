package com.mobiquity.packer.service;

import com.mobiquity.exception.ParsingException;
import com.mobiquity.exception.ProcessingException;
import com.mobiquity.packer.service.comparator.ItemComparator;
import com.mobiquity.packer.persistance.Item;
import com.mobiquity.packer.persistance.Pack;
import com.mobiquity.packer.service.parser.ItemParser;
import com.mobiquity.packer.service.parser.PackageParser;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * Processor to pack items into package.
 */
public class PackageProcessor {

  private final ItemComparator itemComparator;
  private final PackageParser packageParser;
  private final Validator validator;
  private TreeMap<BigDecimal, Deque<Item>> processingMap;

  /**
   * Constructor the package processor.
   *
   * @param itemComparator item comparator.
   */
  public PackageProcessor(ItemComparator itemComparator) {
    this.itemComparator = itemComparator;
    var itemParser = new ItemParser();
    packageParser = new PackageParser(itemParser);
    processingMap = new TreeMap<>();
    var factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   * Parse the line into domain model and try to pack items into the package and record the total
   * cost for each attempt.
   *
   * Then return list of items of the best attempt.
   *
   * @param line raw data.
   * @return @{String} list of items.
   * @throws ParsingException when could not parse the data.
   */
  public String process(String line) throws ProcessingException, ParsingException {
    var pack = packageParser.parse(line);
    validatePackage(pack);
    pack.getItems().sort(itemComparator);
    processingMap = new TreeMap<>();
    for (var i = 0; i < pack.getItems().size(); i++) {
      tryPacking(0, BigDecimal.ZERO, pack, new ArrayDeque<>(), i);
    }
    if (processingMap.isEmpty()) {
      return "-";
    } else {
      var result = new StringBuilder();
      final var items = processingMap.get(processingMap.lastKey());
      while (!items.isEmpty()) {
        result.insert(0, items.pop().getIndex() + ",");
      }
      return result.substring(0, result.length() - 1);
    }
  }

  /**
   * Recursively try to pack items and record the total cost.
   */
  private void tryPacking(double currentWeight, BigDecimal currentCost, Pack pack,
                          Deque<Item> packedItems, int index) {
    final var item = pack.getItems().get(index);
    if (currentWeight + item.getWeight() <= pack.getWeight()) {
      packedItems.push(item);
      if (index < pack.getItems().size() - 1) {
        for (int i = index + 1; i < pack.getItems().size(); i++) {
          tryPacking(currentWeight + item.getWeight(), currentCost.add(item.getCost()), pack,
                  packedItems, i);
        }
      } else {
        final var cost = currentCost.add(item.getCost());
        if (!processingMap.containsKey(cost)) {
          processingMap.put(currentCost.add(item.getCost()), new ArrayDeque<>(packedItems));
        }
      }
      packedItems.pop();
    } else if (!packedItems.isEmpty() && !processingMap.containsKey(currentCost)) {
        processingMap.put(currentCost, new ArrayDeque<>(packedItems));
    }
  }

  /**
   * Validate the package with constraint defined in Pack model and Item model.
   *
   * @param pack package
   * @throws ProcessingException when failed any validation.
   */
  private void validatePackage(Pack pack) throws ProcessingException {
    final var validateResult = validator.validate(pack);
    if (!validateResult.isEmpty()) {
      var validationError = new StringBuilder();
      for (ConstraintViolation<Pack> violation : validateResult) {
        validationError.append(violation.getPropertyPath())
                .append(" => ")
                .append(violation.getMessage())
                .append(System.lineSeparator());
      }
      throw new ProcessingException(
              "Failed validation for these fields\n" + validationError);
    }
  }
}
