package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class ShopTest {

    @Test
    void aqaShopTest1() {
        open("http://localhost:8080");
    }
}
