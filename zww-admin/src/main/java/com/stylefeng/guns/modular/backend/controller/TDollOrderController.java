package com.stylefeng.guns.modular.backend.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.dao.TDollOrderItemMapper;
import com.stylefeng.guns.common.persistence.model.TDollOrder;
import com.stylefeng.guns.common.persistence.model.TDollOrderItem;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.backend.service.ITDollOrderService;
import com.stylefeng.guns.modular.backend.warpper.TDollOrderItemWarpper;
import com.stylefeng.guns.modular.backend.warpper.TDollOrderWarpper;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 待发货列表控制器
 *
 * @author fengshuonan
 * @Date 2018-01-30 17:05:25
 */
@Controller
@RequestMapping("/tDollOrder")
public class TDollOrderController extends BaseController {

    private String PREFIX = "/backend/tDollOrder/";

    @Autowired
    private ITDollOrderService tDollOrderService;

    @Autowired
    private TDollOrderItemMapper dollOrderItemMapper;

    /**
     * 跳转到申请发货列表首页
     */
    @RequestMapping("/apply")
    public String applyPage() {
        return PREFIX + "tDollOrder_apply.html";
    }

    /**
     * 跳转到待发货列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tDollOrder.html";
    }

    /**
     * 跳转到已发货列表首页
     */
    @RequestMapping("/outGoogs")
    public String outGoods() {
        return PREFIX + "tDollOrderOut.html";
    }


    /**
     *
     * 跳转订单信息
     */
    @RequestMapping("/goodsDetail")
    public String goodsDetail(String orderNum,Model model) {
        model.addAttribute("item",orderNum);
        return PREFIX + "goodsDetail.html";
    }



