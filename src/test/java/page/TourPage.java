package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class TourPage {
    private final SelenideElement heading = $(".heading");
    private final SelenideElement buttonBuy = $(".button");
    private final SelenideElement buttonCredit = $(".button_view_extra");

    public void tourPage() {
        heading.shouldHave(Condition.text("Путешествие дня")).shouldBe(Condition.visible);
    }

    public PaymentPage paymentCard() {
        buttonBuy.click();
        return new PaymentPage();
    }

    public CreditPage paymentCredit() {
        buttonCredit.click();
        return new CreditPage();
    }
}