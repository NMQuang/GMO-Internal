/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Otc 11, 2019
 */
package jp.co.gzr_internal.api.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jp.co.gzr_internal.api.service.ExportFileService;
import jp.co.gzr_internal.api.service.dto.response.ListApprovalResponseDto;

/**
 * The Class ExportFileServiceImpl.
 */
@Service
public class ExportFileServiceImpl implements ExportFileService
{

    /** The Constant DIRECTORY. */
    private static final String DIRECTORY = "D:";

    /** The Constant DEFAULT_FILE_NAME. */
    private static final String DEFAULT_FILE_NAME = "ExportListapproval";

    /** The Constant SHEET_NAME. */
    private static final String SHEET_NAME = "ListApproval";

    /**
     * Export list approval.
     *
     * @param keyTotalApprovedOT the key total approved OT
     * @param approvalOT the approval OT
     * @param keyTotalValue the key total value
     * @param keyRowTotal the key row total
     * @param daysOfWeek the days of week
     * @param mapRowTotal the map row total
     * @param listMapDateValue the list map date value
     * @param columns the columns
     * @param listDto the list dto
     * @param numDays the num days
     * @return the response entity
     * @throws Exception the exception
     */
    @Override
    public ResponseEntity<?> exportListApproval(String keyTotalApprovedOT, String approvalOT,
        String keyTotalValue, String keyRowTotal,
        Map<String, String> daysOfWeek, Map<String, Integer> mapRowTotal,
        List<Map<String, Integer>> listMapDateValue, List<String> columns,
        List<ListApprovalResponseDto> listDto,
        Integer numDays, String dateSearch)
        throws Exception {

        // Set variable notCellDay
        // numDays total days of month
        // -1 First cell of row start = 0, -2 by cell of Cell total plan OT, approve OT
        int notCellDay = columns.size() - numDays - 1 - 2;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Create a Workbook
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        Sheet sheet = workbook.createSheet(SHEET_NAME);

        // Set style for Cell name
        CellStyle styleCellName = workbook.createCellStyle();
        styleCellName.setBorderBottom(BorderStyle.THIN);
        styleCellName.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styleCellName.setBorderLeft(BorderStyle.THIN);
        styleCellName.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        styleCellName.setBorderRight(BorderStyle.THIN);
        styleCellName.setRightBorderColor(IndexedColors.BLACK.getIndex());
        styleCellName.setBorderTop(BorderStyle.THIN);
        styleCellName.setTopBorderColor(IndexedColors.BLACK.getIndex());
        styleCellName.setAlignment(HorizontalAlignment.LEFT);

        // Set style for row
        CellStyle styleRowValue = workbook.createCellStyle();
        styleRowValue.setBorderBottom(BorderStyle.THIN);
        styleRowValue.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styleRowValue.setBorderLeft(BorderStyle.THIN);
        styleRowValue.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        styleRowValue.setBorderRight(BorderStyle.THIN);
        styleRowValue.setRightBorderColor(IndexedColors.BLACK.getIndex());
        styleRowValue.setBorderTop(BorderStyle.THIN);
        styleRowValue.setTopBorderColor(IndexedColors.BLACK.getIndex());
        styleRowValue.setVerticalAlignment(VerticalAlignment.CENTER);
        styleRowValue.setAlignment(HorizontalAlignment.CENTER);

        // Set style for row header
        CellStyle headerRowStyle = workbook.createCellStyle();
        headerRowStyle.setBorderBottom(BorderStyle.THIN);
        headerRowStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerRowStyle.setBorderLeft(BorderStyle.THIN);
        headerRowStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerRowStyle.setBorderRight(BorderStyle.THIN);
        headerRowStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerRowStyle.setBorderTop(BorderStyle.THIN);
        headerRowStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerRowStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        headerRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerRowStyle.setAlignment(HorizontalAlignment.CENTER);
        headerRowStyle.setWrapText(true);
        headerRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Set style for row total
        CellStyle totalRowStyle = workbook.createCellStyle();
        totalRowStyle.setBorderBottom(BorderStyle.THIN);
        totalRowStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        totalRowStyle.setBorderLeft(BorderStyle.THIN);
        totalRowStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        totalRowStyle.setBorderRight(BorderStyle.THIN);
        totalRowStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        totalRowStyle.setBorderTop(BorderStyle.THIN);
        totalRowStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        totalRowStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        totalRowStyle.setAlignment(HorizontalAlignment.CENTER);

        // Set style for cell day of week
        CellStyle styleDayOfWeek = workbook.createCellStyle();
        styleDayOfWeek.setBorderBottom(BorderStyle.THIN);
        styleDayOfWeek.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styleDayOfWeek.setBorderLeft(BorderStyle.THIN);
        styleDayOfWeek.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        styleDayOfWeek.setBorderRight(BorderStyle.THIN);
        styleDayOfWeek.setRightBorderColor(IndexedColors.BLACK.getIndex());
        styleDayOfWeek.setBorderTop(BorderStyle.THIN);
        styleDayOfWeek.setTopBorderColor(IndexedColors.BLACK.getIndex());
        styleDayOfWeek.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        styleDayOfWeek.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleDayOfWeek.setAlignment(HorizontalAlignment.CENTER);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        CellRangeAddress cellRangeAddress = null;

        // Set row of header
        int countRowHearder = 2;

        // Create cells header
        for (int k = 0; k < countRowHearder; k++) {
            headerRow = sheet.createRow(k);

            // if row first of header then merger cells is days
            if (k == 0) {
                Cell cell = null;

                // Loop to set value for cells header

                for (int i = 0; i < columns.size(); i++) {

                    // Check if not cell days then set value
                    if (i <= notCellDay || i > numDays) {
                        cell = headerRow.createCell(i);
                        cell.setCellValue(columns.get(i));
                    }

                    // Set value for cell prepare merger
                    if (i == notCellDay + 1) {
                        cell = headerRow.createCell(notCellDay + 1);
                        cell.setCellValue("Số giờ OT");
                    }
                }
                cellRangeAddress = new CellRangeAddress(0, 0, notCellDay + 1, numDays + notCellDay);
                sheet.addMergedRegion(cellRangeAddress);
            } else {
                Cell cell = null;

                // Loop to set value for cell header
                for (int i = 0; i < columns.size(); i++) {

                    // Check if cell of day then set value else merger column and set value is empty
                    if (i > notCellDay && i <= numDays + notCellDay) {
                        cell = headerRow.createCell(i);
                        cell.setCellValue(columns.get(i));
                    } else {
                        cell = headerRow.createCell(i);
                        cell.setCellValue("");
                        sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));
                    }
                }
            }
        }

        // Create Other rows and cells with list data
        List<String> listProject = new ArrayList<String>();
        // Loop get list project name order by project Name
        for (ListApprovalResponseDto listApproval : listDto) {
            String projectName = listApproval.getProjectName();
            // Check if exist list project name then check exist or add new
            if (listProject.size() > 0) {
                boolean isExist = false;
                for (int i = 0; i < listProject.size(); i++) {
                    // check exit project name
                    if (listProject.get(i).equals(projectName)) {
                        isExist = true;
                    }
                }
                // Add project name into if project name is not exist in list
                if (isExist == false) {
                    listProject.add(listApproval.getProjectName());
                }
            } else {
                listProject.add(listApproval.getProjectName());
            }
        }
        // Row now start
        int rowNum = countRowHearder;
        // Index to count when process each value of list
        int index = 0;

        int countIndexKey = 0;

        // Count row to merger
        int countMerger = 0;

        for (ListApprovalResponseDto listApproval : listDto) {
            Row row = sheet.createRow(rowNum++);

            // Key is project name of list apparoval
            String key = listApproval.getProjectName();

            // Project name of list project
            String projectName = listProject.get(countIndexKey);

            // Check key is same, if same key then count merger row will +1
            if (key.equals(projectName)) {
                countMerger++;
            } else {
                // Check if count >1 then will merger row
                if (countMerger > 1) {
                    cellRangeAddress =
                        new CellRangeAddress((rowNum) - countMerger - 1, rowNum - 2, 4, 4);
                    sheet.addMergedRegion(cellRangeAddress);
                }
                // Set count merger default
                countMerger = 1;
                // Another element whenever the key doesn't match
                countIndexKey++;
            }

            // Set value for cell 'No'
            row.createCell(0)
                .setCellValue(rowNum - countRowHearder);

            // Set value for cell Employee code
            row.createCell(1)
                .setCellValue(listApproval.getEmployeeCode());

            // Set value for cell Employee name
            row.createCell(2)
                .setCellValue(listApproval.getEmployeeName());

            // Set value for cell Position name
            row.createCell(3)
                .setCellValue(listApproval.getPositionName());

            // Set value for cell Project name
            row.createCell(4)
                .setCellValue(listApproval.getProjectName());

            int countCell = 0;
            for (int i = 1; i <= numDays; i++) {
                String keyOfDay = String.valueOf(i);
                // if i < 10 then set key ="0" + value i
                // example: 1 => 01, 2=> 02
                if (i < 10) {
                    keyOfDay = "0" + i;
                }
                // if key is exist then set value for cell else set cell =0
                if (listMapDateValue.get(index).containsKey(keyOfDay)) {
                    row.createCell(i + notCellDay)
                        .setCellValue(listMapDateValue.get(index).get(keyOfDay));
                } else {
                    row.createCell(i + notCellDay)
                        .setCellValue(0);
                }

                // Check Sunday end Saturday
                if (daysOfWeek.containsKey(keyOfDay)) {
                    row.getCell(i + notCellDay).setCellStyle(styleDayOfWeek);
                }
                countCell = (i + notCellDay);
            }

            // Set value for cell total plan OT
            row.createCell(countCell + 1)
                .setCellValue(listMapDateValue.get(index).get(keyTotalValue));

            // Set value for cell total approval time OT
            row.createCell(countCell + 2)
                .setCellValue(listMapDateValue.get(index).get(approvalOT));
            index++;
        }

        // Check if count >1 then will merger row
        if (countMerger > 1) {
            cellRangeAddress =
                new CellRangeAddress((rowNum) - countMerger, rowNum - 1, 4, 4);
            sheet.addMergedRegion(cellRangeAddress);
        }

        // Create row Total
        // Set value for row total
        Row row = sheet.createRow(rowNum++);
        row.createCell(0)
            .setCellValue("");
        row.createCell(1)
            .setCellValue("");
        row.createCell(2)
            .setCellValue("TỔNG CỘNG");
        row.createCell(3)
            .setCellValue("");
        row.createCell(4)
            .setCellValue("");
        int countCell = 0;

        /*
         * Loop to set value for cell of row total
         */
        for (int i = 1; i <= numDays; i++) {
            String key = String.valueOf(i);

            // if i < 10 then set key ="0" + value i
            // ex: 1 => 01, 2=> 02
            if (i < 10) {
                key = "0" + i;
            }

            // if key is exist then set value for cell else set cell =0
            if (mapRowTotal.containsKey(key)) {
                row.createCell(i + notCellDay)
                    .setCellValue(mapRowTotal.get(key));
            } else {
                row.createCell(i + notCellDay)
                    .setCellValue(0);
            }

            // Check Sunday end Saturday
            if (daysOfWeek.containsKey(key)) {
                row.getCell(i + notCellDay).setCellStyle(styleDayOfWeek);
            }
            countCell = (i + notCellDay);
        }

        // Set value for cell total plan ot
        row.createCell(countCell + 1)
            .setCellValue(mapRowTotal.get(keyRowTotal));

        // Set value for cell total approval ot
        row.createCell(countCell + 2)
            .setCellValue(mapRowTotal.get(keyTotalApprovedOT));

        /*
         * Set style for row value
         * 
         */
        index = 0;
        boolean isCellDay = false;
        // Loop Row of sheet
        for (Row item : sheet) {
            int cellName = 0;
            // Loop cells of row
            for (Cell cell : item) {
                // Check if cell is not cell of days and if index is cell of days then set index =1 and isCellDay=true;
                if (index == (notCellDay + 1) && isCellDay == false) {
                    index = 1;
                    isCellDay = true;
                }
                String key = String.valueOf(index);
                // Check Sunday end Saturday
                // set style for day of week of row total
                if (daysOfWeek.containsKey(String.valueOf(key)) && isCellDay == true) {
                    cell.setCellStyle(styleDayOfWeek);
                } else {
                    cell.setCellStyle(styleRowValue);
                }
                if (cellName == 2) {
                    // Set style for cell name
                    cell.setCellStyle(styleCellName);
                }
                index++;
                cellName++;
            }
            isCellDay = false;
            index = 0;
        }
        /*
         * Set style for row headers
         */
        index = 0;
        isCellDay = false;
        for (Row items : sheet) {
            // Check if row header then set style
            if (index <= 1) {
                int countCellOfRow = 0;
                for (Cell cells : items) {
                    if (index > 0) {
                        // Check if cell is not cell of days and if index is cell of days then set index =1 and
                        // isCellDay=true;
                        if (countCellOfRow == (notCellDay + 1) && isCellDay == false) {
                            countCellOfRow = 1;
                            isCellDay = true;
                        }
                        String key = String.valueOf(countCellOfRow);
                        /*
                         * Check Sunday end Saturday
                         */
                        // set style for day of week of row total
                        if (daysOfWeek.containsKey(String.valueOf(key)) && isCellDay == true) {
                            cells.setCellStyle(styleDayOfWeek);
                        } else {
                            cells.setCellStyle(headerRowStyle);
                        }
                        //
                        countCellOfRow++;
                    } else {
                        cells.setCellStyle(headerRowStyle);
                    }
                }
            } else {
                break;
            }
            index++;
        }
        /*
         * Set style for row total
         */
        index = 0;
        isCellDay = false;
        for (Cell cell : sheet.getRow(sheet.getLastRowNum())) {
            // Check if cell is not cell of days and if index is cell of days then set index =1 and isCellDay=true;
            if (index == (notCellDay + 1) && isCellDay == false) {
                index = 1;
                isCellDay = true;
            }
            String key = String.valueOf(index);
            /*
             * Check Sunday end Saturday
             */
            // set style for day of week of row total
            if (daysOfWeek.containsKey(String.valueOf(key)) && isCellDay == true) {
                cell.setCellStyle(styleDayOfWeek);
            } else {
                cell.setCellStyle(totalRowStyle);
            }
            index++;
        }
        // Resize all columns to fit the content size
        for (int i = 0; i < columns.size(); i++) {
            sheet.autoSizeColumn(i, true);
        }

        HttpHeaders responseHeader = new HttpHeaders();
        InputStreamResource inputStreamResource = null;
        String fileName = DEFAULT_FILE_NAME + "_" + dateSearch + ".xlsx";
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] barray = bos.toByteArray();
         // Set return mimeType
            responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            // Set return information
            responseHeader.set("Content-disposition", "attachment; filename=" + fileName);
            responseHeader.setContentLength(barray.length);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(barray));
            inputStreamResource = new InputStreamResource(inputStream);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);

        
    }
}
