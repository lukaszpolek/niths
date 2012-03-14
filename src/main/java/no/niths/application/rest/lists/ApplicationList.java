package no.niths.application.rest.lists;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import no.niths.common.AppConstants;
import no.niths.domain.APIEvent;
import no.niths.domain.Application;
import no.niths.domain.Developer;

@XmlRootElement(name = AppConstants.APPLICATIONS)
public class ApplicationList extends ListAdapter<Application>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7176343871162986392L;
	@XmlElement(name = "application")
    private List<Application> data;

    @Override
    public void setData(List<Application> data) {
     this.data = data;
    }
}