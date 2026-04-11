package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DatePickerComponent {

    private final SelenideElement dateInput = $("[data-testid='dateOfBirth']");

    public void setDate(String day, String month, String year) {
        dateInput.click();
        dateInput.sendKeys(day);
        dateInput.sendKeys(month);
        dateInput.sendKeys(year);
        dateInput.pressTab();
    }
}