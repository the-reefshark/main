package life.calgo.model.day;

import static life.calgo.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.OptionalDouble;

import life.calgo.model.food.Food;
import life.calgo.commons.util.CollectionUtil;

public class Day {
    // Identity fields
    private final LocalDate localDate;

    // Data fields
    private final DailyFoodLog dailyFoodLog;
    // food list does not have to be unique, so we can follow rules of immutability.

    public Day() {
        localDate = LocalDate.now();
        dailyFoodLog = new DailyFoodLog();
    }

    public Day(LocalDate localDate) {
        CollectionUtil.requireAllNonNull(localDate);
        this.localDate = localDate;
        dailyFoodLog = new DailyFoodLog();
    }

    public Day(LocalDate localDate, DailyFoodLog dailyFoodLog) {
        CollectionUtil.requireAllNonNull(localDate, dailyFoodLog);
        this.localDate = localDate;
        this.dailyFoodLog = dailyFoodLog.copy();
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public DailyFoodLog getDailyFoodLog() {
        return dailyFoodLog.copy();
    }

    public Day setDate(LocalDate date) {
        // do exception checking for invalid date? or catch LocalDate.parse?
        return new Day(date, dailyFoodLog.copy());
    }

    public Day consume(Food food, double quantity) {
        return new Day(localDate, dailyFoodLog.add(food, quantity));
    }

    public Day vomit(Food food, OptionalDouble quantity) {
        return new Day(localDate, dailyFoodLog.remove(food, quantity));
    }

    public double getPortion(Food food) {
        return dailyFoodLog.getPortion(food);
    }

    public static boolean isValidDate(String test) {
        return true;
        // else throw exceptions
    }

//    public ObservableList<Food> getFilteredDailyList() {
//        return dailyFoodLog.getAsFilteredList();
//    }

    public boolean isSameDay(Day otherDay) {
        if (otherDay == this) {
            return true;
        }

        return otherDay != null
                && otherDay.getLocalDate().equals(getLocalDate());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return otherDay.getLocalDate().equals(getLocalDate())
                && otherDay.getDailyFoodLog().equals(getDailyFoodLog());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(localDate, dailyFoodLog);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(localDate);
        return builder.toString();
    }
}