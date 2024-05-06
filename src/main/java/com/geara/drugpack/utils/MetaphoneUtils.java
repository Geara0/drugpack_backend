package com.geara.drugpack.utils;

import org.apache.commons.codec.language.Metaphone;

public class MetaphoneUtils {
  private static final Metaphone metaphone = new Metaphone();

  public static String generateMetaphone(String input) {
    return metaphone.encode(input);
  }
}
