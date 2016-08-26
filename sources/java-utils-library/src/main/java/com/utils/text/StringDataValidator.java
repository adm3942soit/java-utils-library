package com.utils.text;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.utils.text.Preconditions.checkArgument;

/**
 * Created by oksdud on 23.08.2016.
 */

public class StringDataValidator {
    private static Pattern pattern;
    private static Matcher matcher;
    public static final String ACOUNT_PATTERN = "[a-zA-Z0-9]";
    public static final String ZIP_PATTERN = "([A-Z]{1,2}" + "[0-9]{1,2}[A-Z]?)" + "\\s?" + "([0-9][A-Z]{2})";
    public static final String ZIP_PATTERN_ALL = "[a-zA-Z0-9\\s]";
    public static final String SURNAME_PATTERN = "[a-zA-Z]";
    public static final String RECIPIENT_DOB_FORMAT = "yyyyMMdd";

    public static boolean symbolValidator(final Character smb, String template) {
        pattern = Pattern.compile(template);
        matcher = pattern.matcher(String.valueOf(smb));
        return matcher.matches();
    }

    public static boolean stringValidator(final String ss, String template) {
        pattern = Pattern.compile(template);
        matcher = pattern.matcher(ss);
        boolean result = matcher.matches();
        return result;
    }

    public static String stringValidatorFirstGroup(final String ss, String template) {
        pattern = Pattern.compile(template);
        matcher = pattern.matcher(ss);
        boolean result = matcher.matches();
        return matcher.group(1);
    }

    public static String stringValidatorByChar(final String ss, String template) {
        char[] array = ss.toCharArray();
        StringBuffer result = new StringBuffer("");
        for (char a : array) {
            if (symbolValidator(a, template)) result.append(a);
        }
        return result.toString();
    }

    public static boolean booleanValidatorByChar(final String ss, String template) {
        char[] array = ss.toCharArray();
        StringBuffer result = new StringBuffer("");
        for (char a : array) {
            if (!symbolValidator(a, template)) return false;
        }
        return true;
    }

    public static String tailWithSymbol(String variable, char smb, int len) {
        String result = variable +
                (variable.length() < len ?
                        StringUtils.repeat(
                                String.valueOf(new Character(smb)), len - variable.length()) :
                        "");
        return result;
    }

    public static String validateAccount(String account) throws Exception{
        String result = stringValidatorByChar(account, StringDataValidator.ACOUNT_PATTERN);
        int length = result.length();
        checkArgument(
                (length >= 1),
                400,
                "Account in additional data can not to have zero-length"
        );
        char z = 42;
        result = StringDataValidator.tailWithSymbol(result, z, 10);
        return result;
    }

    public static String validateZIP(String zip) throws Exception{
        String result = zip.trim().toUpperCase();
        checkArgument(
                StringDataValidator.stringValidator(result, StringDataValidator.ZIP_PATTERN),
                400,
                "ZIP in additional data can consist only from symbols " + StringDataValidator.ZIP_PATTERN
        );
        /*return first group only!*/
        result = StringDataValidator.stringValidatorFirstGroup(result, StringDataValidator.ZIP_PATTERN);
        return result;

    }

    public static String validateSurname(String surname) throws Exception{
        String result = TransliterationFromCyrToLat.cyrToLat(surname);
        result = StringDataValidator.stringValidatorByChar(result, StringDataValidator.SURNAME_PATTERN);
        int length = result.length();
        checkArgument(
                (length >= 1),
                400,
                "Surname in additional data can not to have zero-length"
        );
        char z = 42;
        result = StringDataValidator.tailWithSymbol(result, z, 6);
        result = result.toUpperCase();
        return result;
    }

    public static void validateDOB(String dob) throws Exception {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(RECIPIENT_DOB_FORMAT);
            sdf.setLenient(false);
            date = sdf.parse(dob);
            if (!dob.equals(sdf.format(date))
                    || (date.compareTo(java.sql.Date.valueOf(LocalDate.now())) > 0)) {
                date = null;
            }
        } catch (ParseException ex) {

            System.out.println("Cannot parse Recipient.DOB "+ dob);
        }
        if (date == null) {
            throw new Exception("Invalid Recipient DOB provided");
        }

    }

    public static void main(String[] args) {
        try {


        String ac = validateAccount("54216045101234");
        int lenAccount = ac.length();
        System.out.println(ac.substring(0, 6) + ac.substring(lenAccount - 4));

        System.out.println(validateZIP("PR17QH"));
        System.out.println(validateZIP("PR29RQ"));
        System.out.println(validateSurname("Дудник Оксана"));
        System.out.println(StringDataValidator.stringValidatorByChar("PR1 @7QH", StringDataValidator.ZIP_PATTERN_ALL));
        validateDOB("19800212");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

