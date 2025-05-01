package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {

    private final SelenideElement heading = $("button ~ h3.heading");
    private final SelenideElement numberCardField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement ownerField = $$(".input__inner").findBy(text("Владелец")).$(".input__control");
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement continueButton = $$(".button__content").findBy(text("Продолжить"));
    private final SelenideElement paymentApproved = $$(".notification__title").findBy(text("Успешно"));
    private final SelenideElement paymentDeclined = $$(".notification__content").findBy(text("Ошибка! Банк отказал в проведении операции"));
    private final SelenideElement incorrectFormat = $$(".input__sub").findBy(text("Неверный формат"));
    private final SelenideElement requiredField = $$(".input__sub").findBy(text("Поле обязательно для заполнения"));
    private final SelenideElement expiredCard = $$(".input__sub").findBy(text("Истёк срок действия карты"));
    private final SelenideElement incorrectPeriodCard = $$(".input__sub").findBy(text("Неверно указан срок действия карты"));



    public CreditPage() {
        heading.shouldBe(visible);
    }
    public void fillForm(String cardNumber, String month, String year, String owner, String cvcCode) {
        numberCardField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        cvcField.setValue(cvcCode);
        continueButton.click();
    }

    public void paymentApproved() {
        paymentApproved.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void paymentDeclined() {
        paymentDeclined.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void incorrectFormat() {
        incorrectFormat.shouldBe(visible);
    }

    public void requiredField() {
        requiredField.shouldBe(visible);
    }

    public void expiredCard() {
        expiredCard.shouldBe(visible);
    }

    public void incorrectPeriodCard() {
        incorrectPeriodCard.shouldBe(visible);
    }

    public void cleanFields() {
        numberCardField.doubleClick().sendKeys(Keys.BACK_SPACE);
        monthField.doubleClick().sendKeys(Keys.BACK_SPACE);
        yearField.doubleClick().sendKeys(Keys.BACK_SPACE);
        ownerField.doubleClick().sendKeys(Keys.BACK_SPACE);
        cvcField.doubleClick().sendKeys(Keys.BACK_SPACE);
    }
}
