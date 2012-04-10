package no.niths.application.rest;

import no.niths.application.rest.exception.ObjectNotFoundException;
import no.niths.application.rest.interfaces.GameController;
import no.niths.application.rest.lists.GameList;
import no.niths.application.rest.lists.ListAdapter;
import no.niths.common.AppConstants;
import no.niths.common.ValidationHelper;
import no.niths.domain.Console;
import no.niths.domain.Game;
import no.niths.services.interfaces.ConsoleService;
import no.niths.services.interfaces.GameService;
import no.niths.services.interfaces.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;

/**
 * Controller for games
 */
@Controller
@RequestMapping(AppConstants.GAMES)
public class GameControllerImpl extends AbstractRESTControllerImpl<Game> implements GameController {

    private static final Logger logger = LoggerFactory
            .getLogger(GameControllerImpl.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private ConsoleService consoleService;

    private GameList gameList = new GameList();

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Game> getAll(Game domain) {
        gameList = (GameList) super.getAll(domain);
        clearRelations();
        return gameList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Game> getAll(Game domain, @PathVariable int firstResult, @PathVariable int maxResults) {
        gameList = (GameList) super.getAll(domain, firstResult, maxResults);
        clearRelations();
        return gameList;
    }

    private void clearRelations(){
        for(Game game : gameList){
            game.setConsole(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "add/console/{gameId}/{consoleId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK, reason = "Console Added")
    public void addConsole(@PathVariable Long gameId, @PathVariable Long consoleId) {
        Game game = gameService.getById(gameId);
        ValidationHelper.isObjectNull(game, "Game does not exist");

        Console console = consoleService.getById(consoleId);
        ValidationHelper.isObjectNull(console, "Console does not exist");

        game.setConsole(console);
        gameService.update(game);
        logger.debug("Game updated");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(value = "remove/console/{gameId}/{consoleId}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK, reason = "Console Removed")
    public void removeConsole(@PathVariable Long gameId, @PathVariable Long consoleId) {
        Game game = gameService.getById(gameId);
        ValidationHelper.isObjectNull(game, "Game does not exist");

        boolean isRemoved = false;

        if (game.getConsole() != null && game.getConsole().getId() == consoleId) {
            game.setConsole(null);
            isRemoved = true;
        }

        if (isRemoved) {
            gameService.update(game);
        } else {
            logger.debug("Console not found");
            throw new ObjectNotFoundException("Console not found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GenericService<Game> getService() {
        return gameService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListAdapter<Game> getList() {
        return gameList;
    }
}