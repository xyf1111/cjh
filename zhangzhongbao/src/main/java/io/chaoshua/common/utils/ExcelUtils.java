package io.chaoshua.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Excel工具类
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2018-04-17 14:47
 */
public class ExcelUtils {
    private final static String EXCEL_TYPE_XLS = "xls";
    private final static String EXCEL_TYPE_XLSX = "xlsx";

    /**
     * 读入excel文件，解析后返回
     *
     * @param file 待解析文件
     * @throws IOException
     */
    public static List<String[]> readExcel(MultipartFile file) throws IOException {
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getLastCellNum();
                    if (lastCellNum == -1){
                        continue;
                    }
                    String[] cells = new String[lastCellNum];
                    //循环当前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }

    /**
     * 检查文件
     *
     * @param file 待检查文件
     * @throws IOException
     */
    public static Integer checkFile(MultipartFile file) throws IOException {
        Integer flag = 1;
        //判断文件是否存在
        if (null == file) {
            flag = 2;
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith(EXCEL_TYPE_XLS) && !fileName.endsWith(EXCEL_TYPE_XLSX)) {
            flag = 3;
        }
        return flag;
    }

    /**
     * 检查文件
     *
     * @param file 判断文件类型
     * @throws IOException
     */
    public static Integer checkFileName(MultipartFile file) throws IOException {
        Integer flag = 1;
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (fileName.endsWith(EXCEL_TYPE_XLS)) {
            flag = 1;
        } else if (fileName.endsWith(EXCEL_TYPE_XLSX)) {
            flag = 2;
        }
        return flag;
    }

    /**
     * 根据不同的excel文件类型，获取工作簿
     *
     * @param file 文件
     * @return
     */
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(EXCEL_TYPE_XLS)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(EXCEL_TYPE_XLSX)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return workbook;
    }

    /**
     * 根据类型获取单元格的值
     *
     * @param cell cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            cell.setCellType(CellType.STRING);
        }
        //判断数据的类型
        switch (cell.getCellTypeEnum()) {
            //数字
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            //字符串
            case STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            //Boolean
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            //公式
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            //空值
            case BLANK:
                cellValue = "";
                break;
            //故障
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 一个不带有头信息,数据信息的空的excel文件
     * 在填充sheet数据的时候,会需要一个空的Excel文件,用于设置Sheet信息的时候用到
     *
     * @return HSSFWorkbook
     */
    public static HSSFWorkbook createExcelFile() {
        return new HSSFWorkbook();
    }

    /**
     * 创建一个空的带有头信息的excel
     *
     * @param fileName 文件名称
     * @param heads    头部
     * @return: HSSFWorkbook 创建一个空的带有头信息的excel
     */
    public static HSSFWorkbook createExcelFile(String fileName, List<String> heads) {
        HSSFWorkbook wb = new HSSFWorkbook();
        if (StringUtils.isEmpty(fileName) || null == heads) {
            return null;
        } else {
            HSSFSheet sheet = wb.createSheet(fileName);
            HSSFRow row = sheet.createRow(0);
            // 封装头信息
            for (int index = 0; index < heads.size(); index++) {
                row.createCell(index).setCellValue(heads.get(index));
            }
        }
        return wb;
    }

    /**
     * 创建excel,带有头信息和数据
     *
     * @param fileName excel表格文件名称
     * @param heads    excel表格的头信息
     * @param dataList excel表格要填充的数据
     * @return: HSSFWorkbook
     */
    public static HSSFWorkbook createExcelFile(String fileName, List<String> heads, List<String[]> dataList) {
        HSSFWorkbook wb = new HSSFWorkbook();
        if (StringUtils.isEmpty(fileName) || null == heads || null == dataList) {
            return null;
        } else {
            HSSFSheet sheet = wb.createSheet(fileName);
            //设置统一单元格的高度，就用这个方法
            sheet.setDefaultRowHeight((short) 400);
            ///sheet.setDefaultColumnWidth(20 * 400);
            //设置样式
            HSSFCellStyle style = wb.createCellStyle();
            //设置字体
            HSSFFont font = wb.createFont();
            //字号
            font.setFontHeightInPoints((short) 12);
            style.setFont(font);
            //左右居中
            style.setAlignment(HorizontalAlignment.CENTER);
            //上下居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            HSSFRow row = sheet.createRow(0);
            row.setRowStyle(style);
            //列宽集合
            List<Integer> columnWidthList = new ArrayList<>();
            //封装头信息
            for (int index = 0; index < heads.size(); index++) {
                HSSFCell cell = row.createCell(index);
                cell.setCellStyle(style);
                cell.setCellValue(heads.get(index));
                //获取列宽
                columnWidthList.add(heads.get(index).length());
            }
            //填充数据信息
            for (int i = 0; i < dataList.size(); i++) {
                HSSFRow rowData = sheet.createRow(i + 1);
                Integer colLength;
                for (int j = 0; j < dataList.get(i).length; j++) {
                    HSSFCell cell = rowData.createCell(j);
                    cell.setCellStyle(style);
                    String cellValue = dataList.get(i)[j];
                    cell.setCellValue(dataList.get(i)[j]);
                    if (StringUtils.isNotBlank(cellValue)){
                        colLength = cellValue.length();
                        //不包含中文
                        if (!isContainsChinese(dataList.get(i)[j])) {
                            colLength = colLength / 2;
                        }
                        if (columnWidthList.get(j) < colLength) {
                            columnWidthList.set(j, colLength);
                        }
                    }
                }
            }
            //设置列宽、
            for (int columnIndex = 0; columnIndex < columnWidthList.size(); columnIndex++) {
                sheet.setColumnWidth(columnIndex, columnWidthList.get(columnIndex) * 2 * 256 + 4 * 256);
            }
        }
        return wb;
    }

