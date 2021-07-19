package com.mobiquity.exception;

/**
 * Exception thrown when can not process.
 */
public class ProcessingException extends Exception {

  public ProcessingException(String message, Exception e) {
    super(message, e);
  }

  public ProcessingException(String message) {
    super(message);
  }
}
