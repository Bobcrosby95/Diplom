package ru.netology.diplom.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.netology.diplom.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement debit = $(byText("Купить"));
    private final SelenideElement credit = $(byText("Купить в кредит"));

    public DebitPage debitBuy() {
        debit.click();
        return new DebitPage();
    }

    public CreditPage creditBuy() {
        credit.click();
        return new CreditPage();
    }
}
