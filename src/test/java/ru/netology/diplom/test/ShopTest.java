package ru.netology.diplom.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diplom.data.DataHelper;
import ru.netology.diplom.data.SQLHelper;
import ru.netology.diplom.page.LoginPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ShopTest {
    LoginPage loginPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void openHost() {
        loginPage = open("http://localhost:8080", LoginPage.class);
    }

    //Позитивные сценарии.

    @Test
    @DisplayName("Покупка с помощью дебетовой карты с валидными данными.")
    void aqaShopTest1() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateAccurateCard();
        debitPage.validCard(validCard);
        debitPage.answerBank("Операция одобрена Банком.");
        var statusCard = SQLHelper.getStatusCard();
        String status = statusCard.getStatusCard();
        String expectedStatus = "APPROVED";
        Assertions.assertEquals(expectedStatus, status);
    }

    @Test
    @DisplayName("Покупка в кредит с валидными данными.")
    void aqaShopTest2() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateAccurateCard();
        creditPage.validCardCredit(validCardCredit);
        creditPage.answerBankCredit("Операция одобрена Банком.");
        var statusCard = SQLHelper.getStatusCardCredit();
        String status = statusCard.getStatusCardCredit();
        String expectedStatus = "APPROVED";
        Assertions.assertEquals(expectedStatus, status);
    }

    //Негетивные сценарии с использованием дебетовой карты.

    @Test
    @DisplayName("1.Покупка тура с дебетовой картой с использованием отказанной карты.")
    void aqaShopTest3() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateRejectedCard();
        debitPage.validCard(validCard);
        debitPage.answerBank("Ошибка! Банк отказал в проведении операции.");
        var statusCard = SQLHelper.getStatusCard();
        String status = statusCard.getStatusCard();
        String expectedStatus = "DECLINED";
        Assertions.assertEquals(expectedStatus, status);
    }

    @Test
    @DisplayName("2.Покупка тура с дебетовой картой с использованием невалидных данных (с прошедшим годом).")
    void aqaShopTest4() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateNoYear();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Истёк срок действия карты");
    }

    @Test
    @DisplayName("3.Покупка тура с дебетовой картой с использованием невалидных данных (с несуществующим месяцем).")
    void aqaShopTest5() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateNoMonth();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("4.Покупка тура с дебетовой картой с использованием невалидных данных (данные владельца на кириллице).")
    void aqaShopTest6() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateCyrillicName();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("5.Покупка тура с дебетовой картой с использованием невалидных данных (поле владелец цифры).")
    void aqaShopTest7() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateNumbersName();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("6.Покупка тура с дебетовой картой с использованием невалидных данных (поле владелец спецсимволы).")
    void aqaShopTest8() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateSymbolsName();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("7.Покупка тура с дебетовой картой с использованием невалидных данных (номер карты нули).")
    void aqaShopTest9() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateNullCard();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("8.Покупка тура с дебетовой картой с использованием невалидных данных (поле месяц заполнить нулями).")
    void aqaShopTest10() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateNullMonth();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("9.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле номер карты).")
    void aqaShopTest11() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateNoCard();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("10.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле владелец).")
    void aqaShopTest12() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateNoName();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("11.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле месяц).")
    void aqaShopTest13() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateEmptyMonth();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("12.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле год).")
    void aqaShopTest14() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateEmptyYear();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("13.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле cvc).")
    void aqaShopTest15() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateEmptyCvc();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("14.Покупка тура с дебетовой картой с использованием невалидных данных (пустые поля).")
    void aqaShopTest16() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateEmpty();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("15.Покупка тура с дебетовой картой с использованием невалидных данных (заполнить поле «cvc» двумя цифрами).")
    void aqaShopTest17() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateCvcTwo();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("16.Покупка тура с дебетовой картой с использованием невалидных данных (заполнить поле «cvc» нулями).")
    void aqaShopTest18() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateNullCvc();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("17.Покупка тура с дебетовой картой с использованием невалидных данных (в поле год нули).")
    void aqaShopTest19() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateNullYear();
        debitPage.validCard(validCard);
        debitPage.verifyErrorNotification("Истёк срок действия карты");
    }

    @Test
    @DisplayName("18.Покупка тура с дебетовой картой с использованием невалидных данных (карта не из набора).")
    void aqaShopTest20() {
        var debitPage = loginPage.debitBuy();
        var validCard = DataHelper.generateRandom();
        debitPage.validCard(validCard);
        debitPage.answerBank("Ошибка! Банк отказал в проведении операции.");
    }

    //Негативные сценарии покупки тура в кредит.

    @Test
    @DisplayName("1.Покупка тура в кредит с использованием невалидных данных (пустые поля).")
    void aqaShopTest21() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateEmpty();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("2.Покупка тура в кредит с использованием невалидных данных (с прошедшим годом).")
    void aqaShopTest22() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateNoYear();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Истёк срок действия карты");
    }

    @Test
    @DisplayName("3.Покупка тура в кредит с использованием невалидных данных (с несуществующим месяцем).")
    void aqaShopTest23() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateNoMonth();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("4.Покупка тура в кредит с использованием невалидных данных (данные владельца на кириллице).")
    void aqaShopTest24() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateCyrillicName();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("5.Покупка тура в кредит с использованием невалидных данных (поле владелец цифры).")
    void aqaShopTest25() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateNumbersName();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("6.Покупка тура в кредит с использованием невалидных данных (поле владелец спецсимволы).")
    void aqaShopTest26() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateSymbolsName();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("7.Покупка тура в кредит с использованием невалидных данных (номер карты нули).")
    void aqaShopTest27() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateNullCard();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("8.Покупка тура в кредит с использованием невалидных данных (поле месяц заполнить нулями).")
    void aqaShopTest28() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateNullMonth();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("9.Покупка тура в кредит с использованием невалидных данных (пустое поле номер карты).")
    void aqaShopTest29() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateNoCard();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("10.Покупка тура в кредит с использованием невалидных данных (пустое поле владелец).")
    void aqaShopTest30() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateNoName();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("11.Покупка тура в кредит с использованием невалидных данных (пустое поле месяц).")
    void aqaShopTest31() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateEmptyMonth();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("12.Покупка тура в кредит с использованием невалидных данных (пустое поле год).")
    void aqaShopTest32() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateEmptyYear();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("13.Покупка тура в кредит с использованием невалидных данных (пустое поле cvc).")
    void aqaShopTest33() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateEmptyCvc();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("14.Покупка тура в кредит с использованием невалидных данных (заполнить поле «cvc» двумя цифрами).")
    void aqaShopTest34() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateCvcTwo();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("15.Покупка тура в кредит с использованием отказанной карты.")
    void aqaShopTest35() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateRejectedCard();
        creditPage.validCardCredit(validCardCredit);
        creditPage.answerBankCredit("Ошибка! Банк отказал в проведении операции.");
        var statusCard = SQLHelper.getStatusCardCredit();
        String status = statusCard.getStatusCardCredit();
        String expectedStatus = "DECLINED";
        Assertions.assertEquals(expectedStatus, status);
    }

    @Test
    @DisplayName("16.Покупка тура в кредит с использованием невалидных данных (заполнить поле «cvc» нулями).")
    void aqaShopTest36() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateNullCvc();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Неверный формат");
    }

    @Test
    @DisplayName("17.Покупка тура в кредит с использованием невалидных данных (заполнить поле «год» нулями).")
    void aqaShopTest37() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateNullYear();
        creditPage.validCardCredit(validCardCredit);
        creditPage.verifyErrorNotification("Истёк срок действия карты");
    }

    @Test
    @DisplayName("18.Покупка тура в кредит с использованием невалидных данных (карта не из набора).")
    void aqaShopTest38() {
        var creditPage = loginPage.creditBuy();
        var validCardCredit = DataHelper.generateRandom();
        creditPage.validCardCredit(validCardCredit);
        creditPage.answerBankCredit("Ошибка! Банк отказал в проведении операции.");
    }
}
