package gov.usgs.cida.wqp.mapping.delimited;

import static gov.usgs.cida.wqp.mapping.BaseColumn.*;
import static gov.usgs.cida.wqp.mapping.delimited.ProjectDelimited.*;
import static gov.usgs.cida.wqp.mapping.ProjectMonitoringLocationWeightingColumn.*;
import static gov.usgs.cida.wqp.mapping.ProjectColumn.*;
import static gov.usgs.cida.wqp.mapping.xml.BaseWqx.*;

import java.util.LinkedHashMap;
import java.util.Map;

import gov.usgs.cida.wqp.mapping.ColumnProfile;
import gov.usgs.cida.wqp.mapping.Profile;

public class ProjectMonitoringLocationWeightingDelimited extends BaseDelimited {
	
	//Column Headings for the Keys
	public static final String VALUE_PRJMLW_VALUE = WQX_MEASURE_VALUE;
	public static final String VALUE_PRJMLW_UNIT = WQX_MEASURE_UNIT;
	public static final String VALUE_PRJMLW_COMMENT = WQX_PROJECT_MONITORING_LOCATION_WEIGHTING_COMMENT_TEXT;
	public static final String VALUE_PRJMLW_STATISTICAL_STRATUM = WQX_STATISTICAL_STRATUM_TEXT;
	public static final String VALUE_PRJMLW_LOCATION_CATEGORY = WQX_LOCATION_CATEGORY_NAME;
	public static final String VALUE_PRJMLW_LOCATION_STATUS = WQX_LOCATION_STATUS_NAME;
	public static final String VALUE_REFERENCE_LOCATION_TYPE_CODE = WQX_REFERENCE_LOCATION_TYPE_CODE;
	public static final String VALUE_REFERENCE_LOCATION_START_DATE = WQX_REFERENCE_LOCATION_START_DATE;
	public static final String VALUE_REFERENCE_LOCATION_END_DATE = WQX_REFERENCE_LOCATION_END_DATE;
	public static final String VALUE_REFERENCE_LOCATION_CITATION_TITLE = WQX_RESOURCE_SUBJECT;
	public static final String VALUE_REFERENCE_LOCATION_CITATION_CREATOR = WQX_RESOURCE_CREATOR;
	public static final String VALUE_REFERENCE_LOCATION_CITATION_SUBJECT = WQX_RESOURCE_SUBJECT;
	public static final String VALUE_REFERENCE_LOCATION_CITATION_PUBLISHER = WQX_RESOURCE_PUBLISHER;
	public static final String VALUE_REFERENCE_LOCATION_CITATION_DATE = WQX_RESOURCE_DATE;
	public static final String VALUE_REFERENCE_LOCATION_CITATION_IDENTIFIER = WQX_RESOURCE_ID;
	
	public static final Map<ColumnProfile, String> MAPPINGS;
	static {
		MAPPINGS = new LinkedHashMap<ColumnProfile,String>();
		MAPPINGS.put(ORGANIZATION, VALUE_ORGANIZATION_IDENTIFIER);
		MAPPINGS.put(ORGANIZATION_NAME, VALUE_ORGANIZATION_FORMAL_NAME);
		MAPPINGS.put(PROJECT_IDENTIFIER, VALUE_PROJECT);
		MAPPINGS.put(SITE_ID, VALUE_MONITORING_LOCATION_IDENTIFIER);
		MAPPINGS.put(PRJMLW_VALUE, VALUE_PRJMLW_VALUE);
		MAPPINGS.put(PRJMLW_UNIT, VALUE_PRJMLW_UNIT);
		MAPPINGS.put(PRJMLW_STATISTICAL_STRATUM, VALUE_PRJMLW_STATISTICAL_STRATUM);
		MAPPINGS.put(PRJMLW_LOCATION_CATEGORY, VALUE_PRJMLW_LOCATION_CATEGORY);
		MAPPINGS.put(PRJMLW_LOCATION_STATUS, VALUE_PRJMLW_LOCATION_STATUS);
		MAPPINGS.put(PRJMLW_REFERENCE_LOCATION_TYPE_CODE, VALUE_REFERENCE_LOCATION_TYPE_CODE);
		MAPPINGS.put(PRJMLW_REFERENCE_LOCATION_START_DATE, VALUE_REFERENCE_LOCATION_START_DATE);
		MAPPINGS.put(PRJMLW_REFERENCE_LOCATION_END_DATE, VALUE_REFERENCE_LOCATION_END_DATE);
		MAPPINGS.put(REFERENCE_LOCATION_CITATION_TITLE, VALUE_REFERENCE_LOCATION_CITATION_TITLE);
		MAPPINGS.put(REFERENCE_LOCATION_CITATION_CREATOR, VALUE_REFERENCE_LOCATION_CITATION_CREATOR);
		MAPPINGS.put(REFERENCE_LOCATION_CITATION_SUBJECT, VALUE_REFERENCE_LOCATION_CITATION_SUBJECT);
		MAPPINGS.put(REFERENCE_LOCATION_CITATION_PUBLISHER, VALUE_REFERENCE_LOCATION_CITATION_PUBLISHER);
		MAPPINGS.put(REFERENCE_LOCATION_CITATION_DATE, VALUE_REFERENCE_LOCATION_CITATION_DATE);
		MAPPINGS.put(REFERENCE_LOCATION_CITATION_IDENTIFIER, VALUE_REFERENCE_LOCATION_CITATION_IDENTIFIER);
		MAPPINGS.put(PRJMLW_COMMENT, VALUE_PRJMLW_COMMENT);
	}
	
	private ProjectMonitoringLocationWeightingDelimited() {
	}
	
	public static Map<String, String> getMapping(Profile profile) {
		return getMapping(MAPPINGS, profile);
	}
}
