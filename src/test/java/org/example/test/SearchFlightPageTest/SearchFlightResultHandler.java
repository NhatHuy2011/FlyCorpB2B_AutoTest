package org.example.test.SearchFlightPageTest;

import org.example.constant.Constant;
import org.example.utils.ExcelUtils;

public class SearchFlightResultHandler {
    public static void writeResult(
            int rowIndex,
            SearchFlightTestRunner.TestResult result
    ) {
        ExcelUtils.writeTestResults(
                Constant.EXCEL_FILE_PATH,
                Constant.EXCEL_SEARCHFLIGHT_SHEET,
                rowIndex,
                result.actualMessage,
                21,
                result.isPassed ? "Pass" : "Fail",
                22
        );
    }
}
