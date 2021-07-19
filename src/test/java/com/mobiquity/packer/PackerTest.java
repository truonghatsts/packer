package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.controller.Packer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PackerTest {

  @Test
  void givenValidData_thenProcessSuccessfully() throws Exception {

    var input = getAbsolutePath("example_input");
    var actual = Packer.pack(input);
    var output = getAbsolutePath("example_output");
    var split = actual.split(System.lineSeparator());
    int i = 0;
    try (var reader = new BufferedReader(new FileReader(output))) {
      var line = reader.readLine();
      while (line != null) {
        assertEquals(line, split[i]);
        line = reader.readLine();
        i++;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void givenInvalidData_thenThrowException() throws Exception {

    final var ioException = assertThrows(APIException.class,
            () -> Packer.pack("invalid_file"));
    assertEquals("Cannot open file", ioException.getMessage());

    final var invalidPackageException = assertThrows(APIException.class,
            () -> Packer.pack(getAbsolutePath("invalid_package.inp")));
    assertTrue(invalidPackageException.getMessage().startsWith("Cannot parse data"));

    final var invalidItemException = assertThrows(APIException.class,
            () -> Packer.pack(getAbsolutePath("invalid_item.inp")));
    assertTrue(invalidItemException.getMessage().startsWith("Cannot parse data"));

    final var packageWeightConstraintException = assertThrows(APIException.class,
            () -> Packer.pack(getAbsolutePath("package_weight_constraint.inp")));
    assertTrue(packageWeightConstraintException.getMessage().startsWith("Cannot process data"));

    final var numberOfItemConstraintException = assertThrows(APIException.class,
            () -> Packer.pack(getAbsolutePath("number_of_items_constraint.inp")));
    assertTrue(numberOfItemConstraintException.getMessage().startsWith("Cannot process data"));

    final var itemWeightConstraintException = assertThrows(APIException.class,
            () -> Packer.pack(getAbsolutePath("item_weight_constraint.inp")));
    assertTrue(itemWeightConstraintException.getMessage().startsWith("Cannot process data"));

    final var itemCostConstraintException = assertThrows(APIException.class,
            () -> Packer.pack(getAbsolutePath("item_cost_constraint.inp")));
    assertTrue(itemCostConstraintException.getMessage().startsWith("Cannot process data"));
  }

  private String getAbsolutePath(String filename) throws URISyntaxException {
    URL res = getClass().getClassLoader().getResource(filename);
    File file = Paths.get(res.toURI()).toFile();
    return file.getAbsolutePath();
  }
}