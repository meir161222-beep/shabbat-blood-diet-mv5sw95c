package com.generated.app;

import java.util.ArrayList;
import java.util.List;

public class FoodDatabase {

    public static List<FoodItem> getAllFoods() {
        List<FoodItem> foods = new ArrayList<>();

        // Shabbat traditional foods & everyday healthy foods
        foods.add(new FoodItem("f1", "חלת שבת (פרוסה בינונית)", 135, "פרוסה (~45 גרם)", true,
                FoodItem.Compatibility.AVOID, FoodItem.Compatibility.AVOID, FoodItem.Compatibility.AVOID, FoodItem.Compatibility.AVOID,
                "פחמימה מורכבת ומתוקה. בסעודה, כדאי להסתפק בכזית לברכה"));

        foods.add(new FoodItem("f2", "חלה מחיטה מלאה / כוסמין", 95, "פרוסה (~40 גרם)", true,
                FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "בחירה מעולה לסעודות שבת ששומרת על שובע ועיכול קל"));

        foods.add(new FoodItem("f3", "יין אדום יבש לקידוש", 85, "כוסית (100 מ\"ל)", true,
                FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL,
                "מכיל נוגדי חמצון טבעיים. עדיף על מיץ ענבים עתיר סוכר"));

        foods.add(new FoodItem("f4", "מיץ ענבים טבעי לקידוש", 140, "כוסית (150 מ\"ל)", true,
                FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.NEUTRAL,
                "מכיל סוכר פירות טבעי, כדאי לשתות כמות מדודה"));

        foods.add(new FoodItem("f5", "צ'ולנט / חמין מסורתי עם בקר וקטניות", 380, "מנה בינונית (250 גרם)", true,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.AVOID, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.AVOID,
                "סוג O ו-B מעכלים בקר מצוין. סוג A ו-AB עדיף חמין צמחוני"));

        foods.add(new FoodItem("f6", "חמין צמחוני (גריסים, ירקות ופטריות)", 220, "מנה בינונית (250 גרם)", true,
                FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL,
                "עשיר בסיבים תזונתיים וקל מאוד לעיכול בשבת בצהריים"));

        foods.add(new FoodItem("f7", "סלמון אפוי בתנור ועשבי תיבול", 210, "מנה (120 גרם)", true,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "מלך שולחן השבת - עשיר באומגה 3 ואידיאלי לכל סוגי הדם"));

        foods.add(new FoodItem("f8", "גפילטע פיש מסורתי", 130, "קציצה בינונית (80 גרם)", true,
                FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "מנה חלבונית קלאסית עם מעט שומן"));

        foods.add(new FoodItem("f9", "מרק עוף צח עם ירקות", 95, "קערה (250 מ\"ל)", true,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.AVOID, FoodItem.Compatibility.AVOID,
                "מרק מנחם ומחמם. שימו לב: לסוג B ו-AB מומלץ מרק הודו"));

        foods.add(new FoodItem("f10", "חזה הודו בתנור", 145, "מנה (120 גרם)", false,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "חלבון רזה שמתאים מעולה לכל סוגי הדם"));

        foods.add(new FoodItem("f11", "חזה עוף על האש", 155, "מנה (120 גרם)", false,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.AVOID, FoodItem.Compatibility.AVOID,
                "קל לעיכול ודל שומן, מתאים במיוחד לסוג O ו-A"));

        foods.add(new FoodItem("f12", "סלט ירקות ישראלי עם שמן זית", 85, "קערה אישית", false,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "תוספת חובה לשולחן השבת ולכל ארוחה - שמן זית מומלץ מאוד"));

        foods.add(new FoodItem("f13", "טחינה גולמית משומשום מלא (מוכנה)", 110, "2 כפות (30 גרם)", true,
                FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.AVOID, FoodItem.Compatibility.NEUTRAL,
                "עשירה בסידן ושומן איכותי. לסוג B עדיף להמעיט בשומשום"));

        foods.add(new FoodItem("f14", "סלט חצילים קלוי בטחינה", 130, "3 כפות (60 גרם)", true,
                FoodItem.Compatibility.AVOID, FoodItem.Compatibility.AVOID, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "חצילים שייכים למשפחת הסולניים - מעולים לסוג B ו-AB"));

        foods.add(new FoodItem("f15", "ביצה קשה מחמין שבת", 78, "ביצה אחת", true,
                FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "חלבון מושלם לשבת בבוקר, משביע ומונע נשנושים מיותרים"));

        foods.add(new FoodItem("f16", "יוגורט יווני טבעי 5%", 120, "גביע (150 גרם)", false,
                FoodItem.Compatibility.AVOID, FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "מוצרי חלב מותססים מועילים ביותר לסוג B ו-AB"));

        foods.add(new FoodItem("f17", "דג טונה במים", 110, "קופסה (100 גרם מסונן)", true,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "חלבון קל ומהיר, אידיאלי לסעודה שלישית"));

        foods.add(new FoodItem("f18", "קינואה מבושלת עם ירקות", 140, "כוס מבושלת", false,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "דגן עתיק ללא גלוטן, מזין ומשביע לאורך זמן"));

        foods.add(new FoodItem("f19", "תפוח עץ ירוק", 75, "יחידה בינונית", false,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "פרי מושלם כנשנוש בין הארוחות, מנקה ומאזן את רמת הסוכר"));

        foods.add(new FoodItem("f20", "אגוזי מלך", 165, "חופן קטן (25 גרם)", true,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "שומן בריא מגן לב, נפלא כקינוח בריא אחרי סעודת שבת"));

        foods.add(new FoodItem("f21", "קוגל ירושלמי מתוק", 290, "פרוסה קטנה (100 גרם)", true,
                FoodItem.Compatibility.AVOID, FoodItem.Compatibility.AVOID, FoodItem.Compatibility.AVOID, FoodItem.Compatibility.AVOID,
                "עתיר סוכר ושמן. מומלץ לקחת טעימה קטנה בלבד לכבוד שבת"));

        foods.add(new FoodItem("f22", "עגבנייה טרייה", 22, "יחידה בינונית", false,
                FoodItem.Compatibility.AVOID, FoodItem.Compatibility.AVOID, FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.NEUTRAL,
                "סולנית - מתאימה לסוג B ו-AB, סוג O ו-A עדיף לגוון בירקות ירוקים"));

        foods.add(new FoodItem("f23", "ברוקולי מאודה עם שום", 55, "כוס מלאה (150 גרם)", false,
                FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "נוגד חמצון מובהק ומחזק את המערכת החיסונית לכל סוגי הדם"));

        foods.add(new FoodItem("f24", "אורז בסמטי / מלא", 150, "כוס מבושלת", false,
                FoodItem.Compatibility.NEUTRAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL, FoodItem.Compatibility.BENEFICIAL,
                "תוספת מאוזנת וקלה לעיכול בשילוב עם תבשילי שבת"));

        return foods;
    }
}
