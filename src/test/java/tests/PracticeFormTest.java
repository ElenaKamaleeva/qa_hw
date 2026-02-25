package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class PracticeFormTest {

    @BeforeAll
    static void setup() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
    }

    @Test
    void fillFullPracticeForm() {


// Теперь заполняем форму
        open("https://app.qa.guru/automation-practice-form/");

        $("[data-testid='ClearIcon']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        sleep(1000);

        $("[data-testid='firstName'] input").sendKeys("Elena");

        $("[data-testid='lastName'] input").sendKeys("Kamaleeva");

        $("[data-testid='email'] input").sendKeys("test_qa_guru@test.com");

        $("[data-testid='phone']").sendKeys("1000000000");


        // Gender
        $("[data-testid='gender'][value='Female']").click();


        // Date of Birth
        $("[data-testid='dateOfBirth']").sendKeys("15/05/1990");


        // Subjects
        $("[data-testid='subjects']").parent().click();
        $("[data-value='Maths']").click();
        $("body").click(); // закрываем дропдаун

        // Language
        $("[data-testid='language']").parent().click();
        $("[data-value='Russian']").click();


        // Hobbies
        $("[data-testid='hobbies'][value='Sports']").click();
        $("[data-testid='hobbies'][value='Reading']").click();
        $("[data-testid='hobbies'][value='Music']").click();



        // State
        $("[data-testid='stateCity']").parent().$("[role='combobox']").click();
        $("[data-value='California']").click();

        $("#city-select option[value='San Diego']").shouldBe(visible);

        // City
        // Обращаться напрямую к select по id:
        $("#city-select").selectOption("San Diego");


        // Address
        $("[data-testid='address']").sendKeys("Limassol, Cyprus");


        // Upload picture
        $("input[type='file']").uploadFile(new File("src/test/resources/test.png"));

        // Submit
        $("[type='submit']").scrollIntoView(true).click();

        // Verify modal appeared
        //$("h4").shouldHave(text("Thank you for submitting the form"));
       // $("p").shouldHave(text("Elena"));
    }
}
