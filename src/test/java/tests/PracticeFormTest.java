package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.$;

public class PracticeFormTest {

    // Test data
    private static final String FIRST_NAME = "Elena";
    private static final String LAST_NAME = "Kamaleeva";
    private static final String EMAIL = "test_qa_guru@test.com";
    private static final String PHONE = "4153456789";
    private static final String PHONE_FORMATTED = "+1 415 345 6789";
    private static final String GENDER = "Female";
    private static final String DATE_OF_BIRTH_DAY = "05";
    private static final String DATE_OF_BIRTH_MONTH = "05";
    private static final String DATE_OF_BIRTH_YEAR = "1990";
    private static final String DATE_OF_BIRTH_FORMATTED = "05/05/1990";
    private static final String SUBJECT = "Maths";
    private static final String LANGUAGE = "Russian";
    private static final String HOBBY_1 = "Sports";
    private static final String HOBBY_2 = "Reading";
    private static final String HOBBY_3 = "Music";
    private static final String HOBBIES_FORMATTED = "Sports, Reading, Music";
    private static final String STATE = "California";
    private static final String CITY = "San Diego";
    private static final String STATE_CITY_FORMATTED = "California, San Diego";
    private static final String ADDRESS = "Limassol, Cyprus";
    private static final String FILE_NAME = "test.png";

    @BeforeAll
    static void setup() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl = "https://app.qa.guru";
    }

    @Test
    void fillFullPracticeForm() {

        open("/automation-practice-form/");

        $("[data-testid='ClearIcon']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        $("[data-testid='firstName'] input").sendKeys(FIRST_NAME);
        $("[data-testid='lastName'] input").sendKeys(LAST_NAME);
        $("[data-testid='email'] input").sendKeys(EMAIL);
        $("[data-testid='phone']").sendKeys(PHONE);

        // Gender
        $("[data-testid='gender'][value='" + GENDER + "']").click();

        // Date of Birth
        $("[data-testid='dateOfBirth']").click();
        $("[data-testid='dateOfBirth']").sendKeys(DATE_OF_BIRTH_DAY);
        $("[data-testid='dateOfBirth']").sendKeys(DATE_OF_BIRTH_MONTH);
        $("[data-testid='dateOfBirth']").sendKeys(DATE_OF_BIRTH_YEAR);
        $("[data-testid='dateOfBirth']").pressTab();

        // Subjects
        $("[data-testid='subjects']").parent().click();
        $("[data-value='" + SUBJECT + "']").click();
        $("body").click();

        // Language
        $("[data-testid='language']").parent().click();
        $("[data-value='" + LANGUAGE + "']").click();

        // Hobbies
        $("[data-testid='hobbies'][value='" + HOBBY_1 + "']").click();
        $("[data-testid='hobbies'][value='" + HOBBY_2 + "']").click();
        $("[data-testid='hobbies'][value='" + HOBBY_3 + "']").click();

        // State
        $x("//input[@data-testid='stateCity']/preceding-sibling::div[@role='combobox']").click();
        $$("li").findBy(text(STATE)).click();

        // City
        $("#city-select").selectOption(CITY);

        // Address
        $("[data-testid='address']").sendKeys(ADDRESS);

        // Upload picture
        $("input[type='file']").uploadFromClasspath(FILE_NAME);

        // Submit
        $("[type='submit']").scrollIntoView(true).click();

        $("[type='submit']").scrollIntoView(true).click();
        executeJavaScript("window.scrollTo(0, 0)");

        $x("//h4[text()='Thank you for submitting the form']")
                .shouldBe(visible, Duration.ofSeconds(10));

        // Проверяем поля
        $x("//p[text()='firstName']/following::p[1]").shouldHave(text(FIRST_NAME));
        $x("//p[text()='lastName']/following::p[1]").shouldHave(text(LAST_NAME));
        $x("//p[text()='email']/following::p[1]").shouldHave(text(EMAIL));
        $x("//p[text()='gender']/following::p[1]").shouldHave(text(GENDER));
        $x("//p[text()='phone']/following::p[1]").shouldHave(text(PHONE_FORMATTED));
        $x("//p[text()='dateOfBirth']/following::p[1]").shouldHave(text(DATE_OF_BIRTH_FORMATTED));
        $x("//p[text()='subjects']/following::p[1]").shouldHave(text(SUBJECT));
        $x("//p[text()='hobbies']/following::p[1]").shouldHave(text(HOBBIES_FORMATTED));
        $x("//p[text()='stateCity']/following::p[1]").shouldHave(text(STATE_CITY_FORMATTED));
        $x("//p[text()='language']/following::p[1]").shouldHave(text(LANGUAGE));
        $x("//p[text()='address']/following::p[1]").shouldHave(text(ADDRESS));
        $x("//p[text()='file']/following::p[1]").shouldHave(text(FILE_NAME));
    }
}