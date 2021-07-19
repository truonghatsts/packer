package com.mobiquity.packer.controller;

import com.mobiquity.exception.APIException;
import com.mobiquity.exception.ParsingException;
import com.mobiquity.exception.ProcessingException;
import com.mobiquity.packer.service.PackageProcessorFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implementation to pack the package of items.
 */
public class Packer {

  private Packer() {
  }

  /**
   * Stream the data from file and process each line.
   *
   * @param filePath file path to the data file.
   * @return lines of packing result.
   * @throws APIException when cannot pack.
   */
  public static String pack(String filePath) throws APIException {

    var result = new StringBuilder();
    try (var reader = new BufferedReader(new FileReader(filePath))) {
      final var packageProcessor = PackageProcessorFactory.getProcessor(
              PackageProcessorFactory.Priority.COST_FIRST_THEN_WEIGHT);
      var line = reader.readLine();
      while (line != null) {
        var packingResult = packageProcessor.process(line);
        result.append(packingResult)
                .append(System.lineSeparator());
        line = reader.readLine();
      }
    } catch (IOException e) {
      throw new APIException("Cannot open file", e);
    } catch (ParsingException e) {
      throw new APIException("Cannot parse data", e);
    } catch (ProcessingException e) {
      throw new APIException("Cannot process data", e);
    }
    return result.toString();
  }
}