    /**
     *
     * 订单信息列表
     */
    @RequestMapping(value = "/goodsDetailLists")
    @ResponseBody
    public Object goodsDetailList(String orderNum) {

        Page<TDollOrderItem> page = new PageFactory<TDollOrderItem>().defaultPage();
        List<Map<String, Object>> result = dollOrderItemMapper.selectLists(page, orderNum);
        page.setRecords((List<TDollOrderItem>)new TDollOrderItemWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 跳转到添加待发货列表
     */
    @RequestMapping("/tDollOrder_add")
    public String tDollOrderAdd() {
        return PREFIX + "tDollOrder_add.html";
    }

    /**
     * 跳转到修改待发货列表
     */
     
    @RequestMapping(value = "/tDollOrder_update/{ids}")
    public String tDollOrderUpdate(@PathVariable String ids, Model model) {
        model.addAttribute("ids",ids);
        LogObjectHolder.me().set(ids);
        return PREFIX + "tDollOrder_edit.html";
    }

    /**
     * 获取申请发货列表列表
     */

    @RequestMapping(value = "/applylist")
    @ResponseBody
    public Object applylist(Integer id,String addrName,String phone,String dollName) {
        Page<TDollOrder> page = new PageFactory<TDollOrder>().defaultPage();
        List<Map<String, Object>> result = tDollOrderService.selectTDollOrderApply(page,id,addrName,phone,dollName);
        page.setRecords((List<TDollOrder>)new TDollOrderWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 获取待发货列表列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer id,String addrName,String phone,String dollName) {
        Page<TDollOrder> page = new PageFactory<TDollOrder>().defaultPage();
        List<Map<String, Object>> result = tDollOrderService.selectTDollOrder(page,id,addrName,phone,dollName);
        page.setRecords((List<TDollOrder>)new TDollOrderWarpper(result).warp());
        return super.packForBT(page);
    }


    /**
     * 获取已发货列表列表
     */

    @RequestMapping(value = "/outList")
    @ResponseBody
    public Object outList(Integer id,String addrName,String phone,String dollName) {
        Page<TDollOrder> page = new PageFactory<TDollOrder>().defaultPage();
        List<Map<String, Object>> result = tDollOrderService.selectTDollOrderOut(page,id,addrName,phone,dollName);
        page.setRecords((List<TDollOrder>)new TDollOrderWarpper(result).warp());
        return super.packForBT(page);
    }


    /**
     * 新增待发货列表
     */
     
//    @RequestMapping(value = "/add")
//    @ResponseBody
//    public Object add(TDollOrder tDollOrder) {
//        tDollOrderService.insert(tDollOrder);
//        return super.SUCCESS_TIP;
//    }


    /**
     * 扣留违规娃娃
     */

    @RequestMapping(value = "/callInDoll")
    @ResponseBody
    public Object callInDoll(@RequestParam Long tDollOrderId) {
        TDollOrder tDollOrder = new TDollOrder();
        tDollOrder.setId(tDollOrderId);
        tDollOrder.setModifiedBy(ShiroKit.getUser().getId());
        tDollOrder.setModifiedDate(new Date());
        tDollOrder.setStatus("违规收回");
        tDollOrderService.updateById(tDollOrder);
        return super.SUCCESS_TIP;
    }

    /**
     * 寄存娃娃直接兑换币
     */

    @RequestMapping(value = "/dollBackCoins")
    @ResponseBody
    public Object dollBackCoins(@RequestParam Integer tDollOrderId,@RequestParam String memberId) {
        tDollOrderService.dollBackCoins(tDollOrderId,memberId);
        return super.SUCCESS_TIP;
    }


    /**
     * 跳转到添加娃娃·详情
     */
    @RequestMapping("/toAddDoll/{memberId}")
    public String toAddDoll(@PathVariable String memberId, Model model) {
        model.addAttribute("memberId",memberId);
        return PREFIX + "tDollOrder_add_doll.html";
    }

    /**
     * 添加娃娃
     */
    @RequestMapping(value = "/backDoll")
    @ResponseBody
    public Object backDoll(TDollOrder tDollOrder) {
        tDollOrderService.backDoll(tDollOrder);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除待发货列表
     */
     
//    @RequestMapping(value = "/delete")
//    @ResponseBody
//    public Object delete(@RequestParam Integer tDollOrderId) {
//        tDollOrderService.deleteById(tDollOrderId);
//        return SUCCESS_TIP;
//    }

    /**
     * 修改待发货列表
     */
     
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TDollOrder tDollOrder) {
        String idStr = tDollOrder.getOrderNumber();
        List<Long> ids = new ArrayList<Long>();
        for (int i=0;i<idStr.split(",").length;i++){
            ids.add(Long.valueOf(idStr.split(",")[i]));
        }
      //  tDollOrder.setDeliverDate(new Date());
      //  tDollOrder.setModifiedDate(new Date());
      //  tDollOrder.setStatus("已发货");
        tDollOrderService.updateTDollOrderById(ids,tDollOrder.getDeliverMethod(),tDollOrder.getDeliverNumber(),tDollOrder.getDeliverAmount(),tDollOrder.getComment());
        return super.SUCCESS_TIP;
    }

    /**
     * 揽件
     */

    @RequestMapping(value = "/order_apply")
    @ResponseBody
    public Object order_apply(String tDollOrderIds) {
        List<Long> ids = new ArrayList<Long>();
        for (int i=0;i<tDollOrderIds.split(",").length;i++){
            ids.add(Long.valueOf(tDollOrderIds.split(",")[i]));
        }
        tDollOrderService.updateTDollOrderApplyById(ids);
        return super.SUCCESS_TIP;
    }

    /**
     * 待发货列表详情
     */
     
    @RequestMapping(value = "/detail/{tDollOrderId}")
    @ResponseBody
    public Object detail(@PathVariable("tDollOrderId") Integer tDollOrderId) {
        return tDollOrderService.selectById(tDollOrderId);
    }


    /***
     * 获取excel数据
     * @return 返回文件名称及excel文件的URL
     * @throws IOException
     */
    @RequestMapping(value = "/tDollOrderExecl",method = RequestMethod.GET)
    public void profitHistory(HttpServletResponse response, Integer id,String addrName,String phone,String dollName) throws IOException {
        List<TDollOrder> result = tDollOrderService.selectTDollOrderExecl(id,addrName,phone,dollName);

        String fileName = "未发货数据统计.xls";

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), TDollOrder.class, result);
        workbook.write(response.getOutputStream());
    }
}
