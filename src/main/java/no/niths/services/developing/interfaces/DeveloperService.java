package no.niths.services.developing.interfaces;

import java.util.List;

import no.niths.domain.developing.Developer;
import no.niths.services.interfaces.GenericService;

public interface DeveloperService extends GenericService<Developer> {
	
	/**
	 * Returns the developer with the matching developer token
	 * 
	 * @param token string with the developer token
	 * @param isEnabled set to tru if you want dev that is enabled
	 * @return the developer or null if no developer were found
	 */
	@Deprecated
	Developer getDeveloperByDeveloperToken(String token, boolean isEnabled);
	
	/**
	 * Returns all developers with and their applications
	 * @param dev a developer object with attributes to limit the result set,
	 * 			if null, all developers will be returned
	 * @return list of all developers
	 */
	List<Developer> getAllWithApps(Developer dev);

	/**
	 * Returns the developer with the matching developer token
	 * 
	 * @param token string with the developer key
	 * @return the developer or null if no developer were found
	 */
	Developer getDeveloperByDeveloperKey(String key);

	void enableDeveloper(Long developerId);

	void disableDeveloper(Long developerId);

	void resetDeveloperKey(Long developerId, String generatedDeveloperKey);

	void addApplication(Long developerId, Long applicationId);

	void removeApplicaiton(Long developerId, Long applicationId);

	
}
