package data;

import com.github.javafaker.Faker;
import lombok.Value;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.Value;
//
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        public String cardNumber;
        public String cardStatus;
    }

    public static CardInfo getApproved() {
        return new CardInfo("4444 4444 4444 4441", "APPROVED");
    }

    public static CardInfo getDeclined() {
        return new CardInfo("4444 4444 4444 4442", "DECLINED");
    }

    private static Faker FAKER = new Faker(new Locale("en"));
    private static Faker FAKERRus = new Faker(new Locale("ru"));

    public static String generateRandomCardNumber() {
        return FAKER.business().creditCardNumber();
    }

    public static String generateMonth(int month) {
        return LocalDate.now().plusMonths(month).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear(int year) {
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateOwner() {
        return FAKER.name().fullName();
    }

    public static String generateOwnerRus() {
        return FAKERRus.name().fullName();
    }

    public static String generateOwnerWithNumbers() {
        return FAKER.numerify("#### #### #### ####");
    }

    public static String generateCVC() {
        return FAKER.number().digits(3);
    }
}


//????    private static CardInfo generateValidCardInfo() {
//        return new CardInfo(getApproved(), generateMonth(), generateYear(), generateOwner(), generateCVC());
//    }
//
//????    private static CardInfo generateInValidCardInfo() {
//        return new CardInfo(getDeclined(), generateMonth(), generateYear(), generateOwner(), generateCVC());
//    }
//}


//    public static String getRandomShorterCardNumber() {
//        int shortNumber = faker.random().nextInt(16);
//        return faker.number().digits(shortNumber);
//    }
//    private static String getRandomMonth() {
//
//    }
//
//    private static String generateRandomLogin() {
//        return FAKER.name().username();
//    }
//
//    private static String generateRandomPassword() {
//        return FAKER.internet().password();
//    }
//
//    public static AuthInfo generateRandomUser() {
//        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
//    }
//
//    public static VerificationCode generateRandomVerificationCode() {
//        return new VerificationCode(FAKER.numerify("######"));
//    }
//
//    @Value
//    public static class AuthInfo{
//        String login;
//        String password;
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class VerificationCode {
//        String code;
//    }
//}
//
