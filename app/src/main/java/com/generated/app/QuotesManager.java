package com.generated.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuotesManager {
    private static final List<String> QUOTES = new ArrayList<>();
    private static final Random RANDOM = new Random();

    static {
        QUOTES.add("״ונשמרתם מאד לנפשותיכם״ - הבריאות של גופך היא שליחות של הנפש.");
        QUOTES.add("הרמב״ם לימד: ״יהיו מזונותיך תרופותיך״. כל בחירה בריאה היום היא ברכה לגופך.");
        QUOTES.add("שבת נועדה לעונג ולשלווה – אכילה מתונה ומודעת מעניקה עונג שבת אמיתי ללא כבדות!");
        QUOTES.add("אין מושלמות, יש התמדה! גם אם חרגת מעט – המשך הלאה ברוגע ובאהבה עצמית.");
        QUOTES.add("כל כוס מים וכל מנה בריאה שבחרת היום היא ניצחון קטן של כוח הרצון שלך.");
        QUOTES.add("הגוף שלך הוא יצירת מופת שניתנה לך לשמור עליה. תן לו את המזון הנכון באהבה.");
        QUOTES.add("שולחן השבת מלא בשפע, והעוצמה האמיתית היא לטעום בנחת ולשבוע בשמחה.");
        QUOTES.add("״איזהו גיבור? הכובש את יצרו״ - גם בהחלטה לעצור כששבעים.");
        QUOTES.add("צעד אחד קטן בכל יום בונה אורח חיים איתן, בריא ומלא אנרגיה!");
        QUOTES.add("אכילה רגועה בשבת מביאה שמחה, קלילות ושלווה לכל בני הבית.");
    }

    public static String getRandomQuote() {
        return QUOTES.get(RANDOM.nextInt(QUOTES.size()));
    }
}
