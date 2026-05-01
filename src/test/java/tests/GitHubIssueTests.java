package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steps.GitHubIssueSteps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class GitHubIssueTests extends TestBase {

    private static final String ISSUE_URL =
            "https://github.com/microsoft/powerbi-visuals-utils-formattingutils/issues/36";
    private static final String EXPECTED_TITLE = "valueFormatter ignores culture";
    private static final String ISSUE_TITLE_SELECTOR = "h1 bdi";

    private final GitHubIssueSteps steps = new GitHubIssueSteps();

    @BeforeAll
    static void setUpGitHub() {
        Configuration.baseUrl = "https://github.com";
        Configuration.pageLoadTimeout = 30000;
    }

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
    // Вариант 3 — Шаги с аннотацией @Step (вынесены в GitHubIssueSteps)
    // ══════════════════════════════════════════════════════════════════════════

    @Test
    void checkIssueTitleWithAnnotationSteps() {
        steps.openIssuePage(ISSUE_URL);
        steps.checkIssueTitleIsVisible(ISSUE_TITLE_SELECTOR);
        steps.checkIssueTitleText(ISSUE_TITLE_SELECTOR, EXPECTED_TITLE);
    }
}