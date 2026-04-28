package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class ParameterizedTests extends TestBase {

    // ── 1. @ValueSource ───────────────────────────────────────────────────────
    // Проверяем что страницы demoqa открываются и содержат заголовок h1

    @ValueSource(strings = {
            "text-box",
            "radio-button",
            "buttons",
            "links"
    })
    @ParameterizedTest(name = "Page ''{0}'' opens and has a heading")
    void demoqaPageOpensTest(String page) {
        open("https://demoqa.com/" + page);
        $("h1").shouldBe(visible);
    }


    // ── 2. @CsvSource ─────────────────────────────────────────────────────────
    // Проверяем что Text Box принимает разные комбинации имя + email

    @CsvSource({
            "John,       john@example.com",
            "Jane Smith, jane.smith@mail.co.uk",
            "A,          a@b.io"
    })
    @ParameterizedTest(name = "Text Box accepts name=''{0}'' and email=''{1}''")
    void textBoxAcceptsInputTest(String name, String email) {
        open("https://demoqa.com/text-box");
        $("#userName").setValue(name.trim());
        $("#userEmail").setValue(email.trim());

        $("#userName").shouldHave(com.codeborne.selenide.Condition.value(name.trim()));
        $("#userEmail").shouldHave(com.codeborne.selenide.Condition.value(email.trim()));
    }


    // ── 3. @MethodSource ──────────────────────────────────────────────────────
    // Проверяем что radio buttons кликабельны и результат отображается

    static Stream<String> radioButtonValues() {
        return Stream.of("Yes", "Impressive");
        // "No" не включён — на demoqa он disabled
    }

    @MethodSource("radioButtonValues")
    @ParameterizedTest(name = "Radio button ''{0}'' can be selected")
    void radioButtonSelectionTest(String value) {
        open("https://demoqa.com/radio-button");

        // Кликаем по label — так как input скрыт
        $x("//label[text()='" + value + "']").click();

        // Проверяем что результат содержит выбранное значение
        $(".mt-3").shouldBe(visible).shouldHave(text(value));
    }
}