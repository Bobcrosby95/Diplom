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

    public static String statusCardCredit() {
        return "APPROVED";
    }

    public static String generateCVC() {
        return faker.numerify("###");
    }

    public static CardInfo generateRandom() {
        return new CardInfo(generateCardNumber4(), generateName("en"), generateMonth(), generateYear(), generateCVC());
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

    @Value
    public static class StatusCardCredit {
        String statusCardCredit;
    }

    public static String generateCardNumber1() {
        return "4444 4444 4444 4441";
    }

    public static String generateCardNumber2() {
        return "4444 4444 4444 4442";
    }

    public static String generateCardNumber4() {
        return "4444 4444 4444 4443";
    }

    public static String generateCardNumber3() {
        return "0000 0000 0000 0000";
    }

    public static String generateNull() {
        return "00";
    }

    public static String generateYear2() {
        return "21";
    }

    public static String generateCVC1() {

        return "000";
    }

    public static String generateMonth2() {
        return "15";
    }

    public static String generateName1() {
        return "@@@";
    }

    public static String generateName3() {
        return "12345";
    }

    public static String generateFieldsEmpty() {
        return "";
    }

    public static CardInfo generateAccurateCard() {
        return new CardInfo(generateCardNumber1(), generateName("en"), generateMonth(), generateYear(), generateCVC());
    }

    public static CardInfo generateNullCard() {
        return new CardInfo(generateCardNumber3(), generateName("en"), generateMonth(), generateYear(), generateCVC());
    }

    public static CardInfo generateRejectedCard() {
        return new CardInfo(generateCardNumber2(), generateName("en"), generateMonth(), generateYear(), generateCVC());
    }

    public static CardInfo generateNoCard() {
        return new CardInfo(generateFieldsEmpty(), generateName("en"), generateMonth(), generateYear(), generateCVC());
    }

    public static CardInfo generateNullYear() {
        return new CardInfo(generateCardNumber1(), generateName("en"), generateMonth(), generateNull(), generateCVC());
    }

    public static CardInfo generateNoYear() {
        return new CardInfo(generateCardNumber1(), generateName("en"), generateMonth(), generateYear2(), generateCVC());
    }

    public static CardInfo generateEmptyYear() {
        return new CardInfo(generateCardNumber1(), generateName("en"), generateMonth(), generateFieldsEmpty(), generateCVC());
    }

    public static CardInfo generateNullCvc() {
        return new CardInfo(generateCardNumber1(), generateName("en"), generateMonth(), generateYear(), generateCVC1());
    }

    public static CardInfo generateEmptyCvc() {
        return new CardInfo(generateCardNumber1(), generateName("en"), generateMonth(), generateYear(), generateFieldsEmpty());
    }

    public static CardInfo generateCvcTwo() {
        return new CardInfo(generateCardNumber1(), generateName("en"), generateMonth(), generateYear(), generateNull());
    }

    public static CardInfo generateNullMonth() {
        return new CardInfo(generateCardNumber1(), generateName("en"), generateNull(), generateYear(), generateCVC());
    }

    public static CardInfo generateNoMonth() {
        return new CardInfo(generateCardNumber1(), generateName("en"), generateMonth2(), generateYear(), generateCVC());
    }

    public static CardInfo generateEmptyMonth() {
        return new CardInfo(generateCardNumber1(), generateName("en"), generateFieldsEmpty(), generateYear(), generateCVC());
    }

    public static CardInfo generateSymbolsName() {
        return new CardInfo(generateCardNumber1(), generateName1(), generateMonth(), generateYear(), generateCVC());
    }

    public static CardInfo generateCyrillicName() {
        return new CardInfo(generateCardNumber1(), generateName("ru"), generateMonth(), generateYear(), generateCVC());
    }

    public static CardInfo generateNumbersName() {
        return new CardInfo(generateCardNumber1(), generateName3(), generateMonth(), generateYear(), generateCVC());
    }

    public static CardInfo generateNoName() {
        return new CardInfo(generateCardNumber1(), generateFieldsEmpty(), generateMonth(), generateYear(), generateCVC());
    }

    public static CardInfo generateEmpty() {
        return new CardInfo(generateFieldsEmpty(), generateFieldsEmpty(), generateFieldsEmpty(), generateFieldsEmpty(), generateFieldsEmpty());
    }
}
