package ru.netology.diplom.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diplom.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ShopTest {
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
        open("http://localhost:8080");
    }

    //Позитивные сценарии.

    @Test
    @DisplayName("Покупка с помощью дебетовой карты с валидными данными.")
    void aqaShopTest1() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Операция одобрена Банком."));
    }

    @Test
    @DisplayName("Покупка в кредит с валидными данными.")
    void aqaShopTest2() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Операция одобрена Банком."));
    }

    //Негетивные сценарии с использованием дебетовой карты.

    @Test
    @DisplayName("1.Покупка тура с дебетовой картой с использованием отказанной карты.")
    void aqaShopTest3() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4442");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Операция одобрена Банком."));
    }

    @Test
    @DisplayName("2.Покупка тура с дебетовой картой с использованием невалидных данных (с прошедшим годом).")
    void aqaShopTest4() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4442");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue("21");
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Истёк срок действия карты"));
    }

    @Test
    @DisplayName("3.Покупка тура с дебетовой картой с использованием невалидных данных (с несуществующим месяцем).")
    void aqaShopTest5() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue("13");
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    @Test
    @DisplayName("4.Покупка тура с дебетовой картой с использованием невалидных данных (данные владельца на кириллице).")
    void aqaShopTest6() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue("Дмитрий Петров");
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("5.Покупка тура с дебетовой картой с использованием невалидных данных (поле владелец цифры).")
    void aqaShopTest7() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue("123456");
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("6.Покупка тура с дебетовой картой с использованием невалидных данных (поле владелец спецсимволы).")
    void aqaShopTest8() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue("!№;%:?");
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("7.Покупка тура с дебетовой картой с использованием невалидных данных (номер карты нули).")
    void aqaShopTest9() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("0000 0000 0000 0000");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
    @DisplayName("8.Покупка тура с дебетовой картой с использованием невалидных данных (поле месяц заполнить нулями).")
    void aqaShopTest10() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue("00");
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    @Test
    @DisplayName("9.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле номер карты).")
    void aqaShopTest11() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("10.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле владелец).")
    void aqaShopTest12() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue("");
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("11.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле месяц).")
    void aqaShopTest13() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue("");
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("12.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле год).")
    void aqaShopTest14() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4442");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue("");
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("13.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле cvc).")
    void aqaShopTest15() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue("");
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("14.Покупка тура с дебетовой картой с использованием невалидных данных (пустые поля).")
    void aqaShopTest16() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("");
        $(".input__control[placeholder='08']").setValue("");
        $(".input__control[placeholder='22']").setValue("");
        $(".input__control[value='']").setValue("");
        $(".input__control[placeholder='999']").setValue("");
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("15.Покупка тура с дебетовой картой с использованием невалидных данных (заполнить поле «cvc» двумя цифрами).")
    void aqaShopTest17() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue("36");
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("16.Покупка тура с дебетовой картой с использованием невалидных данных (заполнить поле «cvc» нулями).")
    void aqaShopTest18() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue("000");
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("17.Покупка тура с дебетовой картой с использованием невалидных данных (пустое поле год).")
    void aqaShopTest19() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4442");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue("00");
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Истёк срок действия карты"));
    }

    @Test
    @DisplayName("18.Покупка тура с дебетовой картой с использованием невалидных данных (карта не из набора).")
    void aqaShopTest20() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("3333 3333 3333 3333");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    }

    //Негативные сценарии покупки тура в кредит.

    @Test
    @DisplayName("1.Покупка тура в кредит с использованием невалидных данных (пустые поля).")
    void aqaShopTest21() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("");
        $(".input__control[placeholder='08']").setValue("");
        $(".input__control[placeholder='22']").setValue("");
        $(".input__control[value='']").setValue("");
        $(".input__control[placeholder='999']").setValue("");
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("2.Покупка тура в кредит с использованием невалидных данных (с прошедшим годом).")
    void aqaShopTest22() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue("21");
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Истёк срок действия карты"));
    }

    @Test
    @DisplayName("3.Покупка тура в кредит с использованием невалидных данных (с несуществующим месяцем).")
    void aqaShopTest23() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue("13");
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    @Test
    @DisplayName("4.Покупка тура в кредит с использованием невалидных данных (данные владельца на кириллице).")
    void aqaShopTest24() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue("Дмитрий Петров");
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("5.Покупка тура в кредит с использованием невалидных данных (поле владелец цифры).")
    void aqaShopTest25() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue("123456");
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("6.Покупка тура в кредит с использованием невалидных данных (поле владелец спецсимволы).")
    void aqaShopTest26() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue("!№;%:?");
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("7.Покупка тура в кредит с использованием невалидных данных (номер карты нули).")
    void aqaShopTest27() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("0000 0000 0000 0000");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    }

    @Test
    @DisplayName("8.Покупка тура в кредит с использованием невалидных данных (поле месяц заполнить нулями).")
    void aqaShopTest28() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue("00");
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    @Test
    @DisplayName("9.Покупка тура в кредит с использованием невалидных данных (пустое поле номер карты).")
    void aqaShopTest29() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("10.Покупка тура в кредит с использованием невалидных данных (пустое поле владелец).")
    void aqaShopTest30() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue("");
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("11.Покупка тура в кредит с использованием невалидных данных (пустое поле месяц).")
    void aqaShopTest31() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue("");
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("12.Покупка тура в кредит с использованием невалидных данных (пустое поле год).")
    void aqaShopTest32() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4442");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue("");
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("13.Покупка тура в кредит с использованием невалидных данных (пустое поле cvc).")
    void aqaShopTest33() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue("");
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("14.Покупка тура в кредит с использованием невалидных данных (заполнить поле «cvc» двумя цифрами).")
    void aqaShopTest34() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue("36");
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("15.Покупка тура в кредит с использованием отказанной карты.")
    void aqaShopTest35() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4442");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Операция одобрена Банком."));
    }

    @Test
    @DisplayName("16.Покупка тура в кредит с использованием невалидных данных (заполнить поле «cvc» нулями).")
    void aqaShopTest36() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue("000");
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Неверный формат"));
    }

    @Test
    @DisplayName("17.Покупка тура в кредит с использованием невалидных данных (заполнить поле «год» нулями).")
    void aqaShopTest37() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("4444 4444 4444 4441");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue("00");
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".input__sub")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Истёк срок действия карты"));
    }

    @Test
    @DisplayName("18.Покупка тура в кредит с использованием невалидных данных (карта не из набора).")
    void aqaShopTest38() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        $(byText("Купить в кредит")).click();
        $(".input__control[placeholder='0000 0000 0000 0000']").setValue("3333 3333 3333 3333");
        $(".input__control[placeholder='08']").setValue(month);
        $(".input__control[placeholder='22']").setValue(year);
        $(".input__control[value='']").setValue(name);
        $(".input__control[placeholder='999']").setValue(cvc);
        $(byText("Продолжить")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    }
}
