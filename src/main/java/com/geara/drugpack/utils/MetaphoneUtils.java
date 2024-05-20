package com.geara.drugpack.utils;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class MetaphoneUtils {
  public static String generateMetaphone(@NotNull List<String> input) {
    final var res = new StringBuilder();
    for (var i = 0; i < input.size(); i++) {
      final var word = input.get(i);
      if (word == null) continue;

      final var metaphone = generateMetaphone(word).trim();
      if (metaphone.isEmpty()) continue;
      res.append(metaphone);
      if (i != input.size() - 1) res.append(" ");
    }

    return res.toString();
  }

  public static String generateMetaphone(@NotNull String input) {
    if (input.contains(" ")) return generateMetaphone(List.of(input.split(" ")));

    final var alf = "ОЕАИУЭЮЯПСТРКЛМНБВГДЖЗЙФХЦЧШЩЁЫ";
    final var cns1 = "БЗДВГ";
    final var cns2 = "ПСТФК";
    final var cns3 = "ПСТКБВГДЖЗФХЦЧШЩ";
    final var ch = "ОЮЕЭЯЁЫ";
    final var ct = "АУИИАИА";
    final var W = input.toUpperCase();

    var S = new StringBuilder();

    for (final var c : W.chars().mapToObj(e -> (char) e).toArray()) {
      if (!alf.contains(String.valueOf(c))) continue;
      S.append(c);
    }

    if (S.isEmpty()) return "";

    if (S.length() > 6) {
      final var left = S.substring(0, S.length() - 6);
      final var right =
          switch (S.substring(S.length() - 6)) {
            case "ОВСКИЙ", "ОВСКАЯ" -> "!";
            case "ЕВСКИЙ", "ЕВСКАЯ" -> "@";
            default -> S.substring(S.length() - 6);
          };

      S = new StringBuilder(left + right);
    }

    if (S.length() > 4) {
      final var left = S.substring(0, S.length() - 4);
      final var right =
          switch (S.substring(S.length() - 4)) {
            case "ИЕВА", "ЕЕВА" -> "9";
            default -> S.substring(S.length() - 4);
          };

      S = new StringBuilder(left + right);
    }

    if (S.length() > 3) {
      final var left = S.substring(0, S.length() - 3);
      final var right =
          switch (S.substring(S.length() - 3)) {
            case "ОВА", "ЕВА" -> "9";
            case "ИНА" -> "1";
            case "ИЕВ", "ЕЕВ" -> "4";
            case "НКО" -> "3";
            case "АТЬ", "ЯТЬ", "ОТЬ", "ЕТЬ", "УТЬ" -> "#";
            case "ЕШЬ", "ИШЬ", "ЕТЕ", "ИТЕ" -> "$";
            case "АЛА", "ЯЛА", "АЛИ", "ЯЛИ", "УЛА", "УЛИ" -> "%";
            case "ОЛА", "ЕЛА", "ОЛИ", "ЕЛИ" -> "^";

            default -> S.substring(S.length() - 3);
          };

      S = new StringBuilder(left + right);
    }

    if (S.length() > 2) {
      final var left = S.substring(0, S.length() - 2);
      final var right =
          switch (S.substring(S.length() - 2)) {
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

            default -> S.substring(S.length() - 2);
          };

      S = new StringBuilder(left + right);
    }

    int B = cns1.indexOf(S.charAt(S.length() - 1)) + 1;
    if (B > 0) {
      S = new StringBuilder(S.substring(0, S.length() - 1) + cns2.charAt(B - 1));
    }

    String old_c = " ";
    StringBuilder V = new StringBuilder();
    int i = 1;
    while (i <= S.length()) {
      String c = S.substring(i - 1, i);
      B = ch.indexOf(c) + 1;
      if (B > 0) {
        if (old_c.equals("Й") || old_c.equals("И")) {
          if (c.equals("О") || c.equals("Е")) {
            old_c = "И";
            S = new StringBuilder(S.substring(0, S.length() - 1) + old_c);
          } else {
            if (!c.equals(old_c)) {
              V.append(ct.charAt(B - 1));
            }
          }
        } else {
          if (!c.equals(old_c)) {
            V.append(ct.charAt(B - 1));
          }
        }
      } else {
        if (!c.equals(old_c) && cns3.indexOf(c) > 0) {
          B = cns1.indexOf(old_c) + 1;
          if (B > 0) {
            old_c = cns2.substring(B - 1, B);
            V = new StringBuilder(V.substring(0, V.length() - 1) + old_c);
          }
        }
        if (!c.equals(old_c)) {
          V.append(c);
        }
      }
      old_c = c;
      i++;
    }

    return V.toString();
  }

  // https://moluch.ru/archive/19/1967/
  public static String getSqlMetaphone() {
    return "CREATE OR REPLACE FUNCTION rumetaphone(W text) "
        + "RETURNS text "
        + "LANGUAGE plpgsql "
        + "AS $$ "
        + "DECLARE "
        + "  alf text; "
        + "  cns1 text; "
        + "  cns2 text; "
        + "  cns3 text; "
        + "  ch text; "
        + "  ct text; "
        + "  S text; "
        + "  V text; "
        + "  i int; "
        + "  B int; "
        + "  c char(1); "
        + "  old_c char(1); "
        + "BEGIN "
        + "  alf := 'ОЕАИУЭЮЯПСТРКЛМНБВГДЖЗЙФХЦЧШЩЁЫ'; "
        + "  cns1 := 'БЗДВГ'; "
        + "  cns2 := 'ПСТФК'; "
        + "  cns3 := 'ПСТКБВГДЖЗФХЦЧШЩ'; "
        + "  ch := 'ОЮЕЭЯЁЫ'; "
        + "  ct := 'АУИИАИА'; "
        + "  W := UPPER(W); "
        + "  S := ''; "
        + "  V := ''; "
        + "  i := 1; "
        + "  WHILE i <= LENGTH(W) LOOP "
        + "    c := SUBSTRING(W, i, 1); "
        + "    IF POSITION(c IN alf) > 0 THEN "
        + "      S := S || c; "
        + "    END IF; "
        + "    i := i + 1; "
        + "  END LOOP; "
        + "  "
        + "  IF LENGTH(S) = 0 THEN "
        + "    RETURN ''; "
        + "  END IF; "
        + "  IF LENGTH(S) > 6 THEN "
        + "    S := LEFT(S, LENGTH(S) - 6) || "
        + "         CASE RIGHT(S, 6) "
        + "           WHEN 'ОВСКИЙ' THEN '!' "
        + "           WHEN 'ОВСКАЯ' THEN '!' "
        + "           WHEN 'ЕВСКИЙ' THEN '@' "
        + "           WHEN 'ЕВСКАЯ' THEN '@' "
        + "           ELSE RIGHT(S, 6) "
        + "         END; "
        + "  END IF; "
        + "  IF LENGTH(S) > 4 THEN "
        + "    S := LEFT(S, LENGTH(S) - 4) || "
        + "         CASE RIGHT(S, 4) "
        + "           WHEN 'ИЕВА' THEN '9' "
        + "           WHEN 'ЕЕВА' THEN '9' "
        + "           ELSE RIGHT(S, 4) "
        + "         END; "
        + "  END IF; "
        + "  IF LENGTH(S) > 3 THEN "
        + "    S := LEFT(S, LENGTH(S) - 3) || "
        + "         CASE RIGHT(S, 3) "
        + "           WHEN 'ОВА' -> '9' "
        + "           WHEN 'ЕВА' -> '9' "
        + "           WHEN 'ИНА' -> '1' "
        + "           WHEN 'ИЕВ' -> '4' "
        + "           WHEN 'ЕЕВ' -> '4' "
        + "           WHEN 'НКО' -> '3' "
        + "           WHEN 'АТЬ' -> '#' "
        + "           WHEN 'ЯТЬ' -> '#' "
        + "           WHEN 'ОТЬ' -> '#' "
        + "           WHEN 'ЕТЬ' -> '#' "
        + "           WHEN 'УТЬ' -> '#' "
        + "           WHEN 'ЕШЬ' -> '$' "
        + "           WHEN 'ИШЬ' -> '$' "
        + "           WHEN 'ЕТЕ' -> '$' "
        + "           WHEN 'ИТЕ' -> '$' "
        + "           WHEN 'АЛА' -> '%' "
        + "           WHEN 'ЯЛА' -> '%' "
        + "           WHEN 'АЛИ' -> '%' "
        + "           WHEN 'ЯЛИ' -> '%' "
        + "           WHEN 'УЛА' -> '%' "
        + "           WHEN 'УЛИ' -> '%' "
        + "           WHEN 'ОЛА' -> '^' "
        + "           WHEN 'ЕЛА' -> '^' "
        + "           WHEN 'ОЛИ' -> '^' "
        + "           WHEN 'ЕЛИ' -> '^' "
        + "           ELSE RIGHT(S, 3) "
        + "         END; "
        + "  END IF; "
        + "  IF LENGTH(S) > 2 THEN "
        + "    S := LEFT(S, LENGTH(S) - 2) || "
        + "         CASE RIGHT(S, 2) "
        + "           'АЯ' -> '6' "
        + "           'ЯЯ' -> '6' "
        + "           'ИЙ' -> '7' "
        + "           'ЫЙ' -> '7' "
        + "           'ИЯ' -> '7' "
        + "           'ЫХ' -> '5' "
        + "           'ИХ' -> '5' "
        + "           'ИН' -> '8' "
        + "           'ИК' -> '2' "
        + "           'ЕК' -> '2' "
        + "           'УК' -> '0' "
        + "           'ЮК' -> '0' "
        + "           'ЕМ' -> '$' "
        + "           'ИМ' -> '$' "
        + "           'ЕТ' -> '$' "
        + "           'ИТ' -> '$' "
        + "           'УТ' -> '%' "
        + "           'ЮТ' -> '%' "
        + "           'АТ' -> '%' "
        + "           'ЯТ' -> '%' "
        + "           'УЛ' -> '%' "
        + "           'ЮЛ' -> '%' "
        + "           'АЛ' -> '%' "
        + "           'ЯЛ' -> '%' "
        + "           'ОЛ' -> '^' "
        + "           'ЕЛ' -> '^' "
        + "           ELSE RIGHT(S, 2) "
        + "         END; "
        + "  END IF; "
        + "  B := POSITION(RIGHT(S, 1) IN cns1); "
        + "  IF B > 0 THEN "
        + "    S := LEFT(S, LENGTH(S) - 1) || SUBSTRING(cns2 FROM B FOR 1); "
        + "  END IF; "
        + "  old_c := ' '; "
        + "  i := 1; "
        + "  WHILE i <= LENGTH(S) LOOP "
        + "    c := SUBSTRING(S, i, 1); "
        + "    B := POSITION(c IN ch); "
        + "    IF B > 0 THEN "
        + "      IF old_c = 'Й' OR old_c = 'И' THEN "
        + "        IF c = 'О' OR c = 'Е' THEN "
        + "          old_c := 'И'; "
        + "          S := LEFT(S, LENGTH(S) - 1) || old_c; "
        + "        ELSE "
        + "          IF c <> old_c THEN "
        + "            V := V || SUBSTRING(ct FROM B FOR 1); "
        + "          END IF; "
        + "        END IF; "
        + "      ELSE "
        + "        IF c <> old_c THEN "
        + "          V := V || SUBSTRING(ct FROM B FOR 1); "
        + "        END IF; "
        + "      END IF; "
        + "    ELSE "
        + "      IF c <> old_c AND POSITION(c IN cns3) > 0 THEN "
        + "        B := POSITION(old_c IN cns1); "
        + "        IF B > 0 THEN "
        + "          old_c := SUBSTRING(cns2 FROM B FOR 1); "
        + "          V := LEFT(V, LENGTH(V) - 1) || old_c; "
        + "        END IF; "
        + "      END IF; "
        + "      IF c <> old_c THEN "
        + "        V := V || c; "
        + "      END IF; "
        + "    END IF; "
        + "    old_c := c; "
        + "    i := i + 1; "
        + "  END LOOP; "
        + "  "
        + "  RETURN V; "
        + "END; "
        + "$$; ";
  }
}
