package life.calgo.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Stream;

import life.calgo.logic.commands.UpdateCommand;
import life.calgo.logic.parser.exceptions.ParseException;
import f11_1.calgo.model.food.*;
import life.calgo.model.tag.Tag;
import life.calgo.commons.core.Messages;
import life.calgo.model.food.Calorie;
import life.calgo.model.food.Carbohydrate;
import life.calgo.model.food.Fat;
import life.calgo.model.food.Food;
import life.calgo.model.food.Name;
import life.calgo.model.food.Protein;

public class UpdateCommandParser implements Parser<UpdateCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        System.out.println("I AM BEING PARSED");
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_CALORIES,
                        CliSyntax.PREFIX_PROTEIN, CliSyntax.PREFIX_CARBOHYDRATE, CliSyntax.PREFIX_FAT, CliSyntax.PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_CALORIES,
                CliSyntax.PREFIX_PROTEIN, CliSyntax.PREFIX_FAT, CliSyntax.PREFIX_CARBOHYDRATE)
                || !argMultimap.getPreamble().isEmpty()) {
            // if not all prefixes are present then throw error
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Calorie calorie = ParserUtil.parseCalorie(argMultimap.getValue(CliSyntax.PREFIX_CALORIES).get());
        Protein protein = ParserUtil.parseProtein(argMultimap.getValue(CliSyntax.PREFIX_PROTEIN).get());
        Carbohydrate carbohydrate = ParserUtil.parseCarbohydrate(argMultimap.getValue(CliSyntax.PREFIX_CARBOHYDRATE).get());
        Fat fat = ParserUtil.parseFat(argMultimap.getValue(CliSyntax.PREFIX_FAT).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(CliSyntax.PREFIX_TAG));

        Food food = new Food(name, calorie, protein, carbohydrate, fat, tagList);

        return new UpdateCommand(food);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}