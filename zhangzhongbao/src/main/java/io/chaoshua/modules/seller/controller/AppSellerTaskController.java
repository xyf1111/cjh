package io.chaoshua.modules.seller.controller;

import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.common.utils.*;
import io.chaoshua.modules.app.annotation.Login;
import io.chaoshua.modules.background.entity.task.TaskEntity;
import io.chaoshua.modules.background.service.task.TaskService;
import io.chaoshua.modules.oss.cloud.OSSFactory;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import static io.chaoshua.common.utils.ExcelUtils.getWorkBook;

/**
 * Created by dws on 2018/11/15 0015.
 */
@RestController
@RequestMapping("/app/seller/task")
public class AppSellerTaskController {
    @Autowired
    private TaskService taskService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    public R list(@RequestParam Map<String, Object> params,@ApiIgnore @RequestAttribute("userId") Long sellerId){
        params.put("sellerId",sellerId);
        PageUtils page = taskService.querySellerPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        TaskEntity task = taskService.selectById(id);
        return R.ok().put("task", task);
    }

    /**
     * 保存(发布任务)
     */
    @RequestMapping("/save")
    @Login
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", name = "token", value = "token", required = true, dataType = "String"),
    })
    @LocalLock
    public R save(@RequestBody TaskEntity task,@ApiIgnore @RequestAttribute("userId") Long sellerId){
        task.setSellerId(sellerId);
        return taskService.insertTask(task);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TaskEntity task){
        taskService.updateById(task);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        taskService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 商家撤销任务
     */
    @RequestMapping("/revoke/{id}")
    @LocalLock
    public R revoke(@PathVariable("id") Long id){
        taskService.deleteTaskById(id);
        return R.ok();
    }

    /**
     * 导出佣金信息
     *
     * @param response response
     * @throws IOException
     */
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response, @RequestParam String params) throws IOException {
        params = URLDecoder.decode(params,"utf-8");
        Map<String,Object> parseMap = JsonUtils.parseMap(params);
        String excelType = parseMap.get("excelType").toString();
        String str = parseMap.get("ids").toString();
        List<TaskEntity> list = new ArrayList<>();
        if (str != null && str.length() >0){
            str = str.replace("[","").replace("]","").trim();
            String [] ids = str.split(",");
            for (int i = 0;i<ids.length;i++){
                list.add(taskService.selectById(ids[i].trim()));
            }
        }
        BigDecimal sumPrice = new BigDecimal(0);
        Integer sumNumber = 0;
        BigDecimal sumPay = new BigDecimal(0);

        String fileName = null;
        List<String> headList = new ArrayList<>();
        List<String[]> list1 = new LinkedList<>();
        if (excelType.equals("1")) {
            //文件名称
            fileName = "" + System.currentTimeMillis() + "-任务佣金信息";
            //头标题 "宝贝链接", "店铺名", "任务类型", "关键词", "首单时间", "时间间隔", "类目", "商品单价", "数量", "任务佣金","浏览佣金","总佣金"
            headList.add("宝贝链接");
            headList.add("店铺名");
            headList.add("任务类型");
            headList.add("关键词");
            headList.add("首单时间");
            headList.add("时间间隔");
            headList.add("类目");
            headList.add("商品单价");
            headList.add("数量");
            headList.add("任务佣金");
            headList.add("浏览佣金");
            headList.add("总佣金");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (TaskEntity taskEntity : list) {
                Integer count = taskEntity.getAmount()-taskEntity.getRemove();
                sumNumber += count;//总数
                sumPrice = sumPrice.add(taskEntity.getPrice().multiply(new BigDecimal(count)));//总商品价格
                sumPay = sumPay.add((taskEntity.getSellerPay().divide(new BigDecimal(taskEntity.getAmount())).multiply(new BigDecimal(count))
                        .add(taskEntity.getViewPay().divide(new BigDecimal(taskEntity.getAmount())).multiply(new BigDecimal(count)))));//总佣金价格
                list1.add(new String[]{
                        taskEntity.getUrl(),
                        taskEntity.getShopName(),
                        taskEntity.getTaskStyleStr(),
                        taskEntity.getKeyword(),
                        sdf.format(taskEntity.getBeginTime()),
                        taskEntity.getInterval().toString(),
                        taskEntity.getShopCategory(),
                        taskEntity.getPrice().toString(),
                        taskEntity.getAmount().toString(),
                        taskEntity.getSellerPay().divide(new BigDecimal(taskEntity.getAmount())).multiply(new BigDecimal(count)).toString(),
                        taskEntity.getViewPay().divide(new BigDecimal(taskEntity.getAmount())).multiply(new BigDecimal(count)).toString(),
                        taskEntity.getSellerPay().divide(new BigDecimal(taskEntity.getAmount())).multiply(new BigDecimal(count))
                        .add(taskEntity.getViewPay().divide(new BigDecimal(taskEntity.getAmount())).multiply(new BigDecimal(count))).toString()
                });
            }
            list1.add(new String[]{
                    "总计", "", "", "", "", "", "", sumPrice.toString(), sumNumber.toString(), "", "", sumPay.toString()
            });
            Workbook workbook = ExcelUtils.createExcelFile(fileName, headList, list1);
            if (workbook != null) {
                response.setContentType("application/ms-excel;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename="
                        .concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
                workbook.write(response.getOutputStream());
            }
        }else if (excelType.equals("2")) {
            InputStream inputStream = ClassUtils.class.getClassLoader().getResourceAsStream("excel/表格批量导入任务模板.xls");
            DownloadUtils.download(inputStream, "表格批量导入任务模板.xls", response);
        }
    }


    /**
     * 上传文件
     *
     * @param file 文件
     * @return
     */
    @PostMapping("/upload/{sellerId}")
    public R upload(@RequestParam("file") MultipartFile file,@PathVariable("sellerId") Long sellerId) {
        try {
            if (ExcelUtils.checkFile(file).equals(2)){
                return R.error(500,"文件不存在!");
            }else if (ExcelUtils.checkFile(file).equals(3)){
                return R.error(500,"文件格式不对!");
            }
               HSSFWorkbook workbook = (HSSFWorkbook)getWorkBook(file);
               //读取图片
               List<HSSFPictureData> pictures = workbook.getAllPictures();
               if (pictures.size()>1){
                   return R.error(500,"只能上传一张产品主图!");
               }else if (pictures.size() < 1){
                   return R.error(500,"请上传一张产品主图!");
               }
                HSSFPictureData pictureData = pictures.get(0);
               byte[] picData = pictureData.getData();
               String url = OSSFactory.build().uploadSuffix(picData, ".jpg");
            // 获取excel里面的内容
            List<String[]> list = ExcelUtils.readExcel(file);
            return  taskService.insertBatchTask(list,sellerId,url);

        } catch (RRException rre) {
            return R.error(500, rre.getMsg());
        } catch (IOException ioe) {
            return R.error(500, "系统错误，请联系客服!");
        }
    }

}
