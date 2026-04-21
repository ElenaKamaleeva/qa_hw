package tests.testdata;

import com.github.javafaker.Faker;

import java.util.Locale;

import static utils.RandomUtils.*;

public final class TestData {

    private TestData() {}

    private static final Faker faker = new Faker(new Locale("en"));

    // ── Personal info ─────────────────────────────────────────────────────────
    public static final String FIRST_NAME = faker.name().firstName();
    public static final String LAST_NAME  = faker.name().lastName();
    public static final String EMAIL      = getRandomEmail();

    // Phone фиксирован вместе с форматированной версией —
    // чтобы PHONE_FORMATTED гарантированно совпал с тем что показывает форма
    public static final String PHONE           = "4153456789";
    public static final String PHONE_FORMATTED = "+1 415 345 6789";

    // ── Gender ────────────────────────────────────────────────────────────────
    private static final String[] GENDERS = {"Male", "Female", "Other"};
    public static final String GENDER = getRandomItemFromArray(GENDERS);

    // ── Date of Birth ─────────────────────────────────────────────────────────
    // Фиксированные — генерация дат нестабильна для полей с конкретным форматом
    public static final String DATE_OF_BIRTH_DAY       = "05";
    public static final String DATE_OF_BIRTH_MONTH     = "05";
    public static final String DATE_OF_BIRTH_YEAR      = "1990";
    public static final String DATE_OF_BIRTH_FORMATTED = "05/05/1990";

    // ── Subject ───────────────────────────────────────────────────────────────
    private static final String[] SUBJECTS = {"Maths", "English", "Physics", "Chemistry"};
    public static final String SUBJECT = getRandomItemFromArray(SUBJECTS);

    // ── Language ──────────────────────────────────────────────────────────────
    private static final String[] LANGUAGES = {"Russian", "English", "Hindi"};
    public static final String LANGUAGE = getRandomItemFromArray(LANGUAGES);

    // ── Hobbies ───────────────────────────────────────────────────────────────
    // Фиксированные — нужны конкретные значения для проверки результата
    public static final String HOBBY_1           = "Sports";
    public static final String HOBBY_2           = "Reading";
    public static final String HOBBY_3           = "Music";
    public static final String HOBBIES_FORMATTED = "Sports, Reading, Music";

    // ── State & City ──────────────────────────────────────────────────────────
    // Фиксированные — City зависит от выбранного State
    public static final String STATE               = "California";
    public static final String CITY                = "San Diego";
    public static final String STATE_CITY_FORMATTED = "California, San Diego";

    // ── Address ───────────────────────────────────────────────────────────────
    public static final String ADDRESS = faker.address().fullAddress();

    // ── File ──────────────────────────────────────────────────────────────────
    public static final String FILE_NAME = "test.png";
}