package com.svetylkovo.rojo.matcher;

import com.svetylkovo.rojo.lambda.*;

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
        Iterable<Matcher> it = () -> new MatchIterator(pattern.matcher(str));
        return StreamSupport.stream(it.spliterator(), false);
    }

    public Stream<String> asStream(String str) {
        return asMatcherStream(str).map(Matcher::group);
    }

    public List<String> asList(String str) {
        return asStream(str).collect(toList());
    }

    public Iterable<String> asIterable(String str) {
        return asStream(str)::iterator;
    }

    public Iterable<Matcher> asMatcherIterable(String str) {
        return asMatcherStream(str)::iterator;
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

    public void forEach(String str, GroupArgs2 func) {
        asMatcherStream(str).forEach(m -> func.apply(m.group(1), m.group(2)));
    }

    public void forEach(String str, GroupArgs3 func) {
        asMatcherStream(str).forEach(m -> func.apply(m.group(1), m.group(2), m.group(3)));
    }

    public void forEach(String str, GroupArgs4 func) {
        asMatcherStream(str).forEach(m -> func.apply(m.group(1), m.group(2), m.group(3), m.group(4)));
    }

    public void forEach(String str, GroupArgs5 func) {
        asMatcherStream(str).forEach(m -> func.apply(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5)));
    }

    public void forEach(String str, GroupArgs6 func) {
        asMatcherStream(str).forEach(m -> func.apply(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6)));
    }

    public void forEach(String str, GroupArgs7 func) {
        asMatcherStream(str).forEach(m -> func.apply(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7)));
    }

    public void forEach(String str, GroupArgs8 func) {
        asMatcherStream(str).forEach(m -> func.apply(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7), m.group(8)));
    }

    public void forEach(String str, GroupArgs9 func) {
        asMatcherStream(str).forEach(m -> func.apply(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7), m.group(8), m.group(9)));
    }

    public void forEach(String str, GroupArgs10 func) {
        asMatcherStream(str).forEach(m -> func.apply(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7), m.group(8), m.group(9), m.group(10)));
    }
}
