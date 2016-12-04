package com.svetylkovo.rojo;

import com.svetylkovo.rojo.lambda.*;
import com.svetylkovo.rojo.matcher.RojoBeanMatcher;
import com.svetylkovo.rojo.matcher.RojoMatcher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.svetylkovo.rojo.matcher.RojoConstants.DEFAULT_FLAGS;

/**
 * Main static Rojo class which you should use to perform your regexp matching.
 */
public class Rojo {

    /**
     * Creates a new {@link RojoBeanMatcher} instance for Bean (POJO) regexp matching.
     *
     * @param rojoBean Bean class
     * @param <T> Type of the Bean class
     * @return RojoBeanMatcher instance
     */
    public static <T> RojoBeanMatcher<T> of(Class<T> rojoBean) {
        return new RojoBeanMatcher<>(rojoBean);
    }

    /**
     * Get a new instance of {@link RojoMatcher} for further reuse
     *
     * @param regex Regexp pattern
     * @return New {@link RojoMatcher} instance
     */
    public static RojoMatcher matcher(String regex) {
        return new RojoMatcher(regex);
    }

    /**
     * Get a new instance of {@link RojoMatcher} for further reuse
     *
     * @param regex Regexp pattern
     * @param flags Regexp flags from the class {@link Pattern}
     * @return New {@link RojoMatcher} instance
     */
    public static RojoMatcher matcher(String regex, int flags) {
        return new RojoMatcher(regex, flags);
    }

    /**
     * Gets Stream of {@link Matcher}
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @return Stream of {@link Matcher}
     */
    public static Stream<Matcher> asMatcherStream(String regex, String str) {
        return matcher(regex).asMatcherStream(str);
    }

    /**
     * Gets Stream of {@link Matcher}
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}. Use DEFAULT_FLAGS for default.
     * @return Stream of {@link Matcher}
     */
    public static Stream<Matcher> asMatcherStream(String regex, String str, int flags) {
        return matcher(regex, flags).asMatcherStream(str);
    }

    /**
     * Gets Stream of String matches
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @return Stream of String matches
     */
    public static Stream<String> asStream(String regex, String str) {
        return matcher(regex).asStream(str);
    }

    /**
     * Gets Stream of String matches
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}. Use DEFAULT_FLAGS for default.
     * @return Stream of String matches
     */
    public static Stream<String> asStream(String regex, String str, int flags) {
        return matcher(regex, flags).asStream(str);
    }

    /**
     * Gets List of String matches
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @return List of String matches
     */
    public static List<String> asList(String regex, String str) {
        return matcher(regex).asList(str);
    }

    /**
     * Gets List of String matches
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}. Use DEFAULT_FLAGS for default.
     * @return List of String matches
     */
    public static List<String> asList(String regex, String str, int flags) {
        return matcher(regex, flags).asList(str);
    }

    /**
     * Gets Iterable of {@link Matcher}
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @return Iterable of {@link Matcher}
     */
    public static Iterable<Matcher> asMatcherIterable(String regex, String str) {
        return asMatcherIterable(regex, str, DEFAULT_FLAGS);
    }

    /**
     * Gets Iterable of {@link Matcher}
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @return Iterable of {@link Matcher}
     */
    public static Iterable<Matcher> asMatcherIterable(String regex, String str, int flags) {
        return matcher(regex, flags).asMatcherIterable(str);
    }

    /**
     * Gets Iterable of String matches
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @return Iterable of String matches
     */
    public static Iterable<String> asIterable(String regex, String str) {
        return asIterable(regex, str, DEFAULT_FLAGS);
    }

    /**
     * Gets Iterable of String matches
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @return Iterable of String matches
     */
    public static Iterable<String> asIterable(String regex, String str, int flags) {
        return matcher(regex, flags).asIterable(str);
    }

    /**
     * Gets Map of String pairs. You can use this only with regexp that has <b>exactly 2 groups</b> defined in it.
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @return Map of String pairs
     */
    public static Map<String, String> asMap(String regex, String str) {
        return matcher(regex).asMap(str);
    }

    /**
     * Gets Map of String pairs. You can use this only with regexp that has <b>exactly 2 groups</b> defined in it.
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @return Map of String pairs
     */
    public static Map<String, String> asMap(String regex, String str, int flags) {
        return matcher(regex, flags).asMap(str);
    }

    /**
     * Finds single match
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @return Optional of matched string
     */
    public static Optional<String> find(String regex, String str) {
        return matcher(regex).find(str);
    }

    /**
     * Finds single match
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @return Optional of matched string
     */
    public static Optional<String> find(String regex, String str, int flags) {
        return matcher(regex, flags).find(str);
    }

    /**
     * Finds single match
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @return Optional of matched {@link Matcher}
     */
    public static Optional<Matcher> findMatcher(String regex, String str) {
        return matcher(regex).findMatcher(str);
    }

