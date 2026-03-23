package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.PracticeFormPage;

import static tests.testdata.TestData.*;

public class PracticeFormTest {

    private final PracticeFormPage practiceFormPage = new PracticeFormPage();

    @BeforeAll
    static void setup() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl = "https://app.qa.guru";
    }

    @Test
    void fillFullPracticeForm() {
        practiceFormPage
                .open()
                .fillFirstName(FIRST_NAME)
                .fillLastName(LAST_NAME)
                .fillEmail(EMAIL)
                .fillPhone(PHONE)
                .selectGender(GENDER)
                .fillDateOfBirth(DATE_OF_BIRTH_DAY, DATE_OF_BIRTH_MONTH, DATE_OF_BIRTH_YEAR)
                .selectSubject(SUBJECT)
                .selectLanguage(LANGUAGE)
                .selectHobbies(HOBBY_1, HOBBY_2, HOBBY_3)
                .selectState(STATE)
                .selectCity(CITY)
                .fillAddress(ADDRESS)
                .uploadFile(FILE_NAME)
                .submit()
                .verifySuccessMessage()
                .verifyResult("firstName", FIRST_NAME)
                .verifyResult("lastName", LAST_NAME)
                .verifyResult("email", EMAIL)
                .verifyResult("gender", GENDER)
                .verifyResult("phone", PHONE_FORMATTED)
                .verifyResult("dateOfBirth", DATE_OF_BIRTH_FORMATTED)
                .verifyResult("subjects", SUBJECT)
                .verifyResult("hobbies", HOBBIES_FORMATTED)
                .verifyResult("stateCity", STATE_CITY_FORMATTED)
                .verifyResult("language", LANGUAGE)
                .verifyResult("address", ADDRESS)
                .verifyResult("file", FILE_NAME);
    }
}