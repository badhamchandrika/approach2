package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.beans.Cluster;
import ca.airmiles.travel.core.services.ExcelToJsonService;
import com.google.gson.Gson;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

@Component(service = ExcelToJsonService.class, immediate = true)
public class ExcelToJsonServiceImpl implements ExcelToJsonService {
    static final int SHEET_INDEX = 0;
    static final int CLUSTER_ID_CELL = 0;
    static final int PACKAGE_NAME_CELL = 1;
    static final int REDEMPTION_CELL = 4;
    static final int PACKAGE_ID_CELL = 6;
    static final int TRIPTYPE_ID_CELL = 3;

    Gson gson;
    public ExcelToJsonServiceImpl(Gson gson) {
        this.gson = gson;
    }

    public ExcelToJsonServiceImpl() {
        this.gson = new Gson();
    }

    /**
     *
     * @param workbook Excel Workbook (apache poi) object
     * @return InputStream to be converted as Asset
     */
    public InputStream getJson(XSSFWorkbook workbook){
        XSSFSheet sheet = workbook.getSheetAt(SHEET_INDEX);
        ArrayList<Cluster> allItems = new ArrayList<>();
        int lastRow = sheet.getLastRowNum();
        XSSFRow row;
        int i = 0;
        while (i<lastRow){
           i++;
           row = sheet.getRow(i);
           Cluster cluster = new Cluster(row.getCell(CLUSTER_ID_CELL).toString(),row.getCell(PACKAGE_NAME_CELL).toString(),row.getCell(REDEMPTION_CELL).toString(),row.getCell(PACKAGE_ID_CELL).toString(), row.getCell(TRIPTYPE_ID_CELL).toString());
           allItems.add(cluster);
        }
        String jsonOutput = gson.toJson(allItems);
        InputStream is = new ByteArrayInputStream(jsonOutput.getBytes());

        return is;
    }
}
