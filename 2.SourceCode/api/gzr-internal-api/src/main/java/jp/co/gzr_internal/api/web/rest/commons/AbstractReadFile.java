/*	
 * Aviva SMS Revamp
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: HiepNH
 * Creation Date : Oct 20, 2019
 */
package jp.co.gzr_internal.api.web.rest.commons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class AbstractReadFile.
 *
 * @param <T> the generic type
 */
public abstract class AbstractReadFile<T> {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(AbstractReadFile.class);


    /**
     * Read file.
     *
     * @param file the file
     * @return the list
     */
    public List<T> readFile(MultipartFile file) {

        Workbook workBook = null;
        List<T> list = new ArrayList<T>();
        try {

            if (file.getOriginalFilename().endsWith(".xls")) {
                workBook = new HSSFWorkbook(file.getInputStream());
            } else if (file.getOriginalFilename().endsWith(".xlsx")) {
                workBook = new XSSFWorkbook(OPCPackage.open(file.getInputStream()));
            } else {

            }
            // Get data to file excel
            list = getData(workBook);

        } catch (Exception e) {
            log.error("Exception: " + e);
        } finally {
            if (workBook != null) {
                try {
                    workBook.close();
                } catch (IOException e) {
                    log.error("Exception: " + e);
                }
            }
        }
        return list;
    }

    /**
     * Gets the data.
     *
     * @param workbook the workbook
     * @return the data
     * @throws Exception the exception
     */
    protected abstract List<T> getData(Workbook workbook) throws Exception;
}
