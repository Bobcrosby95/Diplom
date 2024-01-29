package ru.netology.diplom.;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ShopTest {

    @Test
    void aqaShopTest1() {
        String month = DataHelper.generateMonth();
        String year = DataHelper.generateYear();
        String cvc = DataHelper.generateCVC();
        String name = DataHelper.generateName("en");
        open("http://localhost:8080");
        $(byText("Купить")).click();
        $(".input__control[placeholder='08']").setValue("01");
    }
}
