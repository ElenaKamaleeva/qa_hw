package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class GitHubIssueTests extends TestBase {

    private static final String ISSUE_URL =
            "https://github.com/microsoft/powerbi-visuals-utils-formattingutils/issues/36";
    private static final String EXPECTED_TITLE = "valueFormatter ignores culture";

    // Заголовок issue на GitHub находится в теге <bdi> внутри блока с классом .markdown-title
    // или в h1 > bdi — это актуальная разметка GitHub
    private static final String ISSUE_TITLE_SELECTOR = "h1 bdi";

    // ══════════════════════════════════════════════════════════════════════════
    // Вариант 1 — Чистый Selenide с Listener (AllureSelenide)
    // ══════════════════════════════════════════════════════════════════════════

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @Test
    void checkIssueTitleWithSelenideListener() {
        open(ISSUE_URL);

        $(ISSUE_TITLE_SELECTOR)
                .shouldBe(visible)
                .shouldHave(text(EXPECTED_TITLE));
    }


    // ══════════════════════════════════════════════════════════════════════════
    // Вариант 2 — Лямбда шаги через step("name", () -> {})
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    void checkIssueTitleWithLambdaSteps() {
        step("Open GitHub Issue page", () ->
                open(ISSUE_URL));

        step("Check that issue title is visible", () ->
                $(ISSUE_TITLE_SELECTOR).shouldBe(visible));

        step("Check that issue title text is correct", () ->
                $(ISSUE_TITLE_SELECTOR).shouldHave(text(EXPECTED_TITLE)));
    }


    // ══════════════════════════════════════════════════════════════════════════
    // Вариант 3 — Шаги с аннотацией @Step
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    void checkIssueTitleWithAnnotationSteps() {
        openIssuePage();
        checkIssueTitleIsVisible();
        checkIssueTitleText(EXPECTED_TITLE);
    }

    @Step("Open GitHub Issue page")
    private void openIssuePage() {
        open(ISSUE_URL);
    }

    @Step("Check that issue title is visible")
    private void checkIssueTitleIsVisible() {
        $(ISSUE_TITLE_SELECTOR).shouldBe(visible);
    }

    @Step("Check that issue title text equals '{expectedTitle}'")
    private void checkIssueTitleText(String expectedTitle) {
        $(ISSUE_TITLE_SELECTOR).shouldHave(text(expectedTitle));
    }
}