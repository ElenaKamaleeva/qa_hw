package pages.components;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class ResultTableComponent {

    public void verifyField(String field, String expected) {
        $x("//p[text()='" + field + "']/following::p[1]").shouldHave(text(expected));
    }
}