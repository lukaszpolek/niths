package no.niths.domain.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.niths.application.rest.exception.CustomParseException;
import no.niths.common.AppConstants;

public class XmlCalendarAdapter extends XmlAdapter<String, Calendar> {

	private DateFormat df = new SimpleDateFormat(AppConstants.CALENDAR_FORMAT);
	private Logger logger = LoggerFactory
			.getLogger(XmlCalendarAdapter.class);
	@Override
	public Calendar unmarshal(String date) {

		Calendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(df.parse(date));
			return calendar;
		} catch (ParseException px) {
			logger.error(px.getMessage(),px);
			throw new CustomParseException("Invalid syntacs! Valid syntax : "
					+ AppConstants.CALENDAR_FORMAT + " ErrorOffset:"
					+ px.getErrorOffset());
		}
		

	}

	@Override
	public String marshal(Calendar calendar) throws ParseException {
		return df.format(calendar.getTime());
	}

}
