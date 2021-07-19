package com.mobiquity.packer.service.parser;

import com.mobiquity.exception.ParsingException;
import com.mobiquity.packer.persistance.Item;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Implementation to parse the String to Item.
 */
public class ItemParser implements Parsable<Item> {

  private static final Pattern ITEM_PATTERN =
          Pattern.compile("(\\d+),(\\d+(?:\\.\\d+)?),.(\\d+(?:\\.\\d+)?)");

  @Override
  public Item parse(String data) throws ParsingException {
    var matcher = ITEM_PATTERN.matcher(data);
    if (matcher.find()) {
      var index = Integer.parseInt(matcher.group(1));
      var weight = Double.parseDouble(matcher.group(2));
      var cost = BigDecimal.valueOf(Double.parseDouble(matcher.group(3)));
      var item = new Item();
      item.setIndex(index);
      item.setWeight(weight);
      item.setCost(cost);
      return item;
    }
    throw new ParsingException("Could not parse this item " + data);
  }
}
