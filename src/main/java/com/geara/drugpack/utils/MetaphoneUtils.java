package com.geara.drugpack.utils;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class MetaphoneUtils {
  private static final String ALPHABET = "ОЕАИУЭЮЯПСТРКЛМНБВГДЖЗЙФХЦЧШЩЁЫ";
  private static final String VOICED_CONSONANTS = "БЗДВГ";
  private static final String VOICELESS_CONSONANTS = "ПСТФК";
  private static final String WEAKENING_CONSONANTS = "ПСТКБВГДЖЗФХЦЧШЩ";
  private static final String FROM_VOWELS = "ОЮЕЭЯЁЫ";
  private static final String TO_VOWELS = "АУИИАИА";

  public static String generateMetaphone(@NotNull List<String> input) {
    return generateMetaphone(String.join(" ", input), TransliterationType.sound);
  }

  public static String generateMetaphone(@NotNull String input) {
    return generateMetaphone(input, TransliterationType.sound);
  }

  public static String generateMetaphone(@NotNull String input, @NotNull TransliterationType type) {
    if (input == null || type == null) throw new IllegalArgumentException();

    final var transliterated =
        switch (type) {
          case qwerty -> TransliterationUtils.qwertyReplace(input);
          case sound -> TransliterationUtils.transliterate(input);
        };

    return generateMetaphoneFromString(transliterated);
  }

  private static String generateMetaphoneFromList(@NotNull List<String> input) {
    if (input == null) throw new IllegalArgumentException();

    final var res = new StringBuilder();
    for (var i = 0; i < input.size(); i++) {
      final var word = input.get(i);
      if (word == null) continue;

      final var metaphone = generateMetaphoneFromString(word).trim();
      if (metaphone.isEmpty()) continue;
      res.append(metaphone);
      if (i != input.size() - 1) res.append(" ");
    }

    return res.toString();
  }

  private static String generateMetaphoneFromString(@NotNull String input) {
    if (input == null) throw new IllegalArgumentException();

    if (input.contains(" ")) return generateMetaphoneFromList(List.of(input.split(" ")));

    final var word = input.toUpperCase();

    var compressed = new StringBuilder();

    for (final var c : word.chars().mapToObj(e -> (char) e).toArray()) {
      if (!ALPHABET.contains(String.valueOf(c))) continue;
      compressed.append(c);
    }

    if (compressed.isEmpty()) return "";

    if (compressed.length() > 6) {
      final var left = compressed.substring(0, compressed.length() - 6);
      final var right =
          switch (compressed.substring(compressed.length() - 6)) {
            case "ОВСКИЙ", "ОВСКАЯ" -> "!";
            case "ЕВСКИЙ", "ЕВСКАЯ" -> "@";
            default -> compressed.substring(compressed.length() - 6);
          };

      compressed = new StringBuilder(left + right);
    }

    if (compressed.length() > 4) {
      final var left = compressed.substring(0, compressed.length() - 4);
      final var right =
          switch (compressed.substring(compressed.length() - 4)) {
            case "ИЕВА", "ЕЕВА" -> "9";
            default -> compressed.substring(compressed.length() - 4);
          };

      compressed = new StringBuilder(left + right);
    }

    if (compressed.length() > 3) {
      final var left = compressed.substring(0, compressed.length() - 3);
      final var right =
          switch (compressed.substring(compressed.length() - 3)) {
            case "ОВА", "ЕВА" -> "9";
            case "ИНА" -> "1";
            case "ИЕВ", "ЕЕВ" -> "4";
            case "НКО" -> "3";
            case "АТЬ", "ЯТЬ", "ОТЬ", "ЕТЬ", "УТЬ" -> "#";
            case "ЕШЬ", "ИШЬ", "ЕТЕ", "ИТЕ" -> "$";
            case "АЛА", "ЯЛА", "АЛИ", "ЯЛИ", "УЛА", "УЛИ" -> "%";
            case "ОЛА", "ЕЛА", "ОЛИ", "ЕЛИ" -> "^";

            default -> compressed.substring(compressed.length() - 3);
          };

      compressed = new StringBuilder(left + right);
    }

    if (compressed.length() > 2) {
      final var left = compressed.substring(0, compressed.length() - 2);
      final var right =
          switch (compressed.substring(compressed.length() - 2)) {
            case "ОВ", "ЕВ", "ОЕ", "ЕЕ", "ОЙ" -> "4";
            case "АЯ", "ЯЯ" -> "6";
            case "ИЙ", "ЫЙ", "ИЯ" -> "7";
            case "ЫХ", "ИХ" -> "5";
            case "ИН" -> "8";
            case "ИК", "ЕК" -> "2";
            case "УК", "ЮК" -> "0";
            case "ЕМ", "ИМ", "ЕТ", "ИТ" -> "$";
            case "УТ", "ЮТ", "АТ", "ЯТ", "УЛ", "ЮЛ", "АЛ", "ЯЛ" -> "%";
            case "ОЛ", "ЕЛ" -> "^";

            default -> compressed.substring(compressed.length() - 2);
          };

      compressed = new StringBuilder(left + right);
    }

    int B = VOICED_CONSONANTS.indexOf(compressed.charAt(compressed.length() - 1));
    if (B > -1) {
      compressed =
          new StringBuilder(
              compressed.substring(0, compressed.length() - 1) + VOICELESS_CONSONANTS.charAt(B));
    }

    String prev = " ";
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < compressed.length(); i++) {
      final var curr = String.valueOf(compressed.charAt(i));

      var prevFromIndex = FROM_VOWELS.indexOf(curr);
      if (prevFromIndex > -1) {
        // if curr is vowel
        if (prev.equals("Й") && (curr.equals("О") || curr.equals("Е"))) {
          // ЙО/ЙЕ -> ИО/ИЕ
          prev = "И";
          compressed.setLength(compressed.length() - 1);
          compressed.append(prev);
        } else if (!curr.equals(prev)) {
          // if curr != prev -> append to res
          res.append(TO_VOWELS.charAt(prevFromIndex));
        }
      } else {
        // if curr is consonant
        if (!curr.equals(prev) && WEAKENING_CONSONANTS.contains(curr)) {
          // if curr is weakening && prev is voiced -> make prev voiceless
          prevFromIndex = VOICED_CONSONANTS.indexOf(prev);
          if (prevFromIndex > -1) {
            prev = String.valueOf(VOICELESS_CONSONANTS.charAt(prevFromIndex));
            res.setLength(res.length() - 1);
            res.append(prev);
          }
        }

        // if curr != prev - add to res
        if (!curr.equals(prev)) {
          res.append(curr);
        }
      }

      prev = curr;
    }

    return res.toString();
  }
}
