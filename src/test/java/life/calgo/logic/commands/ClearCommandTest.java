package life.calgo.logic.commands;

import static life.calgo.logic.commands.CommandTestUtil.assertCommandSuccess;

import life.calgo.model.day.DailyGoal;
import life.calgo.testutil.TypicalFoodItems;

import org.junit.jupiter.api.Test;

import life.calgo.model.FoodRecord;
import life.calgo.model.Model;
import life.calgo.model.ModelManager;
import life.calgo.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalFoodItems.getTypicalFoodRecord(), new UserPrefs(), new DailyGoal());
        Model expectedModel = new ModelManager(TypicalFoodItems.getTypicalFoodRecord(), new UserPrefs(), new DailyGoal());
        expectedModel.setFoodRecord(new FoodRecord());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}