package no.niths.infrastructure;

import no.niths.domain.Console;
import no.niths.infrastructure.interfaces.ConsoleRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ConsoleRepositoryImpl extends AbstractGenericRepositoryImpl<Console> implements ConsoleRepository {

    public ConsoleRepositoryImpl() {
        super(Console.class, new Console());
    }
}
