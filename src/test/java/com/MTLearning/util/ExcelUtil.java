package com.MTLearning.util;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: api_auto_learing
 * @description: Excel读写工具类
 * @author: Mr.Wang
 * @create: 2019-10-02 12:19
 **/
public class ExcelUtil {
    //一个用例编号对应一个行索引
    public static Map<String, Integer> caseIdRownumMapping = new HashMap<>();
    //一列的列名对应一个列索引
    public static Map<String, Integer> cellNameCellnumMapping = new HashMap<>();
    //将文件路径维护成一个常量
    public static final String EXCEL_PATH = "src/test/resources/cases_raptor.xlsx";

    //为了保证调用write方法之前，上述两个map中有数据，属于初始化的工作，放到静态代码块中
    static {
        loadRownumAndCellnumMapping();
    }


    /**
     * @param sheetName
     * @param clazz     用字节码来接收
     * @param <T>
     * @return
     */
    public static <T> List<T> read(String sheetName, Class<T> clazz) {
        InputStream inputStream = null;
        List<T> casesList = new ArrayList<>();
        try {
            inputStream = new FileInputStream(new File(EXCEL_PATH));
            //1.通过工厂方法创建workbook对象（打开一个Excel就是一个工作簿）
            Workbook workbook = WorkbookFactory.create(inputStream);
            //2.获取表单对象sheet
            Sheet sheet = workbook.getSheet(sheetName);
            //3、先处理第一行(标题行)，拿到每个列的列名，拼接对应的set方法
            Row titleRow = sheet.getRow(0);
            //标题行有多少不为空的列(从1开始,比如共2列，cellNum为2)
            int cellNum = titleRow.getLastCellNum();
            //准备一个数组，用来存放标题列的标题
            String[] titles = new String[cellNum];
            //通过循环，依次取出标题行的每一列
            for (int i = 0; i < cellNum; i++) {
                Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //指定列的类型 - 指定为String类型  CellType是一个枚举类型
                cell.setCellType(CellType.STRING);
                String cellValue = cell.getStringCellValue();
                //取到的列名存到一个数组中
                titles[i] = cellValue;
                //System.out.println(cellValue);
            }
            //4、处理数据行,把每一个数据行中的数据读出来，封装到一个对象里面去(每一个数据行就代表一个case对象)
            //共有多少行:拿到表单最后一行的行索引(0开始)
            int rowNum = sheet.getLastRowNum();
            //循环遍历每一行(从非标题行开始，到最后一行),处理每一行上所有的列
            for (int i = 1; i <= rowNum; i++) {
                //Case cs = new Case();//每一个数据行就代表一个case对象,每处理一行,就封装好一个cs对象
                //通过字节码，用反射的方式创建对象
                T obj = clazz.newInstance();
                Row dataRow = sheet.getRow(i);
                //增加判空：空对象或者空行,跳过,不处理
                if (dataRow == null || isEmptyRow(dataRow)) {
                    continue;
                }
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = dataRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();
                    //拿到这一列数据的标题
                    String cellName = titles[j];//CaseId(用例编号)
                    //拼接set方法
                    cellName = cellName.substring(0, cellName.indexOf("("));
                    String reflectMethodName = "set" + cellName;
                    //通过反射，设置cs对象的每个属性
                    //得到反射的方法对象
                    Method method = clazz.getDeclaredMethod(reflectMethodName, String.class);
                    //把cellValue的值封装到cs对象里面
                    method.invoke(obj, cellValue);//执行完循环，会将每一列的值都放到obj对象里面去
                }
                //每一行都封装成一个obj对象后保存起来，保存到一个list中
                casesList.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return casesList;
    }

    /**
     * 判断该行是否为空行：如果是一个空行，就不取它，不对空行进行下面的一系列操作，避免npe
     * 判断空行：每一列都为空，比较麻烦、可以取反：判断非空，只要有一列不为空，该行就是非空行
     *
     * @param row
     * @return
     */
    public static boolean isEmptyRow(Row row) {

        int cellNum = row.getLastCellNum();
        for (int i = 0; i < cellNum; i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            String cellValue = cell.getStringCellValue();
            if (cellValue != null && cellValue.trim().length() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 加载：
     * 用例编号与行索引的映射
     * 列名与列索引的映射
     */
    public static void loadRownumAndCellnumMapping() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(EXCEL_PATH));
            //1.通过工厂方法创建workbook对象（打开一个Excel就是一个工作簿）
            Workbook workbook = WorkbookFactory.create(inputStream);
            //2.获取表单对象sheet
            Sheet sheet = workbook.getSheet("用例");//要回写的数据一定是写在"用例"表单中，故写死就可以
            //共有多少行:拿到表单最后一行的行索引(0开始)
            int rowNum = sheet.getLastRowNum();
            //3.从第二行(行索引1)开始循环遍历每一行【处理用例编号与行索引的映射】
            for (int i = 1; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                //每一行都去处理第一列
                Cell firstCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                firstCell.setCellType(CellType.STRING);
                String caseId = firstCell.getStringCellValue();
                caseIdRownumMapping.put(caseId, i);
            }
            //4.处理列名与列索引的映射
            Row titleRow = sheet.getRow(0);
            //标题行有多少不为空的列(从1开始,比如共2列，cellNum为2)
            int cellNum = titleRow.getLastCellNum();
            //循环处理标题行的每一列
            for (int j = 0; j < cellNum; j++) {
                Cell cell = titleRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String cellName = cell.getStringCellValue();
                cellName = cellName.substring(0, cellName.indexOf("("));
                cellNameCellnumMapping.put(cellName, j);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}