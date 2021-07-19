package com.mobiquity.packer.service.parser;

import com.mobiquity.exception.ParsingException;
import com.mobiquity.packer.persistance.Item;
import com.mobiquity.packer.persistance.Pack;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementation to parse String to Package.
 */
public class PackageParser implements Parsable<Pack> {

  private static final Pattern PACKAGE_PATTERN = Pattern.compile("(.*):(.*)");
  private static final Pattern ITEM_LIST_PATTERN = Pattern.compile("(\\(.*?\\))");
  private final ItemParser itemParser;

  public PackageParser(ItemParser itemParser) {
    this.itemParser = itemParser;
  }

  @Override
  public Pack parse(String data) throws ParsingException {
    var matcher = PACKAGE_PATTERN.matcher(data);
    if (matcher.find()) {
      try {
        var weight = Double.parseDouble(matcher.group(1));
        var itemList = matcher.group(2);
        var itemMatcher = ITEM_LIST_PATTERN.matcher(itemList);
        List<Item> items = new ArrayList<>();
        while (itemMatcher.find()) {
          Item item;
          try {
            item = itemParser.parse(itemMatcher.group(1));
          } catch (ParsingException e) {
            throw new ParsingException("Could not parse this item: " + itemMatcher.group(1), e);
          }
          items.add(item);
        }
        var pack = new Pack();
        pack.setItems(items);
        pack.setWeight(weight);
        return pack;
      } catch (Exception e) {
        throw new ParsingException("Could not parse this package", e);
      }
    }
    throw new ParsingException("Could not parse this package: " + data);
  }
}
