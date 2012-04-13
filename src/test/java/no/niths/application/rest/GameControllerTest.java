package no.niths.application.rest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.validation.ConstraintViolationException;

import no.niths.application.rest.battlestation.interfaces.ConsoleController;
import no.niths.application.rest.battlestation.interfaces.GameController;
import no.niths.application.rest.battlestation.interfaces.LoanController;
import no.niths.application.rest.exception.ObjectNotFoundException;
import no.niths.common.config.HibernateConfig;
import no.niths.common.config.TestAppConfig;
import no.niths.domain.battlestation.Console;
import no.niths.domain.battlestation.Game;
import no.niths.domain.battlestation.Loan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.GregorianCalendar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestAppConfig.class, HibernateConfig.class })
public class GameControllerTest {

    @Autowired
    private GameController gameController;

    @Autowired
    private ConsoleController consoleController;

    @Autowired
    private LoanController loanController;

    @Test(expected= ConstraintViolationException.class)
    public void testInsertNullObject_shallThrowException() {
        Game game = new Game("P");
        gameController.create(game);
    }

    @Test
    public void testCreateAndDelete() {
        int size = 0;

        try {
            size = gameController.getAll(null).size();
        } catch (ObjectNotFoundException exception) {
        }

        Game game = new Game("Super Mario");
        gameController.create(game);

        assertThat(size + 1, is(equalTo(gameController.getAll(null).size())));

        gameController.hibernateDelete(game.getId());

        int currentSize = 0;

        try {
            currentSize = gameController.getAll(null).size();
        }catch (ObjectNotFoundException exception) {
        }

        assertThat(currentSize, is(equalTo(size)));
    }

    @Test
    public void testCreateAndDeleteOfConsole() {
        Game game = new Game("Super Mario");
        gameController.create(game);

        assertThat(game, is(equalTo(gameController.getById(game.getId()))));

        Console console = new Console("Wii");

        consoleController.create(console);

        gameController.addConsole(game.getId(), console.getId());

        assertThat(consoleController.getById(console.getId()), is(equalTo(gameController.getById(game.getId()).getConsole())));

        gameController.removeConsole(game.getId());

        assertThat(gameController.getById(game.getId()).getConsole(), is(nullValue()));

        gameController.hibernateDelete(game.getId());
        consoleController.hibernateDelete(console.getId());
    }

    @Test
    public void testCreateAndDeleteOfLoan() {
        Game game = new Game("Super Mario");
        gameController.create(game);

        assertThat(game, is(equalTo(gameController.getById(game.getId()))));

        Loan loan = new Loan(new GregorianCalendar());
        loanController.create(loan);

        gameController.addLoan(game.getId(), loan.getId());

        assertThat(loanController.getById(loan.getId()), is(equalTo(gameController.getById(game.getId()).getLoan())));

        gameController.removeLoan(game.getId());

        assertThat(gameController.getById(game.getId()).getLoan(), is(nullValue()));

        gameController.hibernateDelete(game.getId());
        loanController.hibernateDelete(loan.getId());
    }
}
