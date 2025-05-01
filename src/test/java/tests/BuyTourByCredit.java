package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SqlHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.TourPage;

import static com.codeborne.selenide.Selenide.open;

public class BuyTourByCredit {
    String approvedCardNumber = DataHelper.getApproved().getCardNumber();
    String declinedCardNumber = DataHelper.getDeclined().getCardNumber();
    String randomCardNumber = DataHelper.generateRandomCardNumber();
    String validMonth = DataHelper.generateMonth(1);
    String validYear = DataHelper.generateYear(1);
    String validOwner = DataHelper.generateOwner();
    String validOwnerRus = DataHelper.generateOwnerRus();
    String validOwnerWithNumber = DataHelper.generateOwnerWithNumbers();
    String validCode = DataHelper.generateCVC();

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
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
    @DisplayName("Покупка тура в кредит со всеми валидными значениями со статусом карты “Approved”")
    public void shouldBuyWithApprovedCard() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        creditPage.cleanFields();
        creditPage.fillForm(approvedCardNumber, validMonth, validYear, validOwner, validCode);
        creditPage.paymentApproved();
        Assertions.assertEquals("APPROVED", SqlHelper.getCreditPayment());
    }

    @Test
    @DisplayName("Покупка тура в кредит со всеми валидными значениями со статусом карты “Declined”")
    public void shouldBuyWithDeclinedCard() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        creditPage.cleanFields();
        creditPage.fillForm(declinedCardNumber, validMonth, validYear, validOwner, validCode);
        creditPage.paymentDeclined();
        Assertions.assertEquals("DECLINED", SqlHelper.getCreditPayment());
    }

    @Test
    @DisplayName("Покупка тура в кредит с невалидным значением месяца со статусом карты “Approved”")
    public void notShouldBuyWithApprovedCardWithInvalidMonth() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        var invalidMonth = "13";
        creditPage.cleanFields();
        creditPage.fillForm(approvedCardNumber, invalidMonth, validYear, validOwner, validCode);
        creditPage.incorrectPeriodCard();
    }

    @Test
    @DisplayName("Покупка тура в кредит с невалидным значением месяца со статусом карты “Declined”")
    public void notShouldBuyWithDeclinedCardWithInvalidMonth() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        var invalidMonth = "13";
        creditPage.cleanFields();
        creditPage.fillForm(declinedCardNumber, invalidMonth, validYear, validOwner, validCode);
        creditPage.incorrectPeriodCard();
    }

    @Test
    @DisplayName("Покупка тура в кредит с истекшим значением года со статусом карты “Approved”")
    public void notShouldBuyWithApprovedCardWithExpiredPeriod() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        var yearExpired = DataHelper.generateYear(-1);
        creditPage.cleanFields();
        creditPage.fillForm(approvedCardNumber, validMonth, yearExpired, validOwner, validCode);
        creditPage.expiredCard();
    }

    @Test
    @DisplayName("Покупка тура в кредит с истекшим значением года со статусом карты “Declined”")
    public void notShouldBuyWithDeclinedCardWithExpiredPeriod() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        var yearExpired = DataHelper.generateYear(-1);
        creditPage.cleanFields();
        creditPage.fillForm(declinedCardNumber, validMonth, yearExpired, validOwner, validCode);
        creditPage.expiredCard();
    }

    @Test
    @DisplayName("Покупка тура в кредит с пустыми значениями в форме")
    public void shouldSendFormWithEmptyFields() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        var emptyCardNumber = "";
        var emptyMonth = "";
        var emptyYear = "";
        var emptyOwner = "";
        var emptyCVC = "";
        creditPage.cleanFields();
        creditPage.fillForm(emptyCardNumber, emptyMonth, emptyYear, emptyOwner, emptyCVC);
        creditPage.incorrectFormat();
    }

    @Test
    @DisplayName("Покупка тура в кредит с несуществующим номером карты")
    public void notShouldBuyWithRandomCard() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        creditPage.cleanFields();
        creditPage.fillForm(randomCardNumber, validMonth, validYear, validOwner, validCode);
        creditPage.paymentDeclined();
    }

    @Test
    @DisplayName("Покупка тура в кредит со значением ФИО на кириллице")
    public void notShouldBuyWithOwnerRussianName() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        var ownerRussianName = DataHelper.generateOwnerRus();
        creditPage.cleanFields();
        creditPage.fillForm(approvedCardNumber, validMonth, validYear, validOwnerRus, validCode);
        creditPage.paymentDeclined();
    }

    @Test
    @DisplayName("Покупка тура в кредит со значением ФИО цифрами")
    public void notShouldBuyWithOwnerWithNumbers() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        var ownerNumber = DataHelper.generateOwnerWithNumbers();
        creditPage.cleanFields();
        creditPage.fillForm(approvedCardNumber, validMonth, validYear, validOwnerWithNumber, validCode);
        creditPage.paymentDeclined();
    }

    @Test
    @DisplayName("Покупка тура в кредит со значением ФИО специальными символами")
    public void notShouldBuyWithOwnerWithSpecialSymbols() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        var ownerSpecialSymbols = "!@#$%&";
        creditPage.cleanFields();
        creditPage.fillForm(approvedCardNumber, validMonth, validYear, ownerSpecialSymbols, validCode);
        creditPage.paymentDeclined();
    }

    @Test
    @DisplayName("Покупка тура в кредит со значением CVC/CVV одной цифрой")
    public void notShouldBuyWithDigitsCVCCode() {
        TourPage tour = new TourPage();
        tour.tourPage();
        var creditPage = tour.paymentCredit();
        var twoDigitsCVC = DataHelper.generateCVC().length() - 1;
        creditPage.cleanFields();
        creditPage.fillForm(approvedCardNumber, validMonth, validYear, validOwner, String.valueOf(twoDigitsCVC));
        creditPage.incorrectFormat();
    }
}
