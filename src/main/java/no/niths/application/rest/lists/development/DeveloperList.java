package no.niths.application.rest.lists.development;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import no.niths.application.rest.lists.ListAdapter;
import no.niths.common.constants.DomainConstantNames;
import no.niths.domain.development.Developer;
/**
 * Class to contain a list of developers
 */
@XmlRootElement(name = DomainConstantNames.DEVELOPERS)
public class DeveloperList extends ListAdapter<Developer> {

    private static final long serialVersionUID = 7176343871162986392L;

    @SuppressWarnings("unused")
    @XmlElement(name = "developer")
    private List<Developer> data;

    @Override
    public void setData(List<Developer> data) {
        this.data = data;
    }
}