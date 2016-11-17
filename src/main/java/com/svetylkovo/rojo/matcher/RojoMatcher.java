package com.svetylkovo.rojo.matcher;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.svetylkovo.rojo.matcher.RojoConstants.DEFAULT_FLAGS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Rojo's plain matcher class
 */
public class RojoMatcher {

    private final Pattern pattern;

    public RojoMatcher(String regex, int flags) {
        pattern = Pattern.compile(regex, flags);
    }

    public RojoMatcher(String regex) {
        pattern = Pattern.compile(regex, DEFAULT_FLAGS);
    }

    public Stream<Matcher> asMatcherStream(String str) {
        return asMatcherStream(str, false);
    }

    public Stream<Matcher> asMatcherStream(String str, boolean parallel) {
        Iterable<Matcher> it = () -> new MatchIterator(pattern.matcher(str));
        return StreamSupport.stream(it.spliterator(), parallel);
    }

    public Stream<String> asStream(String str) {
        return asStream(str, false);
    }

    public Stream<String> asStream(String str, boolean parallel) {
        return asMatcherStream(str, parallel).map(Matcher::group);
    }

    public List<String> asList(String str) {
        return asStream(str).collect(toList());
    }

    public List<Matcher> asMatcherList(String str) {
        return asMatcherStream(str).collect(toList());
    }

    public Map<String, String> asMap(String str) {
        return asMatcherStream(str).collect(toMap(m -> m.group(1), m -> m.group(2)));
    }

    public Optional<String> find(String str) {
        return asStream(str).findFirst();
    }

    public Optional<Matcher> findMatcher(String str) {
        return asMatcherStream(str).findFirst();
    }

    public String replaceMatcher(String str, Function<Matcher, String> func) {
        Matcher matcher = pattern.matcher(str);

        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, func.apply(matcher));
        }
        matcher.appendTail(result);

        return result.toString();
    }

    public String replace(String str, Function<String, String> func) {
        return replaceMatcher(str, m -> func.apply(m.group()));
    }
}
