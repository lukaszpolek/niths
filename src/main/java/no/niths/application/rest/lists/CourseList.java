package no.niths.application.rest.lists;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import no.niths.common.AppConstants;
import no.niths.domain.Course;

@XmlRootElement(name = AppConstants.COURSES)
public class CourseList extends ListAdapter<Course> {

	private static final long serialVersionUID = 8082915037044141181L;
	@XmlElement(name = "course")
	private List<Course> courseData;

	@Override
	public void setData(List<Course> data) {
		this.courseData = data;
	}

	public List<Course> getCourseData() {
		return courseData;
	}

    @Override
    public List<Course> getData() {
        // TODO Auto-generated method stub
        return null;
    }
}
