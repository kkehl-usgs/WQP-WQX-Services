package gov.usgs.wma.wqp;

import static gov.usgs.wma.wqp.openapi.model.StationCountJson.NWIS;
import static gov.usgs.wma.wqp.openapi.model.StationCountJson.STEWARDS;
import static gov.usgs.wma.wqp.openapi.model.StationCountJson.STORET;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import gov.usgs.wma.wqp.mapping.Profile;
import gov.usgs.wma.wqp.parameter.BoundingBox;
import gov.usgs.wma.wqp.parameter.Command;
import gov.usgs.wma.wqp.transform.Transformer;

public abstract class BaseTest {
	protected LocalDate currentDate = LocalDate.now();

	public String harmonizeXml(String xmlDoc) {
		return xmlDoc.replace("\r", "").replace("\n", "").replace("\t", "").replaceAll("> *<", "><");
	}

	public String getSourceFile(String file) throws IOException {
		return new String(FileCopyUtils.copyToByteArray(new ClassPathResource("testData/" + file).getInputStream()));
	}

	public String getCompareFile(String file) throws IOException {
		return new String(FileCopyUtils.copyToByteArray(new ClassPathResource("testResult/" + file).getInputStream()));
	}
	
	public String adjustCsvDatesToTimeFromCurrentYear(String csvString) {
		String adjustedDateString = csvString.replaceAll("\\[year\\-0\\]", String.valueOf(currentDate.getYear()))
				.replaceAll("\\[year\\-1\\]", String.valueOf(currentDate.getYear() - 1))
				.replaceAll("\\[year\\-2\\]", String.valueOf(currentDate.getYear() - 2))
				.replaceAll("\\[year\\-3\\]", String.valueOf(currentDate.getYear() - 3))
				.replaceAll("\\[year\\-4\\]", String.valueOf(currentDate.getYear() - 4))
				.replaceAll("\\[year\\-5\\]", String.valueOf(currentDate.getYear() - 5));			
		
		return adjustedDateString;
	}	
	
	public String getCompareFile(Profile profile, String fileType, String suffix) throws IOException {
		String fileName = "testResult/" + profile.toString() + "/" + profile.toString() + (null == suffix ? "" : suffix) + "." + fileType;
		String fileForCompareAsString = new String(FileCopyUtils.copyToByteArray(new ClassPathResource(fileName).getInputStream()));
		if (fileType.equals("csv")) {
			fileForCompareAsString = adjustCsvDatesToTimeFromCurrentYear(fileForCompareAsString);
		}

		return fileForCompareAsString;
	}

	public String extractZipContent(byte[] content, String expectedEntryName) throws IOException {
		ZipInputStream in = new ZipInputStream(new ByteArrayInputStream(content));
		ZipEntry e = in.getNextEntry();
		assertEquals(expectedEntryName, e.getName());

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		int len;
		byte[] buffer = new byte[1024];
		while ((len = in.read(buffer)) > 0) {
			os.write(buffer, 0, len);
		}
		return os.toString();
	}

