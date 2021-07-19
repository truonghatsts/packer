package com.mobiquity.packer.persistance;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Pack {

  @NotNull
  @Min(value = 0, message = "Weight must be a positive number.")
  @Max(value = 100, message = "Weight must be less or equal to 100.")
  private double weight;
  @NotNull
  @Size(min=1, max=15, message = "Number of items must be less or equal to 15.")
  @Valid
  private List<Item> items;

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

}
