package com.example;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexMain {

    public static void main(String[] args) {

        String str1 = "_abc";
        System.out.println(Pattern.compile("abc").matcher(str1).matches());     // false
        System.out.println(Pattern.compile("_abc").matcher(str1).matches());    // true
        System.out.println(Pattern.compile(".*abc.*").matcher(str1).matches()); // true
        System.out.println(Pattern.compile("^_.*").matcher(str1).matches());    // true

        String str2 = ": abc";
        System.out.println("----------");
        System.out.println(Pattern.compile("abcde").matcher(str2));             // false
        System.out.println(Pattern.compile("abc").matcher(str2).matches());     // false
        System.out.println(Pattern.compile(".*abc.*").matcher(str2).matches()); // true
        System.out.println(Pattern.compile("^:.*").matcher(str2).matches());    // true

        System.out.println("----------");
        System.out.println(Pattern.compile("(https|git)://github.com").matcher("https://github.com")
                                  .matches()); // true
        System.out.println(Pattern.compile("(https|git)://github.com").matcher("http://github.com")
                                  .matches());  // false

        // Dangling meta character
        System.out.println("----------");
        System.out.println(Pattern.compile("github.com").matcher("github.com").matches());  // true
        System.out.println(Pattern.compile("github.com").matcher("githubbcom").matches());  // true
        // escape character
        System.out.println(Pattern.compile("github\\.com").matcher("github.com").matches());  // true
        System.out.println(Pattern.compile("github\\.com").matcher("githubbcom").matches());  // false

        // replaceAll
        System.out.println("----------");
        System.out.println(Pattern.compile("\\+").matcher("++").replaceAll("-"));
        System.out.println(Pattern.compile("\\?").matcher("?!?").replaceAll("!"));

        // group
        System.out.println("----------");
        showGroup("(\\d).(\\d).(\\d)", List.of(), "1.2.3");
        showGroup("(\\d).(\\d).(\\d)", List.of(), "1.2.3.4");
        showGroup("(\\d).(\\d).(\\d)", List.of(), "1.2.3.4.5.6");
        showGroup("(?<first>\\d).(?<second>\\d).(?<third>\\d)",
                  List.of("first", "second", "third"), "1.2.3");
    }

    private static void showGroup(String regex, List<String> names, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        System.out.println("regex: " + regex + ", input: " + input);

        while (matcher.find()) {
            System.out.println("groupCount: " + matcher.groupCount());
            System.out.println("group: " + matcher.group());
            System.out.println("group(0): " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("group(" + i + "): " + matcher.group(i));
            }

            for (String name : names) {
                System.out.println("group(" + name + "): " + matcher.group(name));
            }
        }
    }
}
