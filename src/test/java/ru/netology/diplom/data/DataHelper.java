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
        return faker.numerify("#### #### #### ####");
    }


    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generateMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String statusCard() {
        return "APPROVED";
    }

    public static String generateCVC() {
        return faker.numerify("###");
    }

    public static CardInfo generateRandom() {
        return new CardInfo(generateCardNumber(), generateName("en"), generateMonth(), generateYear(), generateCVC());
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String name;
        String month;
        String year;
        String cvc;
    }

    @Value
    public static class StatusCard {
        String statusCard;
    }
}