	public static URL TEST_NLDI_URL;
	static {
		try {
			TEST_NLDI_URL = new URL("https://cida-test.er.usgs.gov/nldi/wqp/USGS-05427880/navigate/UM/wqp");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static int BASE_HEADER_COUNT = 4;

	public static String TOTAL_SITE_COUNT = "13";
	public static String NWIS_SITE_COUNT = "4";
	public static String STEWARDS_SITE_COUNT = "2";
	public static String STORET_SITE_COUNT = "7";

	//The test data has a case where both a nwis and a storet site have no activity/result data associated with them. Thus they should not show up in any queries/counts below the station level.
	public static String TOTAL_SITE_COUNT_MINUS_1 = "11";
	public static String STORET_SITE_COUNT_MINUS_1 = "6";
	public static String NWIS_SITE_COUNT_MINUS_1 = "3";

	//The test data has a case where a nwis site has no geom associated with it. Thus it should not show up in the json formats. 
	//Look at dynamicWhere.spatialNeeded if you need to add a dataprofile.
	public static String TOTAL_SITE_COUNT_GEOM = "12";
	public static String BIOLOGICAL_METRIC_SITE_COUNT_GEOM = "13";
	public static String NWIS_SITE_COUNT_GEOM = "3";

	public static String TOTAL_ACTIVITY_COUNT = "23";
	public static String NWIS_ACTIVITY_COUNT = "4";
	public static String STEWARDS_ACTIVITY_COUNT = "3";
	public static String STORET_ACTIVITY_COUNT = "16";

	public static String TOTAL_ACTIVITY_METRIC_COUNT = "28";
	public static String NWIS_ACTIVITY_METRIC_COUNT = "4";
	public static String STEWARDS_ACTIVITY_METRIC_COUNT = "3";
	public static String STORET_ACTIVITY_METRIC_COUNT = "21";

	public static String TOTAL_RESULT_COUNT = "55";
	public static String NWIS_RESULT_COUNT = "6";
	public static String STEWARDS_RESULT_COUNT = "3";
	public static String STORET_RESULT_COUNT = "46";

	public static String TOTAL_RES_DETECT_QNT_LMT_COUNT = "70";
	public static String NWIS_RES_DETECT_QNT_LMT_COUNT = "8";
	public static String STEWARDS_RES_DETECT_QNT_LMT_COUNT = "9";
	public static String STORET_RES_DETECT_QNT_LMT_COUNT = "53";

	public static String TOTAL_PROJECT_COUNT = "17";
	public static String NWIS_PROJECT_COUNT = "5";
	public static String STEWARDS_PROJECT_COUNT = "2";
	public static String STORET_PROJECT_COUNT = "10";
	
	public static String TOTAL_BIOLOGICAL_METRIC_COUNT = "13";
	public static String NWIS_BIOLOGICAL_METRIC_COUNT = "4";
	public static String STEWARDS_BIOLOGICAL_METRIC_COUNT = "2";
	public static String STORET_BIOLOGICAL_METRIC_COUNT = "7";

	public static String TOTAL_PRJ_ML_WEIGHTING_COUNT = "7";
	public static String NWIS_PRJ_ML_WEIGHTING_COUNT = "3";
	public static String STEWARDS_PRJ_ML_WEIGHTING_COUNT = "1";
	public static String STORET_PRJ_ML_WEIGHTING_COUNT = "3";

	public static String TOTAL_ORGANIZATION_COUNT = "8";
	public static String NWIS_ORGANIZATION_COUNT = "2";
	public static String STEWARDS_ORGANIZATION_COUNT = "1";
	public static String STORET_ORGANIZATION_COUNT = "5";

	public static final String FILTERED_STORET_SITE_COUNT = "1";
	public static final String FILTERED_TOTAL_SITE_COUNT = "1";
	public static final String FILTERED_STORET_ACTIVITY_COUNT = "2";
	public static final String FILTERED_TOTAL_ACTIVITY_COUNT = "2";
	public static final String FILTERED_STORET_ACTIVITY_METRIC_COUNT = "2";
	public static final String FILTERED_TOTAL_ACTIVITY_METRIC_COUNT = "2";
	public static final String FILTERED_STORET_RESULT_COUNT = "4";
	public static final String FILTERED_TOTAL_RESULT_COUNT = "4";
	public static final String FILTERED_STORET_RES_DETECT_QNT_LMT_COUNT = "7";
	public static final String FILTERED_TOTAL_RES_DETECT_QNT_LMT_COUNT = "7";
	public static final String FILTERED_TOTAL_PROJECT_COUNT = "1";
	public static final String FILTERED_STORET_PROJECT_COUNT = "1";
	public static final String FILTERED_TOTAL_PRJ_ML_WEIGHTING_COUNT = "1";
	public static final String FILTERED_TOTAL_ORGANIZATION_COUNT = "1";
	public static final String FILTERED_STORET_ORGANIZATION_COUNT = "1";

	public static final Integer STEWARDS_ID = Integer.valueOf(1);
	public static final Integer NWIS_ID = Integer.valueOf(2);
	public static final Integer STORET_ID = Integer.valueOf(3);

	public static final String AND_ZIP = "&zip=yes";
	public static final String CSV = "csv";
	public static final String CSV_AND_ZIP = CSV + AND_ZIP;
	public static final String TSV = "tsv";
	public static final String TSV_AND_ZIP = TSV + AND_ZIP;
	public static final String XLSX = "xlsx";
	public static final String XLSX_AND_ZIP = XLSX + AND_ZIP;
	public static final String XML = "xml";
	public static final String XML_AND_ZIP = XML + AND_ZIP;
	public static final String KML = "kml";
	public static final String KML_AND_ZIP = KML + AND_ZIP;
	public static final String KMZ = "kmz";
	public static final String KMZ_AND_ZIP = KMZ + AND_ZIP;
	public static final String GEOJSON = "geojson";
	public static final String GEOJSON_AND_ZIP = GEOJSON + AND_ZIP;
	public static final String JSON = "json";
	public static final String JSON_AND_ZIP = JSON + AND_ZIP;


	public List<String> getAnalyticalMethod() {
		return Arrays.asList("https://www.nemi.gov/methods/method_summary/4665/", "https://www.nemi.gov/methods/method_summary/8896/", "https://analyticalMethod");
	}

	public static String getActivity() {
		return "WIDNR_WQX-7788475-5";
	}

	public Command getCommand() {
		return new Command(NWIS);
	}

	public BoundingBox getBBox() {
		return new BoundingBox("-111,39,-77,47");
	}

	public List<String> getAssemblage() {
		return Arrays.asList("Fish/Nekton", "Benthic Macroinvertebrates", "assemblageSampledName");
	}

	public List<String> getCharacteristicName() {
		return Arrays.asList("Beryllium", "Nitrate", "characteristicName");
	}

	public List<String> getCharacteristicType() {
		return Arrays.asList("Inorganics, Minor, Metals", "Nutrient", "Population/Community", "characteristicType");
	}

	public List<String> getCountry() {
		return Arrays.asList("MX", "US", "XX");
	}

	public List<String> getCounty() {
		return Arrays.asList("US:19:015", "US:30:003", "US:55:017", "US:55:021", "US:55:027", "XX:44:555");
	}

	public List<String> getHuc() {
		return Arrays.asList("07*", "0708*", "070801*", "07090002", "07080105", "0000", "0708010509", "070801050907");
	}

	public List<String> getHuc2() {
		return Arrays.asList("07");
	}

	public List<String> getHuc3() {
		return Arrays.asList("07*");
	}

	public List<String> getHuc4() {
		return Arrays.asList("0709");
	}

	public List<String> getHuc5() {
		return Arrays.asList("0709*");
	}

	public List<String> getHuc6() {
		return Arrays.asList("070900");
	}

	public List<String> getHuc7() {
		return Arrays.asList("070900*");
	}

	public List<String> getHuc8() {
		return Arrays.asList("07090001");
	}

	public List<String> getHuc10() {
		return Arrays.asList("0709000111");
	}

	public List<String> getHuc12() {
		return Arrays.asList("070900011105");
	}

	public String getLatitude() {
		return "43.3836014";
	}

	public String getLongitude() {
		return "-88.9773314";
	}

	public String getMinActivities() {
		return "2";
	}

	public String getMinResults() {
		return "3";
	}

	public List<String> getNldiSites() {
		return Arrays.asList("11NPSWRD-BICA_MFG_B", "WIDNR_WQX-10030952", "USGS-05425700",
				"USGS-431925089002701", "ARS-IAWC-IAWC225", "ARS-IAWC-IAWC410", "USGS-11421000", "organization-siteId2", "organization-siteId3");
	}

	public String getNldiurl() {
		return TEST_NLDI_URL.toString();
	}

	public List<String> getOrganization() {
		return Arrays.asList("ARS", "11NPSWRD", "USGS-WI", "WIDNR_WQX", "organization");
	}

	public List<String> getPcode() {
		return Arrays.asList("00032", "00004", "99999");
	}

	public List<String> getProject() {
		return Arrays.asList("projectId", "CEAP", "NAWQA", "SACR TDB");
	}
	public static String getRestProject() {
		return "WR047";
	}
	public static List<String> getRestProjects() {
		return Arrays.asList(getRestProject());
	}

	public List<String> getProviders() {
		return Arrays.asList(NWIS, STEWARDS, STORET);
	}

	public static String getRestProvider() {
		return "STORET";
	}
	public List<String> getRestProviders() {
		return Arrays.asList(getRestProvider());
	}
	public static String getRestOrganization() {
		return "WIDNR_WQX";
	}
	public List<String> getRestOrganizations() {
		return Arrays.asList(getRestOrganization());
	}
	public static String getResultId() {
		return "5";
	}

	public List<String> getSampleMedia() {
		return Arrays.asList("sampleMedia", "Other", "Sediment", "Water");
	}

	public List<String> getSiteId() {
		return Arrays.asList("organization-siteId", "organization-siteId2", "organization-siteId3", "11NPSWRD-BICA_MFG_B", "WIDNR_WQX-10030952", "USGS-05425700", "USGS-431925089002701", "ARS-IAWC-IAWC225", "ARS-IAWC-IAWC410");
	}

	public List<String> getSiteIdLargeList() throws IOException {
		return Arrays.asList(getSourceFile("manySites.txt").split(","));
	}

	public List<String> getSiteType() {
		return Arrays.asList("siteType", "Lake, Reservoir, Impoundment", "Land", "Stream");
	}

	public static String getSiteUrlBase() {
		return "http://siteUrlBase";
	}

	public String getStartDateHi() {
		return "10-11-2012";
	}

	public String getStartDateLo() {
		return "10-11-1995";
	}

	public List<String> getState() {
		return Arrays.asList("XX:44", "US:19", "US:30", "US:55");
	}

	public List<String> getSubjectTaxonomicName() {
		return Arrays.asList("Acipenser", "Lota lota", "sampleTissueTaxonomicName");
	}
	
	public String getSummaryYears() {
		return "1";
	}

	public String getWithin() {
		return "650";
	}

	public String getZip() {
		return "yes";
	}

	public void assertMapIsAsExpected(Map<String, Object> expectedRow, Map<String, Object> actualRow) {
		//Doing both left to right and right to left to catch missing/extra on either side.
		for (String i : expectedRow.keySet()) {
			assertEquals(Transformer.getStringValue(expectedRow.get(i)), Transformer.getStringValue(actualRow.get(i)), i);
		}
		for (String i : actualRow.keySet()) {
			assertEquals(Transformer.getStringValue(actualRow.get(i)), Transformer.getStringValue(expectedRow.get(i)), i);
		}
	}

}
