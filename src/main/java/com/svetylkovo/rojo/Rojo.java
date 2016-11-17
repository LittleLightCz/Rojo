package com.svetylkovo.rojo;

import com.svetylkovo.rojo.matcher.RojoBeanMatcher;
import com.svetylkovo.rojo.matcher.RojoMatcher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
     * @param flags Regexp flags from the class {@link Pattern}
     * @return Stream of {@link Matcher}
     */
    public static Stream<Matcher> asMatcherStream(String regex, String str, int flags) {
        return matcher(regex, flags).asMatcherStream(str);
    }

    /**
     * Gets Stream of {@link Matcher}
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}. Use DEFAULT_FLAGS for default.
     * @param parallel Use true if you want to use a parallel {@link java.util.Spliterator} underneath.
     * @return Stream of {@link Matcher}
     */
    public static Stream<Matcher> asMatcherStream(String regex, String str, int flags, boolean parallel) {
        return matcher(regex, flags).asMatcherStream(str, parallel);
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
     * Gets Stream of String matches
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}. Use DEFAULT_FLAGS for default.
     * @param parallel Use true if you want to use a parallel {@link java.util.Spliterator} underneath.
     * @return Stream of String matches
     */
    public static Stream<String> asStream(String regex, String str, int flags, boolean parallel) {
        return matcher(regex, flags).asStream(str, parallel);
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
     * Gets List of {@link Matcher}
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @return List of {@link Matcher}
     */
    public static List<Matcher> asMatcherList(String regex, String str) {
        return matcher(regex).asMatcherList(str);
    }

    /**
     * Gets List of {@link Matcher}
     *
     * @param regex Regexp pattern
     * @param str Input string
     * @param flags Regexp flags from the class {@link Pattern}
     * @return List of {@link Matcher}
     */
    public static List<Matcher> asMatcherList(String regex, String str, int flags) {
        return matcher(regex, flags).asMatcherList(str);
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
}
