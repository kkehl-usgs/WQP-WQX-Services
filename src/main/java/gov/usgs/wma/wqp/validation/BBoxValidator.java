package gov.usgs.wma.wqp.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import gov.usgs.wma.wqp.parameter.BoundingBox;

public class BBoxValidator implements ConstraintValidator<BBox, BoundingBox> {

	public static final BigDecimal MAX_LATITUDE = new BigDecimal(90);
	public static final BigDecimal MIN_LATITUDE = new BigDecimal(-90);
	public static final BigDecimal MAX_LONGITUDE = new BigDecimal(180);
	public static final BigDecimal MIN_LONGITUDE = new BigDecimal(-180);

	@Override
	public boolean isValid(BoundingBox value, ConstraintValidatorContext context) {
		boolean valid = false;
		if (null == value || StringUtils.isBlank(value.getSingle())) {
			valid = true;
		} else {
			try {
				BigDecimal north = new BigDecimal(value.getNorth());
				BigDecimal south = new BigDecimal(value.getSouth());
				BigDecimal east = new BigDecimal(value.getEast());
				BigDecimal west = new BigDecimal(value.getWest());
	
				boolean latsValid = south.compareTo(MIN_LATITUDE) != -1
						&& south.compareTo(MAX_LATITUDE) != 1
						&& north.compareTo(MIN_LATITUDE) != -1
						&& north.compareTo(MAX_LATITUDE) != 1;
				boolean longsValid = west.compareTo(MIN_LONGITUDE) != -1
						&& west.compareTo(MAX_LONGITUDE) != 1
						&& east.compareTo(MIN_LONGITUDE) != -1
						&& east.compareTo(MAX_LONGITUDE) != 1;
				boolean crossCheckValid = west.compareTo(east) != 1
						&& south.compareTo(north) != 1;
				valid = latsValid && longsValid && crossCheckValid;
			} catch (NumberFormatException | NullPointerException e) {
				//Nothing to see here, just return not valid.
			}
		}
		return valid;
	}

}
