package pe.com.empresa.poc.azure.projectms.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorConstant {

    public static final String MANDATORY_FIELD_NO_PRESENT = "ERROR0001";
    public static final String DEFAULT_ERROR = "ERROR0002";
    public static final String UNIQUE_CODE_INVALID = "ERROR0003";
    public static final String REQUEST_INVALID = "ERROR0004";
    public static final String RANGE_DATE_INVALIDATED = "ERROR0005";
    public static final String NO_DATA_FOUND = "ERROR0006";
    public static final String CATEGORY_PARENT_CODE_INVALID = "ERROR0007";
    public static final String OPERATION_TYPE_INVALID = "ERROR0008";
    public static final String SEARCH_DATE_FROM_INVALID = "ERROR0009";
    public static final String SEARCH_DATE_UP_TO_INVALID = "ERROR0010";


}
