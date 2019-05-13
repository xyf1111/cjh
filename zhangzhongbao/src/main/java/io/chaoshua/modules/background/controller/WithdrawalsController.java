package io.chaoshua.modules.background.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.common.utils.*;
import io.chaoshua.modules.background.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.chaoshua.modules.background.entity.WithdrawalsEntity;
import io.chaoshua.modules.background.service.WithdrawalsService;

import javax.servlet.http.HttpServletResponse;


/**
 * 刷手提现
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-31 14:55:16
 */
@RestController
@RequestMapping("background/withdrawals")
public class WithdrawalsController {
    @Autowired
    private WithdrawalsService withdrawalsService;
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:withdrawals:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = withdrawalsService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:withdrawals:info")
    public R info(@PathVariable("id") Long id){
		WithdrawalsEntity withdrawals = withdrawalsService.selectById(id);
        return R.ok().put("withdrawals", withdrawals);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:withdrawals:save")
    public R save(@RequestBody WithdrawalsEntity withdrawals){
        withdrawalsService.insert(withdrawals);
        return R.ok();
    }

    /**
     * 修改提现状态
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:withdrawals:update")
    @LocalLock
    public R update(@RequestBody WithdrawalsEntity withdrawalsEntity){
        withdrawalsService.updateStatus(withdrawalsEntity);
        return R.ok();
    }

    /**
     * 导出提现信息
     *
     * @param response response
     * @throws IOException
     */
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletResponse response,@RequestParam String params) throws IOException {
       params = URLDecoder.decode(params,"utf-8");
       Map<String,Object> parseMap = JsonUtils.parseMap(params);
       Integer excelType = Integer.parseInt(parseMap.get("excelType").toString());
       String str = parseMap.get("ids").toString();
       str = str.replace("[","").replace("]","").trim();
       String [] ids = str.split(",");
        List<WithdrawalsEntity> list = new ArrayList<>();
        for (int i = 0;i<ids.length;i++){
            list.add(withdrawalsService.selectById(ids[i].trim()));
        }
        String fileName = null;
        List<String> headList = new ArrayList<>();
        List<String[]> list1 = new LinkedList<>();
        if (excelType == 1){
            //文件名称
            fileName = "" + System.currentTimeMillis() + "-招商银行提现列表";
            //头标题 "收款账户", "收款户名", "转账金额", "备注", "收款银行", "收款银行支行", "收款省/直辖市", "收款市县", "转出账号/卡", "转账模式"
            headList.add("收款账户");
            headList.add("收款户名");
            headList.add("转账金额");
            headList.add("备注");
            headList.add("收款银行");
            headList.add("收款银行支行");
            headList.add("收款省/直辖市");
            headList.add("收款市县");
            headList.add("转出账号/卡");
            headList.add("转账模式");
            for (WithdrawalsEntity withdrawalsEntity1 : list){
                list1.add(new String[]{withdrawalsEntity1.getCardNumber(),
                        withdrawalsEntity1.getHolder(),
                        withdrawalsEntity1.getAmount().toString(),
                        "",withdrawalsEntity1.getBank(),"","","","","实时"
                });
            }
        }else if (excelType == 2){
            //文件名称
            fileName = "" + System.currentTimeMillis() + "-浦发提现列表";
            //头标题 "银行", "卡号", "持卡人", "金额"
            headList.add("银行");
            headList.add("卡号");
            headList.add("持卡人");
            headList.add("金额");

            for (WithdrawalsEntity withdrawalsEntity1 : list){
                list1.add(new String[]{withdrawalsEntity1.getBank(),
                    withdrawalsEntity1.getCardNumber(),
                    withdrawalsEntity1.getHolder(),
                    withdrawalsEntity1.getAmount().toString()
                });

            }
        }
        Workbook workbook = ExcelUtils.createExcelFile(fileName, headList,list1);
        if (workbook != null) {
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName + ".xls", "UTF-8"))));
            workbook.write(response.getOutputStream());
        }
    }
}
