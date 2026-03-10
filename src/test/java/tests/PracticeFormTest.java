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

        $("[data-testid='firstName'] input").sendKeys("Elena");
        $("[data-testid='lastName'] input").sendKeys("Kamaleeva");
        $("[data-testid='email'] input").sendKeys("test_qa_guru@test.com");
        $("[data-testid='phone']").sendKeys("4153456789");

        // Gender
        $("[data-testid='gender'][value='Female']").click();

        // Date of Birth
        $("[data-testid='dateOfBirth']").click();
        $("[data-testid='dateOfBirth']").sendKeys("05");
        $("[data-testid='dateOfBirth']").sendKeys("05");
        $("[data-testid='dateOfBirth']").sendKeys("1990");
        $("[data-testid='dateOfBirth']").pressTab();

        // Subjects
        $("[data-testid='subjects']").parent().click();
        $("[data-value='Maths']").click();
        $("body").click();

        // Language
        $("[data-testid='language']").parent().click();
        $("[data-value='Russian']").click();

        // Hobbies
        $("[data-testid='hobbies'][value='Sports']").click();
        $("[data-testid='hobbies'][value='Reading']").click();
        $("[data-testid='hobbies'][value='Music']").click();

        // State
        $x("//input[@data-testid='stateCity']/preceding-sibling::div[@role='combobox']").click();
        $$("li").findBy(text("California")).click();

        // City
        $("#city-select").selectOption("San Diego");

        // Address
        $("[data-testid='address']").sendKeys("Limassol, Cyprus");

        // Upload picture
        $("input[type='file']").uploadFromClasspath("test.png");

        // Submit
        $("[type='submit']").scrollIntoView(true).click();

        $("[type='submit']").scrollIntoView(true).click();
        executeJavaScript("window.scrollTo(0, 0)");

        $x("//h4[text()='Thank you for submitting the form']")
                .shouldBe(visible, Duration.ofSeconds(10));

        // Проверяем поля
        $x("//p[text()='firstName']/following::p[1]").shouldHave(text("Elena"));
        $x("//p[text()='lastName']/following::p[1]").shouldHave(text("Kamaleeva"));
        $x("//p[text()='email']/following::p[1]").shouldHave(text("test_qa_guru@test.com"));
        $x("//p[text()='gender']/following::p[1]").shouldHave(text("Female"));
        $x("//p[text()='phone']/following::p[1]").shouldHave(text("+1 415 345 6789"));
        $x("//p[text()='dateOfBirth']/following::p[1]").shouldHave(text("05/05/1990"));
        $x("//p[text()='subjects']/following::p[1]").shouldHave(text("Maths"));
        $x("//p[text()='hobbies']/following::p[1]").shouldHave(text("Sports, Reading, Music"));
        $x("//p[text()='stateCity']/following::p[1]").shouldHave(text("California, San Diego"));
        $x("//p[text()='language']/following::p[1]").shouldHave(text("Russian"));
        $x("//p[text()='address']/following::p[1]").shouldHave(text("Limassol, Cyprus"));
        $x("//p[text()='file']/following::p[1]").shouldHave(text("test.png"));
    }
}