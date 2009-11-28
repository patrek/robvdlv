package nl.ddd.facade;

import nl.ddd.command.AddBookCommand;
import nl.ddd.command.Command;
import nl.ddd.commandhandler.CommandHandler;

import javax.annotation.Resource;

public class AmazonLiteFacadeImpl implements AmazonLiteFacade {

    @Resource
    private CommandHandler<AddBookCommand> handler;

    public void publish(Command command) {
        AddBookCommand addBookCommand = (AddBookCommand) command;
        handler.execute(addBookCommand);
    }
}
