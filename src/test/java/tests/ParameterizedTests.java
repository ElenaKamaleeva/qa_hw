package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ParameterizedTests extends TestBase {

    // ── 1. @ValueSource ───────────────────────────────────────────────────────
    // Самый простой провайдер — список значений одного типа.
    // Проверяем что страницы элементов demoqa открываются и содержат заголовок.

    @ValueSource(strings = {
            "text-box",
            "check-box",
            "radio-button",
            "buttons",
            "links"
    })
    @ParameterizedTest(name = "Page ''{0}'' opens and has a heading")
    void demoqaPageOpensTest(String page) {
        open("https://demoqa.com/" + page);
        $(".main-header").shouldBe(visible);
    }


    // ── 2. @CsvSource ─────────────────────────────────────────────────────────
    // Несколько параметров в одном тесте — пары значений.
    // Проверяем что при вводе текста в Text Box поле принимает значение.

    @CsvSource({
            "John,       john@example.com",
            "Jane Smith, jane.smith@mail.co.uk",
            "Иван,       ivan@test.ru",
            "A,          a@b.io"
    })
    @ParameterizedTest(name = "Text Box accepts name=''{0}'' and email=''{1}''")
    void textBoxAcceptsInputTest(String name, String email) {
        open("https://demoqa.com/text-box");
        $("#userName").setValue(name);
        $("#userEmail").setValue(email);

        $("#userName").shouldHave(value(name));
        $("#userEmail").shouldHave(value(email));
    }


    // ── 3. @MethodSource ──────────────────────────────────────────────────────
    // Самый гибкий провайдер — данные из статического метода.
    // Позволяет передавать объекты, генерировать данные динамически.
    // Проверяем что radio buttons на странице кликабельны и выбираются корректно.

    static Stream<String> radioButtonValues() {
        return Stream.of("Yes", "Impressive");
        // "No" намеренно не включён — на demoqa он disabled, хороший кейс для проверки
    }

    @MethodSource("radioButtonValues")
    @ParameterizedTest(name = "Radio button ''{0}'' can be selected")
    void radioButtonSelectionTest(String value) {
        open("https://demoqa.com/radio-button");
        $x("//label[text()='" + value + "']").click();
        $x("//label[text()='" + value + "']")
                .shouldHave(cssClass("custom-control-label"));
        $(".mt-3").shouldHave(text(value));
    }
}