package com.wbg.sums.web;

import com.github.pagehelper.PageHelper;
import com.wbg.sums.util.Result;
import com.wbg.sums.entity.LeaveRecord;
import com.wbg.sums.service.LeaveRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaveRecord")

public class LeaveRecordController {
    
    @Autowired
    private LeaveRecordService leaveRecordService;

    /**
     * 根据aId删除
     * 要求转入 aId
     * @param leaveRecord
     * @return
     */
    @GetMapping("/deleteByPrimaryKey")
    @RequiresRoles("admin")
    public Result deleteByPrimaryKey(LeaveRecord leaveRecord) {
        try {
            return leaveRecordService.deleteByPrimaryKey(leaveRecord.getlId()) > 0 ? new Result().successMessage("删除成功") : new Result("删除失败");
        } catch (Exception ex) {
            return new Result().error("出错,请重试！");
        }
    }

    /**
     * 添加对象LeaveRecord
     *
     * @param leaveRecord
     * @return
     */
    @PostMapping("/insert")
    @RequiresPermissions({"insert"})
    public Result insert(@RequestBody LeaveRecord leaveRecord) {
        try {
            return leaveRecordService.insert(leaveRecord) > 0 ? new Result().successMessage("添加成功！") : new Result("添加失败！");
        } catch (Exception ex) {
            return new Result().error("出错,请重试！");
        }

    }

    /**
     * 根据aid查找对象  最多只能返回一个对象
     *
     * @param leaveRecord
     * @return
     */
    @GetMapping("/selectByPrimaryKey")
    public Result selectByPrimaryKey(LeaveRecord leaveRecord) {
        try {
            LeaveRecord leaveRecord1 = leaveRecordService.selectByPrimaryKey(leaveRecord.getlId());
            if (leaveRecord1 == null) {
                return new Result().successMessage("无数据");
            } else {
                return new Result().success(leaveRecord1);
            }
        } catch (Exception ex) {
            return new Result().error("出错,请重试！");
        }
    }

    /**
     * 查询所有数据
     *
     * @return
     */
    @GetMapping("/selectAll")
    public Result selectAll(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<LeaveRecord> list = leaveRecordService.selectAll();
            if (list == null) {
                return new Result().successMessage("无数据");
            } else {
                return new Result().success(list,leaveRecordService.count());
            }
        } catch (Exception ex) {
            return new Result().error("出错,请重试！");
        }
    }

    /**
     * 根据a_id修改全部字段
     *
     * @param leaveRecord
     * @return
     */
    @PostMapping(value = "/updateByPrimaryKey" )
    @RequiresPermissions({"update"})
    public Result updateByPrimaryKey(@RequestBody LeaveRecord leaveRecord) {
        try {
            return leaveRecordService.updateByPrimaryKey(leaveRecord) > 0 ? new Result().successMessage("修改成功") : new Result("修改失败");
        } catch (Exception ex) {
            return new Result().error("出错,请重试！");
        }
    }

    /**
     * 多个条件查询数据
     *
     * @return
     */
    @GetMapping("/selects")
    public Result selects(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize,String lMName, String dName, String beforeDate, String afterDate) {
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<LeaveRecord> list = leaveRecordService.selects(lMName, dName, beforeDate, afterDate);
            if (list == null) {
                return new Result().successMessage("无数据");
            } else {
                return new Result().success(list,leaveRecordService.counts(lMName, dName, beforeDate, afterDate));
            }
        } catch (Exception ex) {
            return new Result().error("出错,请重试！");
        }
    }
}
