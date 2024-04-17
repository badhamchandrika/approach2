package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.beans.Cluster;
import com.google.gson.Gson;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;

import static ca.airmiles.travel.core.services.impl.ExcelToJsonServiceImpl.SHEET_INDEX;
import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public  class ExcelToJsonServiceImplTest {

    ExcelToJsonServiceImpl fixture;

    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();



    @BeforeEach
    void setUp() throws IOException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Gson gson = mock(Gson.class);
        doReturn("").when(gson).toJson(anyCollection());
        fixture = new ExcelToJsonServiceImpl(gson);
    }

    @Test
    void test() throws IOException {

        XSSFWorkbook mockWorkbook = mock(XSSFWorkbook.class);
        XSSFSheet mockSheet = mock(XSSFSheet.class);
        XSSFRow mockRow = mock(XSSFRow.class);
        XSSFCell mockCell = mock(XSSFCell.class);
        Cluster mockCluster = mock(Cluster.class);

        doReturn(mockSheet).when(mockWorkbook).getSheetAt(SHEET_INDEX);
        doReturn(4).when(mockSheet).getLastRowNum();
        doReturn(mockRow).when(mockSheet).getRow(anyInt());
        doReturn(mockCell).when(mockRow).getCell(anyInt());

        InputStream sheet = fixture.getJson(mockWorkbook);

    }



}