    /**
     * 是否包含中文
     *
     * @param str
     * @return 返回true为包含中文；false不包含
     */
    private static boolean isContainsChinese(String str) {
        Pattern pattern = compile("[\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(str);
        boolean flag = false;
        if (matcher.find()) {
            flag = true;
        }
        return flag;
    }

    //从03格式excel中获取图片
    public static Map<String, PictureData> getSheetPictrues03(int sheetNum, HSSFSheet sheet, HSSFWorkbook workbook) {
        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
        List<HSSFPictureData> pictures = workbook.getAllPictures();
        if (pictures.size() != 0) {
            for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
                HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
                if (shape instanceof HSSFPicture) {
                    HSSFPicture pic = (HSSFPicture) shape;
                    int pictureIndex = pic.getPictureIndex() - 1;
                    HSSFPictureData picData = pictures.get(pictureIndex);
                    String picIndex = String.valueOf(sheetNum) + "_"
                            + String.valueOf(anchor.getRow1()) + "_"
                            + String.valueOf(anchor.getCol1());
                    sheetIndexPicMap.put(picIndex, picData);
                }
            }
            return sheetIndexPicMap;
        } else {
            return null;
        }
    }

    //07格式excel获取图片。
    public static Map<String, PictureData> getSheetPictrues07(int sheetNum, XSSFSheet sheet, XSSFWorkbook workbook) {
        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();

        for (POIXMLDocumentPart dr : sheet.getRelations()) {
            if (dr instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) dr;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture pic = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = pic.getPreferredSize();
                    CTMarker ctMarker = anchor.getFrom();
                    String picIndex = String.valueOf(sheetNum) + "_"
                            + ctMarker.getRow() + "_" + ctMarker.getCol();
//                    pic.getPictureData().getData();
                    sheetIndexPicMap.put(picIndex, pic.getPictureData());
                }
            }
        }

        return sheetIndexPicMap;
    }

//图片及位置获取

    public static void getAllDate(String excelPath) throws InvalidFormatException, IOException {

        // 创建文件
        File file = new File(excelPath);

        // 创建流
        InputStream input = new FileInputStream(file);

        // 获取文件后缀名
        String fileExt = file.getName().substring(file.getName().lastIndexOf(".") + 1);

        // 创建Workbook
        Workbook wb = null;

        // 创建sheet
        Sheet sheet = null;

        //根据后缀判断excel 2003 or 2007+
        if (fileExt.equals("xls")) {
            wb = (HSSFWorkbook) WorkbookFactory.create(input);
        } else {
            wb = new XSSFWorkbook(input);
        }

        //获取excel sheet总数
        int sheetNumbers = wb.getNumberOfSheets();

        // sheet list
        List<Map<String, PictureData>> sheetList = new ArrayList<Map<String, PictureData>>();

        // 循环sheet
        for (int i = 0; i < sheetNumbers; i++) {

            sheet = wb.getSheetAt(i);
            // map等待存储excel图片
            Map<String, PictureData> sheetIndexPicMap;

            // 判断用07还是03的方法获取图片
            if (fileExt.equals("xls")) {
                sheetIndexPicMap = getSheetPictrues03(i, (HSSFSheet) sheet, (HSSFWorkbook) wb);
            } else {
                sheetIndexPicMap = getSheetPictrues07(i, (XSSFSheet) sheet, (XSSFWorkbook) wb);
            }
            // 将当前sheet图片map存入list
            sheetList.add(sheetIndexPicMap);
        }
//        Map map = getData(excelPath);
        printImg(sheetList);

    }

    //将图片保存到指定位置
    public static void printImg(List<Map<String, PictureData>> sheetList) throws IOException {

        for (Map<String, PictureData> map : sheetList) {
            Object key[] = map.keySet().toArray();
            for (int i = 0; i < map.size(); i++) {
                // 获取图片流
                PictureData pic = map.get(key[i]);
                // 获取图片索引
                String picName = key[i].toString();
                // 获取图片格式
                String ext = pic.suggestFileExtension();

                byte[] data = pic.getData();

                FileOutputStream out = new FileOutputStream("D:\\pic\\img" + picName + "." + ext);
                out.write(data);
                out.close();
            }
        }

    }
}
