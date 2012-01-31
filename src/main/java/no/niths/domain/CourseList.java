package no.niths.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import no.niths.common.AppConstants;

@XmlRootElement(name = AppConstants.COURSES)
public class CourseList {

    @XmlElement(name = "course")
    private List<Course> data;

    public void setData(List<Course> data) {
     this.data = data;
    }
}
