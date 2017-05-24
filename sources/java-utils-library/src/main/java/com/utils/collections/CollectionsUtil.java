package com.utils.collections;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by oksdud on 01.12.2016.
 */
public class CollectionsUtil {

    private final static Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();

    private Iterable<String> filterNonNumbers(String param) {
        return
                param == null ? null : Iterables.filter(splitter.split(param),
                        new com.google.common.base.Predicate<String>() {
                            @Override
                            public boolean apply(String input) {
                                return NumberUtils.isDigits(input);
                            }
                        }
                );

    }


        private Iterable<String> filterNonLiterals(String param) {
        return
                param == null ? null : Iterables.filter(splitter.split(param),
                        new com.google.common.base.Predicate<String>() {
                            @Override
                            public boolean apply(String input) {
                                return !StringUtils.isNumeric(input);
                            }
                        }
                );
    }

    public static <T, U> Set<U> convertSet(List<T> from, java.util.function.Function<T, U> func) {
        return from != null ? from.stream().map(func).collect(Collectors.toSet()) : Sets.newHashSet();
    }

    public static boolean isNullOrEmpty(final Collection<?> c) {
        return c == null || c.isEmpty();
    }
}
