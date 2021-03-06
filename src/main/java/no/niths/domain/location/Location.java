package no.niths.domain.location;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import no.niths.common.constants.DomainConstantNames;
import no.niths.common.constants.ValidationConstants;
import no.niths.domain.Domain;
import no.niths.domain.school.Event;
import no.niths.domain.school.Feed;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
/**
 * Domain class for Location
 *
 * <p>
 * Location has these variables:
 * place = example Kroa (can not be null),
 * longitude = example 10.4500,
 * latitude = example 59.5500
 * </p>
 * <p>
 * And relations too:
 * Event,
 * Feed
 * </p>
 */
@Entity
@Table(name = DomainConstantNames.LOCATIONS)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement
public class Location implements Domain {

    private static final long serialVersionUID = -4834276555969698011L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @Pattern(
            regexp  = ValidationConstants.REGULAR,
            message = "Invalid place (should be 2 - 50 alphanumeric chars)")
    private String place;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @JsonIgnore
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Event.class)
    @JoinTable(name = "events_location", 
    joinColumns = @JoinColumn(name = "location_id"), 
    inverseJoinColumns = @JoinColumn(name = "events_id"))
    @Cascade(CascadeType.ALL)
    private List<Event> events = new ArrayList<Event>();

    @JsonIgnore
    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "feeds_location",
        joinColumns =        @JoinColumn(name = "location_id"),
        inverseJoinColumns = @JoinColumn(name = "feeds_id"))
    @Cascade(CascadeType.ALL)
    private List<Feed> feeds = new ArrayList<Feed>();

    public Location() {
        this(null, null);
        setFeeds(null);
        setEvents(null);
    }

    public Location(String place) {
        this.place = place;
    }

    public Location(Double latitude, Double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public Location(String place, double longitude, double latitude) {
        this(latitude, longitude);
        setPlace(place);
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object that) {
        if (that == this)
            return true;

        if (!(that instanceof Location))
            return false;

        Location l = (Location) that;

        return l.toString().equals(toString());
    }

    @Override
    public String toString() {
        return String.format("[%s][%s][Longitud:%s][Latitude:%s]", id, place,
                longitude, latitude);
    }

    @XmlTransient
    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    @XmlTransient
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}