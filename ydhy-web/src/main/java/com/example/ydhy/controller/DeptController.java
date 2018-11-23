package com.example.ydhy.controller;

import com.example.ydhy.Re.Result;
import com.example.ydhy.Re.ResultGenerator;
import com.example.ydhy.dto.DeptInfo;
import com.example.ydhy.entity.Department;
import com.example.ydhy.entity.User;
import com.example.ydhy.service.DeptService;
import com.example.ydhy.service.UserService;
import com.example.ydhy.util.TokenUtil;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Workbook;

@CrossOrigin
@RestController
@RequestMapping("/department")
public class DeptController {
    @Autowired
    private DeptService deptService;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private UserService userService;

    private static Logger LOG = Logger.getLogger(String.valueOf(DeptController.class));

    private String deptExcelUrl="C:\\Users\\YFZX-FZF-1777\\Desktop\\下载文件\\部门示例文件.xls";

    @PostMapping("/deptInfo")
    public Result updateDeptInfo(@ApiParam(value = "部门信息")@RequestBody DeptInfo deptInfo,
                                 @ApiParam(value = "用户token",required = true)@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (jsonObject.optString("isSuper").equals("0")){
            return ResultGenerator.genFailResult("权限不足");
        }
        Department departments=deptService.getDeptById(deptInfo.getId());
        if (departments==null){
            return ResultGenerator.genFailResult("部门不存在");
        }
        try {
            deptService.updateDeptInfo(deptInfo);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult("接口错误");
        }
        return ResultGenerator.genSuccessResult("更新成功");
    }

    @PostMapping("getAll")
    public Result<List<Department>> getDepts(@ApiParam(value = "用户token")@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (jsonObject.optString("isSuper").equals("0")){
            return ResultGenerator.genFailResult("权限不足");
        }
        List<Department> departments=deptService.getAllDept();
        return ResultGenerator.genSuccessResult(departments);
    }

    @PostMapping("/addModel")
    public Result addModel(@ApiParam(value = "部门信息")@RequestBody Department department,
                           @ApiParam(value = "用户token",required = true)@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (jsonObject.optString("isSuper").equals("0")){
            return ResultGenerator.genFailResult("权限不足");
        }
        department.setCreateTime(new Date());
        try {
            if (!deptService.getDeptByName(department.getDeptName())){
                return ResultGenerator.genFailResult("部门已经存在");
            }
            deptService.addDepartment(department);
        }catch (Exception e){
            return ResultGenerator.genFailResult("接口错误");
        }
        return ResultGenerator.genSuccessResult("更新成功");
    }

    @PostMapping(value = "/deleteModel")
    public Result deleteModel(
            @ApiParam(value = "删除的部门ID",example = "1")@RequestParam("id") Integer id,
            @ApiParam(value = "用户token")@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (jsonObject.optString("isSuper").equals("0")){
            return ResultGenerator.genFailResult("权限不足");
        }
        try {
            Department department=deptService.getDeptById(id);
            if (department==null){
                return ResultGenerator.genFailResult("该部门不存在或者已删除。");
            }
            deptService.delete(id);
        } catch (Exception e) {
            return ResultGenerator.genFailResult("接口错误");
        }
        return ResultGenerator.genSuccessResult("删除成功");
    }

    @GetMapping(value = "/findModel/{id}")
    public Result<Department> findModel(
            @ApiParam(value = "寻找的部门ID",example = "1")@PathVariable("id") Integer id,
            @ApiParam(value = "用户token")@RequestParam String token){
        Department model= null;
        try {
            String str = tokenUtil.checkToken(token);
            if (str.equals("token无效")) {
                return ResultGenerator.genFailResult("token无效");
            }
            model = deptService.getDeptById(id);
        } catch (Exception e) {
            return ResultGenerator.genFailResult("接口错误");
        }
        return ResultGenerator.genSuccessResult(model);
    }

    @PostMapping("select")
    public Result<List<Department>> select(@ApiParam(value = "部门名字",required = true)@RequestParam String deptName,
                             @ApiParam(value = "页面size",example = "1")@RequestParam Integer pageSize,
                                           @ApiParam(value = "第几页",example = "1")@RequestParam Integer pageNo,
                                           @ApiParam(value = "用户token",required = true)@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        if (str.equals("token无效")) {
            return ResultGenerator.genFailResult("token无效");
        }
        List<Department> departments=deptService.getDeptByName(deptName,pageNo,pageSize);
        return ResultGenerator.genSuccessResult(departments);
    }
    @GetMapping("getDeptExcelFile")
    public void getDeptExcelFile(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        String fileName="部门示例文件";
        File file =new File(deptExcelUrl);
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.contains(userAgent, "MSIE")) {// IE浏览器
            fileName = URLEncoder.encode(fileName, "UTF8");
        } else if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
            fileName = new String(fileName.getBytes(),
                    "ISO8859-1");
        } else {
            fileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
        }
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" +fileName+".xls");
        try {
            byte[] bytes= FileUtils.readFileToByteArray(file);
            ServletOutputStream sout = response.getOutputStream();
            sout.write(bytes);
            sout.flush();
        } catch (IOException e) {
            LOG.info("============下载成功============");
        }
    }
    @RequestMapping(value = "/importDeptList")
    public Result importDeptList(
            @ApiParam(value = "用户token")@RequestParam String token,
            @ApiParam(value = "file detail") @RequestPart("file") MultipartFile file,
            HttpServletRequest request, HttpServletResponse response){
        try {
            String str=tokenUtil.checkToken(token);
            JSONObject jsonObject=JSONObject.fromObject(str);
            if (jsonObject.optString("isSuper").equals("0")){
                return ResultGenerator.genFailResult("权限不足");
            }
            if (file != null) {
                String fileName = file.getOriginalFilename();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream());
                String sufix = fileName.substring(fileName.lastIndexOf(".") + 1);
                Workbook workbook = null;
                if (sufix.equals("xlsx")) {
                    workbook = new XSSFWorkbook(bufferedInputStream);
                } else if (sufix.equals("xls")) {
                    workbook = new HSSFWorkbook(bufferedInputStream);
                }
                if (workbook != null) {
                    List errorLists=getDeptFromFile(workbook);
                    bufferedInputStream.close();
                    if (errorLists.size()>0)
                        return ResultGenerator.genFailResult(errorLists);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult("导入成功！");

    }

    private List getDeptFromFile(Workbook workbook) {
        List errorLists=new ArrayList();
        Sheet sheet = workbook.getSheetAt(0);
        DecimalFormat decimalFormat=new DecimalFormat("#");
        Row row;
        for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
            row = sheet.getRow(j);
            Department department = new Department();
            if (!deptService.getDeptByName(String.valueOf(row.getCell(0)))){
                errorLists.add(j);
                continue;
            }
            department.setDeptName(String.valueOf(row.getCell(0)));
            department.setPhone(decimalFormat.format(row.getCell(1).getNumericCellValue()));
            String str= String.valueOf(row.getCell(2));
            department.setDirectorId(Integer.valueOf(str.substring(0, str.indexOf('.'))));
            department.setIntrodecu(String.valueOf(row.getCell(3)));
            department.setCreateTime(new Date());
            department.setIsDelete("0");
            department.setStatus("0");
            deptService.addDepartment(department);

        }
        return errorLists;
    }


}
