package com.mobiquity.packer.service.parser;

import com.mobiquity.exception.ParsingException;

/**
 * Interface to parse domain model from String.
 *
 * @param <T> domain model.
 */
public interface Parsable<T> {
  T parse(String data) throws ParsingException;
}
