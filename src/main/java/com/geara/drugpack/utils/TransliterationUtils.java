package com.geara.drugpack.utils;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TransliterationUtils {
  private static final Map<String, Character> translitMap = new HashMap<>();
  private static final Map<Character, Character> qwertyMap = new HashMap<>();

  static {
    translitMap.put("A", 'А');
    translitMap.put("B", 'Б');
    translitMap.put("V", 'В');
    translitMap.put("G", 'Г');
    translitMap.put("D", 'Д');
    translitMap.put("E", 'Е');
    translitMap.put("ZH", 'Ж');
    translitMap.put("Z", 'З');
    translitMap.put("I", 'И');
    translitMap.put("K", 'К');
    translitMap.put("L", 'Л');
    translitMap.put("M", 'М');
    translitMap.put("N", 'Н');
    translitMap.put("O", 'О');
    translitMap.put("P", 'П');
    translitMap.put("R", 'Р');
    translitMap.put("S", 'С');
    translitMap.put("T", 'Т');
    translitMap.put("U", 'У');
    translitMap.put("F", 'Ф');
    translitMap.put("KH", 'Х');
    translitMap.put("TS", 'Ц');
    translitMap.put("CH", 'Ч');
    translitMap.put("SH", 'Ш');
    translitMap.put("SCH", 'Щ');
    translitMap.put("Y", 'Ы');
    translitMap.put("YU", 'Ю');
    translitMap.put("YA", 'Я');
    translitMap.put("W", 'В');

    qwertyMap.put('Q', 'Й');
    qwertyMap.put('W', 'Ц');
    qwertyMap.put('E', 'У');
    qwertyMap.put('R', 'К');
    qwertyMap.put('T', 'Е');
    qwertyMap.put('Y', 'Н');
    qwertyMap.put('U', 'Г');
    qwertyMap.put('I', 'Ш');
    qwertyMap.put('O', 'Щ');
    qwertyMap.put('P', 'З');
    qwertyMap.put('[', 'Х');
    qwertyMap.put('{', 'Х');
    qwertyMap.put(']', 'Ъ');
    qwertyMap.put('}', 'Ъ');
    qwertyMap.put('A', 'Ф');
    qwertyMap.put('S', 'Ы');
    qwertyMap.put('D', 'В');
    qwertyMap.put('F', 'А');
    qwertyMap.put('G', 'П');
    qwertyMap.put('H', 'Р');
    qwertyMap.put('J', 'О');
    qwertyMap.put('K', 'Л');
    qwertyMap.put('L', 'Д');
    qwertyMap.put(':', 'Ж');
    qwertyMap.put(';', 'Ж');
    qwertyMap.put('"', 'Э');
    qwertyMap.put('\'', 'Э');
    qwertyMap.put('Z', 'Я');
    qwertyMap.put('X', 'Ч');
    qwertyMap.put('C', 'С');
    qwertyMap.put('V', 'М');
    qwertyMap.put('B', 'И');
    qwertyMap.put('N', 'Т');
    qwertyMap.put('M', 'Ь');
    qwertyMap.put('<', 'Б');
    qwertyMap.put(',', 'Б');
    qwertyMap.put('>', 'Ю');
    qwertyMap.put('.', 'Ю');
  }

  public static @NotNull String qwertyReplace(@NotNull String input) {
    if (input == null) throw new IllegalArgumentException();

    StringBuilder sb = new StringBuilder();
    int i = 0;
    while (i < input.length()) {
      char c = input.charAt(i);

      if (Character.isWhitespace(c)) {
        sb.append(c);
        i++;
        continue;
      }

      if (qwertyMap.containsKey(Character.toUpperCase(c))) {
        final var isLower = Character.toLowerCase(c) == c;
        final var res =
            isLower
                ? Character.toLowerCase(qwertyMap.get(Character.toUpperCase(c)))
                : Character.toUpperCase(qwertyMap.get(Character.toUpperCase(c)));

        sb.append(res);
      } else {
        sb.append(c);
      }

      i++;
    }
    return sb.toString();
  }

  public static @NotNull String transliterate(@NotNull String input) {
    if (input == null) throw new IllegalArgumentException();

    StringBuilder sb = new StringBuilder();
    int i = 0;
    while (i < input.length()) {
      char c = input.charAt(i);

      if (Character.isWhitespace(c)) {
        sb.append(c);
        i++;
        continue;
      }

      if (i + 2 < input.length()) {
        final var triple = input.substring(i, i + 3);
        if (translitMap.containsKey(triple.toUpperCase())) {
          final var isLower = triple.toLowerCase().equals(triple);
          final var res =
              isLower
                  ? Character.toLowerCase(translitMap.get(triple.toUpperCase()))
                  : Character.toUpperCase(translitMap.get(triple.toUpperCase()));

          sb.append(res);
          i += 2;
          continue;
        }
      }

      if (i + 1 < input.length()) {
        final var pair = input.substring(i, i + 2);
        if (translitMap.containsKey(pair.toUpperCase())) {
          final var isLower = pair.toLowerCase().equals(pair);
          final var res =
              isLower
                  ? Character.toLowerCase(translitMap.get(pair.toUpperCase()))
                  : Character.toUpperCase(translitMap.get(pair.toUpperCase()));

          sb.append(res);
          i += 2;
          continue;
        }
      }

      String one = input.substring(i, i + 1);

      if (translitMap.containsKey(one.toUpperCase())) {
        final var isLower = one.toLowerCase().equals(one);
        final var res =
            isLower
                ? Character.toLowerCase(translitMap.get(one.toUpperCase()))
                : Character.toUpperCase(translitMap.get(one.toUpperCase()));

        sb.append(res);
      } else {
        sb.append(c);
      }

      i++;
    }
    return sb.toString();
  }
}
