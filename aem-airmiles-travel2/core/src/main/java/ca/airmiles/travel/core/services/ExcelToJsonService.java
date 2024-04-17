package ca.airmiles.travel.core.services;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

public interface ExcelToJsonService {

    InputStream getJson(XSSFWorkbook payload);
}
