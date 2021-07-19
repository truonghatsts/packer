package com.mobiquity.exception;

/**
 * Exception thrown when could not parse data.
 */
public class ParsingException extends Exception {

  public ParsingException(String message, Exception e) {
    super(message, e);
  }

  public ParsingException(String message) {
    super(message);
  }
}
