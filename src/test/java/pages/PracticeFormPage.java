package pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormPage {

    // ── Locators ────────────────────────────────────────────────────────────

    private final SelenideElement clearIcon         = $("[data-testid='ClearIcon']");
    private final SelenideElement firstNameInput    = $("[data-testid='firstName'] input");
    private final SelenideElement lastNameInput     = $("[data-testid='lastName'] input");
    private final SelenideElement emailInput        = $("[data-testid='email'] input");
    private final SelenideElement phoneInput        = $("[data-testid='phone']");
    private final SelenideElement dateOfBirthInput  = $("[data-testid='dateOfBirth']");
    private final SelenideElement subjectsDropdown  = $("[data-testid='subjects']");
    private final SelenideElement languageDropdown  = $("[data-testid='language']");
    private final SelenideElement addressInput      = $("[data-testid='address']");
    private final SelenideElement fileInput         = $("input[type='file']");
    private final SelenideElement submitButton      = $("[type='submit']");
    private final SelenideElement citySelect        = $("#city-select");
    private final SelenideElement stateCombobox     =
            $x("//input[@data-testid='stateCity']/preceding-sibling::div[@role='combobox']");
    private final SelenideElement successHeading    =
            $x("//h4[text()='Thank you for submitting the form']");

    // ── Actions ─────────────────────────────────────────────────────────────

    public PracticeFormPage open() {
        com.codeborne.selenide.Selenide.open("/automation-practice-form/");
        clearIcon.shouldBe(visible, Duration.ofSeconds(10)).click();
        return this;
    }

    public PracticeFormPage fillFirstName(String firstName) {
        firstNameInput.sendKeys(firstName);
        return this;
    }

    public PracticeFormPage fillLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
        return this;
    }

    public PracticeFormPage fillEmail(String email) {
        emailInput.sendKeys(email);
        return this;
    }

    public PracticeFormPage fillPhone(String phone) {
        phoneInput.sendKeys(phone);
        return this;
    }

    public PracticeFormPage selectGender(String gender) {
        $("[data-testid='gender'][value='" + gender + "']").click();
        return this;
    }

    public PracticeFormPage fillDateOfBirth(String day, String month, String year) {
        dateOfBirthInput.click();
        dateOfBirthInput.sendKeys(day);
        dateOfBirthInput.sendKeys(month);
        dateOfBirthInput.sendKeys(year);
        dateOfBirthInput.pressTab();
        return this;
    }

    public PracticeFormPage selectSubject(String subject) {
        subjectsDropdown.parent().click();
        $("[data-value='" + subject + "']").click();
        $("body").click();
        return this;
    }

    public PracticeFormPage selectLanguage(String language) {
        languageDropdown.parent().click();
        $("[data-value='" + language + "']").click();
        return this;
    }

    public PracticeFormPage selectHobbies(String... hobbies) {
        for (String hobby : hobbies) {
            $("[data-testid='hobbies'][value='" + hobby + "']").click();
        }
        return this;
    }

    public PracticeFormPage selectState(String state) {
        stateCombobox.click();
        $$("li").findBy(text(state)).click();
        return this;
    }

    public PracticeFormPage selectCity(String city) {
        citySelect.selectOption(city);
        return this;
    }

    public PracticeFormPage fillAddress(String address) {
        addressInput.sendKeys(address);
        return this;
    }

    public PracticeFormPage uploadFile(String fileName) {
        fileInput.uploadFromClasspath(fileName);
        return this;
    }

    public PracticeFormPage submit() {
        submitButton.scrollIntoView(true).click();
        submitButton.scrollIntoView(true).click();
        executeJavaScript("window.scrollTo(0, 0)");
        return this;
    }

    // ── Assertions ───────────────────────────────────────────────────────────

    public PracticeFormPage verifySuccessMessage() {
        successHeading.shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    public PracticeFormPage verifyResult(String field, String expected) {
        $x("//p[text()='" + field + "']/following::p[1]").shouldHave(text(expected));
        return this;
    }
}
