package com.generated.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String PREFS_NAME = "HealthyDietPrefs";
    private static final String KEY_BLOOD_TYPE = "key_blood_type";
    private static final String KEY_DAILY_TARGET = "key_daily_target";
    private static final String KEY_SHABBAT_BONUS = "key_shabbat_bonus";
    private static final String KEY_IS_SHABBAT_MODE = "key_is_shabbat_mode";
    private static final String KEY_CONSUMED_CALORIES = "key_consumed_calories";
    private static final String KEY_LOGGED_FOODS_DATA = "key_logged_foods_data";

    private SharedPreferences prefs;

    // State
    private String selectedBloodType = "O";
    private int weekdayTargetCalories = 1800;
    private int shabbatBonusCalories = 500;
    private boolean isShabbatMode = false;
    private int consumedCalories = 0;
    private final List<LoggedFood> loggedFoodsList = new ArrayList<>();
    private final List<FoodItem> allFoodItems = new ArrayList<>();

    // Active Tab tracking
    private int activeTab = 0; // 0=Tracker, 1=Blood, 2=Shabbat, 3=Settings
    private String currentFoodFilter = "ALL"; // ALL, BENEFICIAL, NEUTRAL, AVOID, SHABBAT
    private String currentSearchQuery = "";

    // Views
    private TextView tvActiveBloodAndDay;
    private TextView tvEncouragementQuote;
    private Button btnNextQuote;

    // Tabs
    private Button tabTracker;
    private Button tabBloodType;
    private Button tabShabbat;
    private Button tabSettings;

    private ScrollView viewTracker;
    private LinearLayout viewBloodType;
    private ScrollView viewShabbat;
    private ScrollView viewSettings;

    // Tracker Views
    private TextView tvCurrentModeIndicator;
    private Switch switchShabbatMode;
    private TextView tvCaloriesConsumed;
    private TextView tvCaloriesTarget;
    private TextView tvCaloriesRemaining;
    private ProgressBar progressCalories;
    private EditText etCustomFoodName;
    private EditText etCustomFoodCalories;
    private Button btnAddCustomFood;
    private Button btnQuickPickFromBloodList;
    private LinearLayout containerLoggedFoods;
    private TextView tvEmptyLogNotice;
    private Button btnClearDailyLog;

    // Blood Guide Views
    private TextView tvBloodGuideHeader;
    private EditText etSearchFood;
    private Button btnFilterAll;
    private Button btnFilterBeneficial;
    private Button btnFilterNeutral;
    private Button btnFilterAvoid;
    private Button btnFilterShabbatFoods;
    private LinearLayout containerFoodList;

    // Shabbat Views
    private TextView tvMeal1Budget;
    private TextView tvMeal2Budget;
    private TextView tvMeal3Budget;
    private Button btnApplyShabbatToday;

    // Settings Views
    private Spinner spinnerBloodType;
    private EditText etTargetWeekdayCalories;
    private EditText etTargetShabbatBonus;
    private Button btnSaveSettings;

    public static class LoggedFood {
        public String name;
        public int calories;

        public LoggedFood(String name, int calories) {
            this.name = name;
            this.calories = calories;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loadPreferences();

        allFoodItems.addAll(FoodDatabase.getAllFoods());

        initViews();
        setupNavigationTabs();
        setupTrackerListeners();
        setupBloodGuideListeners();
        setupShabbatViewListeners();
        setupSettingsListeners();

        // Refresh all views with loaded state
        updateEncouragementQuote();
        updateHeaderAndTotals();
        renderLoggedFoods();
        renderFoodGuideList();
        updateShabbatMealBudgets();
    }

    private void loadPreferences() {
        selectedBloodType = prefs.getString(KEY_BLOOD_TYPE, "O");
        weekdayTargetCalories = prefs.getInt(KEY_DAILY_TARGET, 1800);
        shabbatBonusCalories = prefs.getInt(KEY_SHABBAT_BONUS, 500);
        isShabbatMode = prefs.getBoolean(KEY_IS_SHABBAT_MODE, false);
        consumedCalories = prefs.getInt(KEY_CONSUMED_CALORIES, 0);

        String storedLogged = prefs.getString(KEY_LOGGED_FOODS_DATA, "");
        loggedFoodsList.clear();
        if (!storedLogged.isEmpty()) {
            String[] items = storedLogged.split(";;");
            for (String item : items) {
                String[] parts = item.split("::");
                if (parts.length == 2) {
                    try {
                        loggedFoodsList.add(new LoggedFood(parts[0], Integer.parseInt(parts[1])));
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_BLOOD_TYPE, selectedBloodType);
        editor.putInt(KEY_DAILY_TARGET, weekdayTargetCalories);
        editor.putInt(KEY_SHABBAT_BONUS, shabbatBonusCalories);
        editor.putBoolean(KEY_IS_SHABBAT_MODE, isShabbatMode);
        editor.putInt(KEY_CONSUMED_CALORIES, consumedCalories);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < loggedFoodsList.size(); i++) {
            LoggedFood lf = loggedFoodsList.get(i);
            sb.append(lf.name).append("::").append(lf.calories);
            if (i < loggedFoodsList.size() - 1) {
                sb.append(";;");
            }
        }
        editor.putString(KEY_LOGGED_FOODS_DATA, sb.toString());
        editor.apply();
    }

    private void initViews() {
        tvActiveBloodAndDay = findViewById(R.id.tvActiveBloodAndDay);
        tvEncouragementQuote = findViewById(R.id.tvEncouragementQuote);
        btnNextQuote = findViewById(R.id.btnNextQuote);

        tabTracker = findViewById(R.id.tabTracker);
        tabBloodType = findViewById(R.id.tabBloodType);
        tabShabbat = findViewById(R.id.tabShabbat);
        tabSettings = findViewById(R.id.tabSettings);

        viewTracker = findViewById(R.id.viewTracker);
        viewBloodType = findViewById(R.id.viewBloodType);
        viewShabbat = findViewById(R.id.viewShabbat);
        viewSettings = findViewById(R.id.viewSettings);

        // Tracker
        tvCurrentModeIndicator = findViewById(R.id.tvCurrentModeIndicator);
        switchShabbatMode = findViewById(R.id.switchShabbatMode);
        tvCaloriesConsumed = findViewById(R.id.tvCaloriesConsumed);
        tvCaloriesTarget = findViewById(R.id.tvCaloriesTarget);
        tvCaloriesRemaining = findViewById(R.id.tvCaloriesRemaining);
        progressCalories = findViewById(R.id.progressCalories);
        etCustomFoodName = findViewById(R.id.etCustomFoodName);
        etCustomFoodCalories = findViewById(R.id.etCustomFoodCalories);
        btnAddCustomFood = findViewById(R.id.btnAddCustomFood);
        btnQuickPickFromBloodList = findViewById(R.id.btnQuickPickFromBloodList);
        containerLoggedFoods = findViewById(R.id.containerLoggedFoods);
        tvEmptyLogNotice = findViewById(R.id.tvEmptyLogNotice);
        btnClearDailyLog = findViewById(R.id.btnClearDailyLog);

        // Blood Guide
        tvBloodGuideHeader = findViewById(R.id.tvBloodGuideHeader);
        etSearchFood = findViewById(R.id.etSearchFood);
        btnFilterAll = findViewById(R.id.btnFilterAll);
        btnFilterBeneficial = findViewById(R.id.btnFilterBeneficial);
        btnFilterNeutral = findViewById(R.id.btnFilterNeutral);
        btnFilterAvoid = findViewById(R.id.btnFilterAvoid);
        btnFilterShabbatFoods = findViewById(R.id.btnFilterShabbatFoods);
        containerFoodList = findViewById(R.id.containerFoodList);

        // Shabbat
        tvMeal1Budget = findViewById(R.id.tvMeal1Budget);
        tvMeal2Budget = findViewById(R.id.tvMeal2Budget);
        tvMeal3Budget = findViewById(R.id.tvMeal3Budget);
        btnApplyShabbatToday = findViewById(R.id.btnApplyShabbatToday);

        // Settings
        spinnerBloodType = findViewById(R.id.spinnerBloodType);
        etTargetWeekdayCalories = findViewById(R.id.etTargetWeekdayCalories);
        etTargetShabbatBonus = findViewById(R.id.etTargetShabbatBonus);
        btnSaveSettings = findViewById(R.id.btnSaveSettings);

        // Quote button
        btnNextQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEncouragementQuote();
            }
        });
    }

    private void updateEncouragementQuote() {
        tvEncouragementQuote.setText(QuotesManager.getRandomQuote());
    }

    private void setupNavigationTabs() {
        tabTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTab(0);
            }
        });
        tabBloodType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTab(1);
            }
        });
        tabShabbat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTab(2);
            }
        });
        tabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTab(3);
            }
        });
    }

    private void switchTab(int tabIndex) {
        activeTab = tabIndex;

        viewTracker.setVisibility(tabIndex == 0 ? View.VISIBLE : View.GONE);
        viewBloodType.setVisibility(tabIndex == 1 ? View.VISIBLE : View.GONE);
        viewShabbat.setVisibility(tabIndex == 2 ? View.VISIBLE : View.GONE);
        viewSettings.setVisibility(tabIndex == 3 ? View.VISIBLE : View.GONE);

        // Update button states
        tabTracker.setBackgroundResource(tabIndex == 0 ? R.drawable.tab_btn_selected : R.drawable.tab_btn_unselected);
        tabTracker.setTextColor(tabIndex == 0 ? Color.WHITE : Color.parseColor("#333333"));

        tabBloodType.setBackgroundResource(tabIndex == 1 ? R.drawable.tab_btn_selected : R.drawable.tab_btn_unselected);
        tabBloodType.setTextColor(tabIndex == 1 ? Color.WHITE : Color.parseColor("#333333"));

        tabShabbat.setBackgroundResource(tabIndex == 2 ? R.drawable.tab_btn_selected : R.drawable.tab_btn_unselected);
        tabShabbat.setTextColor(tabIndex == 2 ? Color.WHITE : Color.parseColor("#333333"));

        tabSettings.setBackgroundResource(tabIndex == 3 ? R.drawable.tab_btn_selected : R.drawable.tab_btn_unselected);
        tabSettings.setTextColor(tabIndex == 3 ? Color.WHITE : Color.parseColor("#333333"));
    }

    private int getCurrentEffectiveTarget() {
        return weekdayTargetCalories + (isShabbatMode ? shabbatBonusCalories : 0);
    }

    private void updateHeaderAndTotals() {
        int currentTarget = getCurrentEffectiveTarget();
        int remaining = currentTarget - consumedCalories;

        tvActiveBloodAndDay.setText("סוג דם: " + selectedBloodType + " | " + (isShabbatMode ? "שבת קודש / חג 🕯️" : "יום חול"));

        switchShabbatMode.setChecked(isShabbatMode);
        if (isShabbatMode) {
            tvCurrentModeIndicator.setText("מצב: שבת קודש (+ " + shabbatBonusCalories + " קק״ל בנק שבת)");
            tvCurrentModeIndicator.setTextColor(Color.parseColor("#E65100"));
        } else {
            tvCurrentModeIndicator.setText("מצב יום: יום חול רגיל");
            tvCurrentModeIndicator.setTextColor(Color.parseColor("#2E7D32"));
        }

        tvCaloriesConsumed.setText(String.valueOf(consumedCalories));
        tvCaloriesTarget.setText(String.valueOf(currentTarget));
        tvCaloriesRemaining.setText(String.valueOf(remaining));

        if (remaining < 0) {
            tvCaloriesRemaining.setTextColor(Color.parseColor("#C62828"));
        } else {
            tvCaloriesRemaining.setTextColor(Color.parseColor("#2E7D32"));
        }

        int progress = (currentTarget > 0) ? Math.min(100, (int) ((consumedCalories / (float) currentTarget) * 100)) : 0;
        progressCalories.setProgress(progress);

        tvBloodGuideHeader.setText("התאמת מזונות לסוג דם " + selectedBloodType);
    }

    private void setupTrackerListeners() {
        switchShabbatMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isShabbatMode = isChecked;
                savePreferences();
                updateHeaderAndTotals();
                updateShabbatMealBudgets();
                Toast.makeText(MainActivity.this, isChecked ? "הופעל מצב שבת: תוספת קלוריות הוחלה!" : "הופעל מצב יום חול", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddCustomFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName = etCustomFoodName.getText().toString().trim();
                String calStr = etCustomFoodCalories.getText().toString().trim();

                if (foodName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "נא להזין שם מאכל", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (calStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "נא להזין כמות קלוריות", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int cals = Integer.parseInt(calStr);
                    addFoodToLog(foodName, cals);
                    etCustomFoodName.setText("");
                    etCustomFoodCalories.setText("");
                    Toast.makeText(MainActivity.this, foodName + " נוסף ליומן!", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "קלוריות לא תקינות", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnQuickPickFromBloodList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchTab(1);
            }
        });

        btnClearDailyLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loggedFoodsList.clear();
                consumedCalories = 0;
                savePreferences();
                updateHeaderAndTotals();
                renderLoggedFoods();
                Toast.makeText(MainActivity.this, "היומן אופס בהצלחה לקראת יום חדש!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addFoodToLog(String name, int calories) {
        loggedFoodsList.add(new LoggedFood(name, calories));
        consumedCalories += calories;
        savePreferences();
        updateHeaderAndTotals();
        renderLoggedFoods();
    }

    private void renderLoggedFoods() {
        containerLoggedFoods.removeAllViews();
        if (loggedFoodsList.isEmpty()) {
            tvEmptyLogNotice.setVisibility(View.VISIBLE);
            return;
        }
        tvEmptyLogNotice.setVisibility(View.GONE);

        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < loggedFoodsList.size(); i++) {
            final int index = i;
            final LoggedFood item = loggedFoodsList.get(i);

            View row = inflater.inflate(R.layout.item_logged_food, containerLoggedFoods, false);
            TextView tvName = row.findViewById(R.id.tvLoggedFoodName);
            TextView tvCals = row.findViewById(R.id.tvLoggedFoodCalories);
            Button btnRemove = row.findViewById(R.id.btnRemoveLoggedFood);

            tvName.setText(item.name);
            tvCals.setText(item.calories + " קק״ל");

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    consumedCalories = Math.max(0, consumedCalories - item.calories);
                    loggedFoodsList.remove(index);
                    savePreferences();
                    updateHeaderAndTotals();
                    renderLoggedFoods();
                }
            });

            containerLoggedFoods.addView(row);
        }
    }

    private void setupBloodGuideListeners() {
        etSearchFood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchQuery = s.toString().trim();
                renderFoodGuideList();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        View.OnClickListener filterClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFilterButtons();
                int id = v.getId();
                if (id == R.id.btnFilterAll) {
                    currentFoodFilter = "ALL";
                    btnFilterAll.setBackgroundResource(R.drawable.btn_primary);
                    btnFilterAll.setTextColor(Color.WHITE);
                } else if (id == R.id.btnFilterBeneficial) {
                    currentFoodFilter = "BENEFICIAL";
                    btnFilterBeneficial.setBackgroundResource(R.drawable.btn_primary);
                    btnFilterBeneficial.setTextColor(Color.WHITE);
                } else if (id == R.id.btnFilterNeutral) {
                    currentFoodFilter = "NEUTRAL";
                    btnFilterNeutral.setBackgroundResource(R.drawable.btn_primary);
                    btnFilterNeutral.setTextColor(Color.WHITE);
                } else if (id == R.id.btnFilterAvoid) {
                    currentFoodFilter = "AVOID";
                    btnFilterAvoid.setBackgroundResource(R.drawable.btn_primary);
                    btnFilterAvoid.setTextColor(Color.WHITE);
                } else if (id == R.id.btnFilterShabbatFoods) {
                    currentFoodFilter = "SHABBAT";
                    btnFilterShabbatFoods.setBackgroundResource(R.drawable.btn_primary);
                    btnFilterShabbatFoods.setTextColor(Color.WHITE);
                }
                renderFoodGuideList();
            }
        };

        btnFilterAll.setOnClickListener(filterClick);
        btnFilterBeneficial.setOnClickListener(filterClick);
        btnFilterNeutral.setOnClickListener(filterClick);
        btnFilterAvoid.setOnClickListener(filterClick);
        btnFilterShabbatFoods.setOnClickListener(filterClick);
    }

    private void resetFilterButtons() {
        btnFilterAll.setBackgroundResource(R.drawable.tab_btn_unselected);
        btnFilterAll.setTextColor(Color.parseColor("#333333"));

        btnFilterBeneficial.setBackgroundResource(R.drawable.tab_btn_unselected);
        btnFilterBeneficial.setTextColor(Color.parseColor("#2E7D32"));

        btnFilterNeutral.setBackgroundResource(R.drawable.tab_btn_unselected);
        btnFilterNeutral.setTextColor(Color.parseColor("#1565C0"));

        btnFilterAvoid.setBackgroundResource(R.drawable.tab_btn_unselected);
        btnFilterAvoid.setTextColor(Color.parseColor("#C62828"));

        btnFilterShabbatFoods.setBackgroundResource(R.drawable.tab_btn_unselected);
        btnFilterShabbatFoods.setTextColor(Color.parseColor("#FF8F00"));
    }

    private void renderFoodGuideList() {
        containerFoodList.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (final FoodItem item : allFoodItems) {
            FoodItem.Compatibility compatibility = item.getCompatibility(selectedBloodType);

            // Filter check
            if (!currentSearchQuery.isEmpty()) {
                if (!item.getName().contains(currentSearchQuery) && !item.getTip().contains(currentSearchQuery)) {
                    continue;
                }
            }

            if ("BENEFICIAL".equals(currentFoodFilter) && compatibility != FoodItem.Compatibility.BENEFICIAL) {
                continue;
            }
            if ("NEUTRAL".equals(currentFoodFilter) && compatibility != FoodItem.Compatibility.NEUTRAL) {
                continue;
            }
            if ("AVOID".equals(currentFoodFilter) && compatibility != FoodItem.Compatibility.AVOID) {
                continue;
            }
            if ("SHABBAT".equals(currentFoodFilter) && !item.isShabbatFood()) {
                continue;
            }

            View row = inflater.inflate(R.layout.item_food_card, containerFoodList, false);
            TextView tvName = row.findViewById(R.id.tvFoodName);
            TextView tvShabbatBadge = row.findViewById(R.id.tvFoodShabbatBadge);
            TextView tvCalories = row.findViewById(R.id.tvFoodCalories);
            TextView tvNote = row.findViewById(R.id.tvFoodNote);
            TextView tvBadge = row.findViewById(R.id.tvCompatibilityBadge);
            Button btnAdd = row.findViewById(R.id.btnAddFoodToDiary);

            tvName.setText(item.getName());
            tvCalories.setText(item.getCalories() + " קק״ל • " + item.getPortion());
            tvNote.setText(item.getTip());

            if (item.isShabbatFood()) {
                tvShabbatBadge.setVisibility(View.VISIBLE);
            } else {
                tvShabbatBadge.setVisibility(View.GONE);
            }

            if (compatibility == FoodItem.Compatibility.BENEFICIAL) {
                tvBadge.setText("מועיל מאוד לסוג " + selectedBloodType);
                tvBadge.setTextColor(Color.parseColor("#2E7D32"));
                tvBadge.setBackgroundColor(Color.parseColor("#E8F5E9"));
            } else if (compatibility == FoodItem.Compatibility.NEUTRAL) {
                tvBadge.setText("ניטרלי לסוג " + selectedBloodType);
                tvBadge.setTextColor(Color.parseColor("#1565C0"));
                tvBadge.setBackgroundColor(Color.parseColor("#E3F2FD"));
            } else {
                tvBadge.setText("מומלץ להימנע לסוג " + selectedBloodType);
                tvBadge.setTextColor(Color.parseColor("#C62828"));
                tvBadge.setBackgroundColor(Color.parseColor("#FFEBEE"));
            }

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addFoodToLog(item.getName(), item.getCalories());
                    Toast.makeText(MainActivity.this, item.getName() + " נוסף ליומן!", Toast.LENGTH_SHORT).show();
                }
            });

            containerFoodList.addView(row);
        }
    }

    private void setupShabbatViewListeners() {
        btnApplyShabbatToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShabbatMode = true;
                savePreferences();
                updateHeaderAndTotals();
                switchTab(0);
                Toast.makeText(MainActivity.this, "מצב שבת הופעל בהצלחה ביומן!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateShabbatMealBudgets() {
        int shabbatTotal = weekdayTargetCalories + shabbatBonusCalories;
        int meal1 = (int) (shabbatTotal * 0.38);
        int meal2 = (int) (shabbatTotal * 0.42);
        int meal3 = Math.max(300, shabbatTotal - (meal1 + meal2));

        tvMeal1Budget.setText("סעודת ליל שבת (~" + meal1 + " קק״ל)");
        tvMeal2Budget.setText("סעודת יום שבת - חמין וסעודה (~" + meal2 + " קק״ל)");
        tvMeal3Budget.setText("סעודה שלישית קלה (~" + meal3 + " קק״ל)");
    }

    private void setupSettingsListeners() {
        final String[] bloodTypes = {"O", "A", "B", "AB"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodType.setAdapter(adapter);

        int selectedIndex = 0;
        for (int i = 0; i < bloodTypes.length; i++) {
            if (bloodTypes[i].equalsIgnoreCase(selectedBloodType)) {
                selectedIndex = i;
                break;
            }
        }
        spinnerBloodType.setSelection(selectedIndex);

        etTargetWeekdayCalories.setText(String.valueOf(weekdayTargetCalories));
        etTargetShabbatBonus.setText(String.valueOf(shabbatBonusCalories));

        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPos = spinnerBloodType.getSelectedItemPosition();
                if (selectedPos >= 0 && selectedPos < bloodTypes.length) {
                    selectedBloodType = bloodTypes[selectedPos];
                }

                String weekdayStr = etTargetWeekdayCalories.getText().toString().trim();
                String shabbatBonusStr = etTargetShabbatBonus.getText().toString().trim();

                try {
                    int wTarget = Integer.parseInt(weekdayStr);
                    int sBonus = Integer.parseInt(shabbatBonusStr);

                    if (wTarget < 1000 || wTarget > 5000) {
                        Toast.makeText(MainActivity.this, "נא להזין יעד קלוריות הגיוני (1000 - 5000)", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (sBonus < 0 || sBonus > 2000) {
                        Toast.makeText(MainActivity.this, "תוספת שבת לא תקינה (0 - 2000)", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    weekdayTargetCalories = wTarget;
                    shabbatBonusCalories = sBonus;

                    savePreferences();
                    updateHeaderAndTotals();
                    renderFoodGuideList();
                    updateShabbatMealBudgets();

                    Toast.makeText(MainActivity.this, "ההגדרות נשמרו בהצלחה!", Toast.LENGTH_SHORT).show();
                    switchTab(0);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "ערכי קלוריות שגויים", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
