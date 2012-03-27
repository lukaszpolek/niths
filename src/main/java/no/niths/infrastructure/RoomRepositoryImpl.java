package no.niths.infrastructure;

import no.niths.domain.location.Room;
import no.niths.infrastructure.interfaces.RoomRepository;

import org.springframework.stereotype.Repository;

@Repository
public class RoomRepositoryImpl extends AbstractGenericRepositoryImpl<Room> implements
		RoomRepository {

	public RoomRepositoryImpl() {
		super(Room.class, new Room());
	}
}