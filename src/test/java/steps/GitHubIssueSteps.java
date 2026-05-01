package steps;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class GitHubIssueSteps {

    @Step("Open GitHub Issue page: {url}")
    public void openIssuePage(String url) {
        open(url);
    }

    @Step("Check that issue title is visible")
    public void checkIssueTitleIsVisible(String selector) {
        $(selector).shouldBe(visible);
    }

    @Step("Check that issue title text equals '{expectedTitle}'")
    public void checkIssueTitleText(String selector, String expectedTitle) {
        $(selector).shouldHave(text(expectedTitle));
    }
}