package com.utils.collections;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.utils.date.Intervals;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Splitter.on;

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

    private static final Splitter commaSplitter = on(',').trimResults().omitEmptyStrings();

    public static boolean isNullOrEmpty(final Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static List<Long> stringToLongList(String value) throws Exception{
        final List<Long> result = new ArrayList();
        Iterable<String> data = commaSplitter.split(value);
        for (String s : data) {
            try {
                result.add(Long.parseLong(s));
            } catch (NumberFormatException ex) {
                throw new Exception("Illegal argument");
            }
        }

        return result;
    }

    public static List<String> separatedCommaStringToList(String value)throws Exception {
        final List<String> result = new ArrayList();
        Iterable<String> data = commaSplitter.split(value);
        for (String s : data) {
            try {
                result.add(s);
            } catch (Exception ex) {
                throw new Exception("Illegal argument");
            }
        }

        return result;
    }

    public static String join(Collection collection) {
        return join(collection, ",");
    }

    public static String join(Collection collection, String delimiter) {
        return Joiner.on(delimiter).skipNulls().join(collection);
    }

    /*
     intersection of 2 string exist or not(full or partial)
     */
//    public static Boolean contain(String separatedCommaStringSource, String separatedCommaStringDestination) {
//        if (Strings.isNullOrEmpty(separatedCommaStringSource)) {
//            if (Strings.isNullOrEmpty(separatedCommaStringDestination)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        final List<String> resultSource = separatedCommaStringToList(separatedCommaStringSource);
//        final List<String> resultDestination = separatedCommaStringToList(separatedCommaStringDestination);
//        final List<String> resultList = ListUtils.intersection(resultSource, resultDestination);
//        if (!isNullOrEmpty(resultList)) {
//            if (resultList.size() == resultDestination.size()) {
//                return true;//all elements contains
//            }
//        }
//        return false;//not contain at all
//    }

    /*
       union without the same elements
     */
//    public static String concat(String separatedCommaStringSource, String separatedCommaStringDestination) {
//        if (Strings.isNullOrEmpty(separatedCommaStringSource)) {
//            return separatedCommaStringDestination;
//        }
//        List<String> listSource = convertIntoList(separatedCommaStringSource);
//        List<String> listDestination = convertIntoList(separatedCommaStringDestination);
//        List<String> resultList = ListUtils.union(listSource, listDestination);
//        Set<String> resultSet = new HashSet(resultList);//without the same elements
//        return convertIntoCommaSeparatedString(new ArrayList<String>(resultSet));
//
//    }

    public static List<String> convertIntoList(String commaSeparatedString) {
        if (Strings.isNullOrEmpty(commaSeparatedString)) return Lists.newArrayList();
        List<String> items = Arrays.asList(commaSeparatedString.split("\\s*,\\s*"));
        return items;
    }

    public static List<Long> convertIntoLongList(String commaSeparatedString) {
        if (Strings.isNullOrEmpty(commaSeparatedString)) return Lists.newArrayList();
        List<String> items = Arrays.asList(commaSeparatedString.split("\\s*,\\s*"));
        List<Long> longs = new ArrayList<>();
        for (String item : items) {
            try {
                longs.add(Long.valueOf(item));
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return longs;
    }

    public static List<String> restrict(List<String> all, List<String> permitted) {
        List<String> result = Lists.newArrayList();
        for (String s : all) {
            if (permitted.contains(s)) {
                result.add(s);
            }
        }
        return result;
    }

    public static String convertIntoCommaSeparatedString(List<String> strings) {
        if (isNullOrEmpty(strings)) return "";
        StringBuilder buffer = new StringBuilder();
        for (String string : strings) {
            if (!Strings.isNullOrEmpty(buffer.toString())) buffer.append(",");
            buffer.append(string);
        }
        return buffer.toString();
    }

    public static String convertIntegersIntoCommaSeparatedString(List<Integer> integers) {
        if (isNullOrEmpty(integers)) return "";
        StringBuilder buffer = new StringBuilder();
        for (Integer intValue : integers) {
            if (!Strings.isNullOrEmpty(buffer.toString())) buffer.append(",");
            buffer.append(intValue);
        }
        return buffer.toString();
    }

    public static String convertLongsIntoCommaSeparatedString(List<Long> longs) {
        if (isNullOrEmpty(longs)) return "";
        StringBuilder buffer = new StringBuilder();
        for (Long longValue : longs) {
            if (buffer.length() != 0) buffer.append(",");
            buffer.append(longValue);
        }
        return buffer.toString();
    }

    /**
     * excluding duplicates
     *
     * @param list
     * @param element
     * @return
     */
    public static List<String> add(List<String> list, String element) {
        List<String> newList = new ArrayList<String>(list);
        newList.add(element);
        Set<String> stringSet = new HashSet<String>(newList);
        return new ArrayList<String>(stringSet);
    }

    public static List<String> addLong(List<String> list, Long element) {
        List<String> newList = new ArrayList<String>(list);
        newList.add(String.valueOf(element));
        Set<String> stringSet = new HashSet<String>(newList);
        return new ArrayList<String>(stringSet);
    }

    public static String convertLongsToString(Collection<Long> longs) {
        if (isNullOrEmpty(longs)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Long longValue : longs) {
            if (!Strings.isNullOrEmpty(sb.toString())) {
                sb.append(",");
            }
            sb.append(longValue);
        }

        return sb.toString();
    }

    public static <T> List<T> slice(List<T> list, int from, int limit) {
        int max = list.size();
        return list.subList(
                Intervals.between(from, 0, max, 0),
                Intervals.between(from + limit, 0, max, max));
    }

}
