package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SqlHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.TourPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyTourByCard {
    String approvedCardNumber = DataHelper.getApproved().getCardNumber();
    String declinedCardNumber = DataHelper.getDeclined().getCardNumber();
    String randomCardNumber = DataHelper.generateRandomCardNumber();
    String validMonth = DataHelper.generateMonth(1);
    String validYear = DataHelper.generateYear(1);
    String validOwner = DataHelper.generateOwner();
    String validOwnerRus = DataHelper.generateOwnerRus();
    String validOwnerWithNumber = DataHelper.generateOwnerWithNumbers();
    String validCode = DataHelper.generateCVC();
    TourPage tour;

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
        tour = new TourPage();
    }

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterAll
    public static void shouldCleanBase() {
        SqlHelper.cleanBase();
    }


    @Test
    @DisplayName("Покупка тура по карте со всеми валидными значениями со статусом карты “Approved”")
    public void shouldBuyWithApprovedCard() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        paymentPage.cleanFields();
        paymentPage.fillForm(approvedCardNumber, validMonth, validYear, validOwner, validCode);
        paymentPage.paymentApproved();
        Assertions.assertEquals("APPROVED", SqlHelper.getCardPayment());
    }

    @Test
    @DisplayName("Покупка тура по карте со всеми валидными значениями со статусом карты “Declined”")
    public void shouldBuyWithDeclinedCard() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        paymentPage.cleanFields();
        paymentPage.fillForm(declinedCardNumber, validMonth, validYear, validOwner, validCode);
        paymentPage.paymentDeclined();
        Assertions.assertEquals("DECLINED", SqlHelper.getCardPayment());
    }

    @Test
    @DisplayName("Покупка тура по карте с невалидным значением месяца со статусом карты “Approved”")
    public void notShouldBuyWithApprovedCardWithInvalidMonth() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        var invalidMonth = "13";
        paymentPage.cleanFields();
        paymentPage.fillForm(approvedCardNumber, invalidMonth, validYear, validOwner, validCode);
        paymentPage.incorrectPeriodCard();
    }

    @Test
    @DisplayName("Покупка тура по карте с невалидным значением месяца со статусом карты “Declined”")
    public void notShouldBuyWithDeclinedCardWithInvalidMonth() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        var invalidMonth = "13";
        paymentPage.cleanFields();
        paymentPage.fillForm(declinedCardNumber, invalidMonth, validYear, validOwner, validCode);
        paymentPage.incorrectPeriodCard();
    }

    @Test
    @DisplayName("Покупка тура по карте с истекшим значением года со статусом карты “Approved”")
    public void notShouldBuyWithApprovedCardWithExpiredPeriod() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        var yearExpired = DataHelper.generateYear(-1);
        paymentPage.cleanFields();
        paymentPage.fillForm(approvedCardNumber, validMonth, yearExpired, validOwner, validCode);
        paymentPage.expiredCard();
    }

    @Test
    @DisplayName("Покупка тура по карте с истекшим значением года со статусом карты “Declined”")
    public void notShouldBuyWithDeclinedCardWithExpiredPeriod() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        var yearExpired = DataHelper.generateYear(-1);
        paymentPage.cleanFields();
        paymentPage.fillForm(declinedCardNumber, validMonth, yearExpired, validOwner, validCode);
        paymentPage.expiredCard();
    }

    @Test
    @DisplayName("Покупка тура по карте с пустыми значениями в форме")
    public void shouldSendFormWithEmptyFields() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        var emptyCardNumber = "";
        var emptyMonth = "";
        var emptyYear = "";
        var emptyOwner = "";
        var emptyCVC = "";
        paymentPage.cleanFields();
        paymentPage.fillForm(emptyCardNumber, emptyMonth, emptyYear, emptyOwner, emptyCVC);
        paymentPage.incorrectFormat();
    }

    @Test
    @DisplayName("Покупка тура по карте с несуществующим номером карты")
    public void notShouldBuyWithRandomCard() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        paymentPage.cleanFields();
        paymentPage.fillForm(randomCardNumber, validMonth, validYear, validOwner, validCode);
        paymentPage.paymentDeclined();
    }

    @Test
    @DisplayName("Покупка тура по карте со значением ФИО на кириллице")
    public void notShouldBuyWithOwnerRussianName() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        var ownerRussianName = DataHelper.generateOwnerRus();
        paymentPage.cleanFields();
        paymentPage.fillForm(approvedCardNumber, validMonth, validYear, validOwnerRus, validCode);
        paymentPage.paymentDeclined();
    }

    @Test
    @DisplayName("Покупка тура по карте со значением ФИО цифрами")
    public void notShouldBuyWithOwnerWithNumbers() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        var ownerNumber = DataHelper.generateOwnerWithNumbers();
        paymentPage.cleanFields();
        paymentPage.fillForm(approvedCardNumber, validMonth, validYear, validOwnerWithNumber, validCode);
        paymentPage.paymentDeclined();
    }

    @Test
    @DisplayName("Покупка тура по карте со значением ФИО специальными символами")
    public void notShouldBuyWithOwnerWithSpecialSymbols() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        var ownerSpecialSymbols = "!@#$%&";
        paymentPage.cleanFields();
        paymentPage.fillForm(approvedCardNumber, validMonth, validYear, ownerSpecialSymbols, validCode);
        paymentPage.paymentDeclined();
    }

    @Test
    @DisplayName("Покупка тура по карте со значением CVC/CVV одной цифрой")
    public void notShouldBuyWithDigitsCVCCode() {
        tour.tourPage();
        var paymentPage = tour.paymentCard();
        var twoDigitsCVC = DataHelper.generateCVC().length() - 1;
        paymentPage.cleanFields();
        paymentPage.fillForm(approvedCardNumber, validMonth, validYear, validOwner, String.valueOf(twoDigitsCVC));
        paymentPage.incorrectFormat();
    }
}

