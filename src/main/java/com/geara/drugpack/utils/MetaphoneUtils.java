package com.geara.drugpack.utils;

import org.apache.commons.codec.language.DoubleMetaphone;

public class MetaphoneUtils {
  private static final DoubleMetaphone metaphone = new DoubleMetaphone();

  public static String generateMetaphone(String input) {
    return metaphone.encode(input);
  }
}
