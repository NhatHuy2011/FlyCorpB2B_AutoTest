package org.example.common;

import org.example.constant.Constant;
import org.example.utils.ExcelUtils;

public class WriteResultExcel {

    public static void writeResultExcel(
            String sheetName,
            int rowIndex,
            TestResult result,
            int actualIndex,
            int statusIndex
    ) {
        ExcelUtils.writeTestResults(
                Constant.EXCEL_FILE_PATH,
                sheetName,
                rowIndex,
                result.getActualMessage(),
                actualIndex,
                result.isPassed() ? "Pass" : "Fail",
                statusIndex
        );
    }
}
