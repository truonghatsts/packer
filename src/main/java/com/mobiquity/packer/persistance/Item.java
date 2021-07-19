package com.mobiquity.packer.persistance;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Item model.
 */
public class Item {

  @Min(value = 1, message = "Index must be greater or equal to 1.")
  @Max(value = 15, message = "index must be less or equal to 15.")
  private int index;
  @Min(value = 0, message = "Weight must be a positive number.")
  @Max(value = 100, message = "Weight must be less or equal to 100.")
  private double weight;
  @NotNull
  @Min(value = 0, message = "Cost must be a positive number.")
  @Max(value = 100, message = "Cost must be less or equal to 100.")
  private BigDecimal cost;

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

}
