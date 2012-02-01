package no.niths.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

import no.niths.common.AppConstants;

@XmlRootElement
@Entity
@Table(name = AppConstants.COMMITTEE_EVENTS)
public class CommitteeEvent implements Serializable {

    @Transient
    private static final long serialVersionUID = 1878727682733503699L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Size(min = 3, max = 30, message ="The length of the name must be between 3 to 30 letters")
    private String name;

    @Column(length=500)
    @Size(max = 500, message ="The length of the description must not exceed 500 letters")
    private String description;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar DateAndTime;

    @ManyToOne
    private Committee committee;

    public CommitteeEvent() {
        this(-1, "foo", "bar", new GregorianCalendar());
    }

    public CommitteeEvent(long id, String name, String description,
            GregorianCalendar dateAndTime) {
        setId(id);
        setName(name);
        setDescription(description);
        setDateAndTime(dateAndTime);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Committee getCommittee() {
        return committee;
    }

    public void setCommittee(Committee committee) {
        this.committee = committee;
    }

    public Calendar getDateAndTime() {
        return DateAndTime;
    }

    public void setDateAndTime(GregorianCalendar dateAndTime) {
        DateAndTime = dateAndTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CommitteeEvent)) {
            return false;
        }
        CommitteeEvent s = (CommitteeEvent) obj;
        return s == this ? true : s.getId() == id ? true : false;
    }
}