    /**
     * Finds single match
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @return Optional of matched {@link Matcher}
     */
    public static Optional<Matcher> findMatcher(String regex, String str, int flags) {
        return matcher(regex, flags).findMatcher(str);
    }

    /**
     * Replaces match in String
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func Function that maps {@link Matcher} to String
     * @return Replaced String
     */
    public static String replaceMatcher(String regex, String str, Function<Matcher,String> func) {
        return matcher(regex).replaceMatcher(str, func);
    }

    /**
     * Replaces match in String
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func Function that maps {@link Matcher} to String
     * @param flags Regexp flags from the class {@link Pattern}
     * @return Replaced String
     */
    public static String replaceMatcher(String regex, String str, int flags, Function<Matcher,String> func) {
        return matcher(regex, flags).replaceMatcher(str, func);
    }

    /**
     * Replaces match in String
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func Function that maps matched String to another String
     * @return Replaced String
     */
    public static String replace(String regex, String str, Function<String,String> func) {
        return matcher(regex).replace(str, func);
    }

    /**
     * Replaces match in String
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func Function that maps matched String to another String
     * @param flags Regexp flags from the class {@link Pattern}
     * @return Replaced String
     */
    public static String replace(String regex, String str, int flags, Function<String,String> func) {
        return matcher(regex, flags).replace(str, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func lambda function with 2 arguments
     */
    public static void forEach(String regex, String str, GroupArgs2 func) {
        forEach(regex, str, DEFAULT_FLAGS, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func lambda function with 3 arguments
     */
    public static void forEach(String regex, String str, GroupArgs3 func) {
        forEach(regex, str, DEFAULT_FLAGS, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func lambda function with 4 arguments
     */
    public static void forEach(String regex, String str, GroupArgs4 func) {
        forEach(regex, str, DEFAULT_FLAGS, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func lambda function with 5 arguments
     */
    public static void forEach(String regex, String str, GroupArgs5 func) {
        forEach(regex, str, DEFAULT_FLAGS, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func lambda function with 6 arguments
     */
    public static void forEach(String regex, String str, GroupArgs6 func) {
        forEach(regex, str, DEFAULT_FLAGS, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func lambda function with 7 arguments
     */
    public static void forEach(String regex, String str, GroupArgs7 func) {
        forEach(regex, str, DEFAULT_FLAGS, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func lambda function with 8 arguments
     */
    public static void forEach(String regex, String str, GroupArgs8 func) {
        forEach(regex, str, DEFAULT_FLAGS, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func lambda function with 9 arguments
     */
    public static void forEach(String regex, String str, GroupArgs9 func) {
        forEach(regex, str, DEFAULT_FLAGS, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param func lambda function with 10 arguments
     */
    public static void forEach(String regex, String str, GroupArgs10 func) {
        forEach(regex, str, DEFAULT_FLAGS, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @param func lambda function with 2 arguments
     */
    public static void forEach(String regex, String str, int flags, GroupArgs2 func) {
        matcher(regex, flags).forEach(str, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @param func lambda function with 3 arguments
     */
    public static void forEach(String regex, String str, int flags, GroupArgs3 func) {
        matcher(regex, flags).forEach(str, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @param func lambda function with 4 arguments
     */
    public static void forEach(String regex, String str, int flags, GroupArgs4 func) {
        matcher(regex, flags).forEach(str, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @param func lambda function with 5 arguments
     */
    public static void forEach(String regex, String str, int flags, GroupArgs5 func) {
        matcher(regex, flags).forEach(str, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @param func lambda function with 6 arguments
     */
    public static void forEach(String regex, String str, int flags, GroupArgs6 func) {
        matcher(regex, flags).forEach(str, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @param func lambda function with 7 arguments
     */
    public static void forEach(String regex, String str, int flags, GroupArgs7 func) {
        matcher(regex, flags).forEach(str, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @param func lambda function with 8 arguments
     */
    public static void forEach(String regex, String str, int flags, GroupArgs8 func) {
        matcher(regex, flags).forEach(str, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @param func lambda function with 9 arguments
     */
    public static void forEach(String regex, String str, int flags, GroupArgs9 func) {
        matcher(regex, flags).forEach(str, func);
    }

    /**
     * Iterate over results and call a lambda function, where all groups are extracted as lambda's arguments
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @param func lambda function with 10 arguments
     */
    public static void forEach(String regex, String str, int flags, GroupArgs10 func) {
        matcher(regex, flags).forEach(str, func);
    }

    /**
     * Match and extract the first group as a Stream<String>
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @return The first group as a Stream<String>
     */
    public static Stream<String> firstGroup(String regex, String str) {
        return firstGroup(regex, str, DEFAULT_FLAGS);
    }

    /**
     * Match and extract the first group as a Stream<String>
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @return The first group as a Stream<String>
     */
    public static Stream<String> firstGroup(String regex, String str, int flags) {
        return matcher(regex, flags).firstGroup(str);
    }

}
