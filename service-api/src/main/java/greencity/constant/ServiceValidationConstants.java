package greencity.constant;

public final class ServiceValidationConstants {
    public static final int ADVICE_MIN_LENGTH = 3;
    public static final int ADVICE_MAX_LENGTH = 300;
    public static final int MIN_AMOUNT_OF_ITEMS = 0;
    public static final int MAX_AMOUNT_OF_ITEMS = 16;
    public static final String CATEGORY_NAME_BAD_FORMED = "{greenCity.validation.bad.formed.category.name}";
    public static final int CATEGORY_NAME_MIN_LENGTH = 3;
    public static final int CATEGORY_NAME_MAX_LENGTH = 30;
    public static final int MAX_AMOUNT_OF_TAGS = 3;
    public static final int HABIT_FACT_MIN_LENGTH = 3;
    public static final int HABIT_FACT_MAX_LENGTH = 300;
    public static final int USERNAME_MIN_LENGTH = 6;
    public static final int USERNAME_MAX_LENGTH = 30;
    public static final String INVALID_EMAIL = "{greenCity.validation.invalid.email}";
    public static final String INVALID_USERNAME = "{greenCity.validation.invalid.username}";
    public static final String INVALID_PASSWORD = "{greenCity.validation.invalid.password}";
    public static final String MIN_AMOUNT_OF_TAGS = "{greenCity.validation.empty.tags}";
    public static final int MAX_AMOUNT_OF_SOCIAL_NETWORK_LINKS = 5;
    public static final int COMMENT_MIN_LENGTH = 5;
    public static final int COMMENT_MAX_LENGTH = 300;
    public static final String LAT_MIN_VALIDATION = "{greenCity.validation.min.lat}";
    public static final String LAT_MAX_VALIDATION = "{greenCity.validation.max.lat}";
    public static final String LNG_MIN_VALIDATION = "{greenCity.validation.min.lng}";
    public static final String LNG_MAX_VALIDATION = "{greenCity.validation.max.lng}";
    public static final String RATE_MIN_VALUE = "{greenCity.validation.min.rate}";
    public static final String RATE_MAX_VALUE = "{greenCity.validation.max.rate}";
    public static final String HABIT_COMPLEXITY = "{greenCity.validation.habit.complexity}";
    public static final String TAG_LIST_MIN_LENGTH = "{greenCity.validation.min.tags}";
    public static final String TAG_LIST_MAX_LENGTH = "{greenCity.validation.max.tags}";

    private ServiceValidationConstants() {
    }
}
