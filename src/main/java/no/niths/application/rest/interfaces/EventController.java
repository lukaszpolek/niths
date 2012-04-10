package no.niths.application.rest.interfaces;

import java.text.ParseException;
import java.util.List;

import no.niths.application.rest.helper.TimeDTO;
import no.niths.domain.Event;
/**
 * Controller for events
 */
public interface EventController extends GenericRESTController<Event> {
	/**
	 * 
	 * Retruns a list of events on the given tag.
	 * Here you can search on more tags by adding &
	 * between the serach words like this:
	 * tag = "party&fun"
	 * @param tag
	 * @return
	 */
	List<Event> getEventsByTag(String tag);
	
	/**
	 * Adds a location to a event
	 * @param eventId
	 * @param LocId
	 */
	void addLocation(Long eventId, Long LocId);
	
	/**
	 * Removes a location from a event
	 * @param eventId
	 * @param LocId
	 */
	void removeLocation(Long eventId, Long LocId);
	
	
	List<Event> getEventsBetweenDates(TimeDTO timeDTO) throws ParseException;
}
