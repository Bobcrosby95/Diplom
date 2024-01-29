package ru.netology.diplom.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker();

    private DataHelper() {
    }

    public static String generateCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String generateCardNumber2() {
        return "4444 4444 4444 4442";
    }

    public static String generateCardNumber3() {
        return "4444 4444 4444 4443";
    }

    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generateMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear() {
        int currentYear = LocalDate.now().getYear() % 100;
        return String.valueOf(currentYear);
    }

    public static String generateCVC() {
        return faker.numerify("###");
    }

    @Value
    public static class CardInfo {
        String name;
        String month;
        String year;
        String cvc;
    }
}
