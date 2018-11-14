package com.example.ydhy.controller;

import com.example.ydhy.Re.Result;
import com.example.ydhy.Re.ResultGenerator;
import com.example.ydhy.dto.BorderRoomInfo;
import com.example.ydhy.entity.BorderRoom;
import com.example.ydhy.service.BorderRoomService;
import com.example.ydhy.util.TokenUtil;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/borderRoom")
public class BorderRoomController {
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private BorderRoomService borderRoomService;

    private String bordRoomExcel="C:\\Users\\YFZX-FZF-1777\\Desktop\\下载文件\\会议室示例文件.xls";

    private static Logger LOG = Logger.getLogger(String.valueOf(BorderRoomController.class));

    @PostMapping("/borderRoomInfo")
    public Result updateBorderRoomInfo(@ApiParam(value = "会议室信息")@RequestBody BorderRoomInfo borderRoomInfo,
                                 @ApiParam(value = "用户token",required = true)@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (jsonObject.optString("isSuper").equals("0")){
            return ResultGenerator.genFailResult("权限不足");
        }
        BorderRoom borderRoom=borderRoomService.getById(borderRoomInfo.getId());
        if (borderRoom==null){
            return ResultGenerator.genFailResult("部门不存在");
        }
        try {
            if (Objects.equals(borderRoom.getIsDelete(),"0"))
                borderRoomService.update(borderRoomInfo);
            else {
                return ResultGenerator.genFailResult("部门已被删除");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult("接口错误");
        }
        return ResultGenerator.genSuccessResult("更新成功");
    }

    @PostMapping("/addModel")
    public Result addModel(@ApiParam(value = "会议室信息")@RequestBody BorderRoom borderRoom,
                           @ApiParam(value = "用户token",required = true)@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (jsonObject.optString("isSuper").equals("0")){
            return ResultGenerator.genFailResult("权限不足");
        }
        List<BorderRoom> borderRooms=borderRoomService.getBorderRoomByName(borderRoom.getRoomName());
        if (borderRooms.size()!=0){
            return ResultGenerator.genFailResult("不可重复添加");
        }
        borderRoom.setCreateTime(new Date());
        borderRoom.setIsDelete("0");
        borderRoom.setStatus("0");
        try {
            borderRoomService.addBorderRoom(borderRoom);
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genFailResult("接口错误");
        }
        return ResultGenerator.genSuccessResult("更新成功");
    }

    @PostMapping(value = "/deleteModel")
    public Result deleteModel(
            @ApiParam(value = "id",example = "1") @RequestParam Integer id,
            @ApiParam(value = "用户token")@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (jsonObject.optString("isSuper").equals("0")){
            return ResultGenerator.genFailResult("权限不足");
        }
        BorderRoom borderRoom=borderRoomService.getById(id);
        if (borderRoom==null){
            return ResultGenerator.genFailResult("部门不存在");
        }
        try {
            if (borderRoom.getIsDelete().equals("0")){
                borderRoomService.delete(id);
            }
            else {
                return ResultGenerator.genFailResult("该会议室已删除");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("接口错误");
        }
        return ResultGenerator.genSuccessResult("删除成功");
    }

    @GetMapping(value = "/findModel/{id}")
    public Result<BorderRoom> findModel(
            @ApiParam(value = "id",example = "1")@PathVariable Integer id,
            @ApiParam(value = "用户token")@RequestParam String token){
        BorderRoom model= null;
        try {
            String str = tokenUtil.checkToken(token);
            if (str.equals("token无效")) {
                return ResultGenerator.genFailResult("token无效");
            }
            model = borderRoomService.getById(id);
        } catch (Exception e) {
            return ResultGenerator.genFailResult("接口错误");
        }
        return ResultGenerator.genSuccessResult(model);
    }

    @PostMapping("select")
    public Result<List<BorderRoom>> select(@ApiParam(value = "会议室名字",required = true)@RequestParam String borderName,
                                           @ApiParam(value = "页面size",example = "1")@RequestParam Integer pageSize,
                                           @ApiParam(value = "第几页",example = "1")@RequestParam Integer pageNo,
                                           @ApiParam(value = "用户token",required = true)@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        if (str.equals("token无效")) {
            return ResultGenerator.genFailResult("token无效");
        }
        List<BorderRoom> borderRooms=borderRoomService.getBorderRoomByName(borderName,pageNo,pageSize);
        return ResultGenerator.genSuccessResult(borderRooms);
    }
    @GetMapping("getBordExcelFile")
    public void getBordExcelFile(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String fileName="会议室示例文件";
        File file =new File(bordRoomExcel);
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
            LOG.info("========mady========");
        }
    }
    @PostMapping(value = "/importDeptList")
    public Result importBorderList(
            @RequestParam(value = "token", required = true) String token,
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
                    List errorLists=getBorderFromFile(workbook);
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

    private List getBorderFromFile(Workbook workbook) {
        List errorLists=new ArrayList();
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
            row = sheet.getRow(j);
            BorderRoom borderRoom = new BorderRoom();
            borderRoom.setRoomName(String.valueOf(row.getCell(0)));
            borderRoom.setPosition(String.valueOf(row.getCell(1)));
            borderRoom.setIntroduce(String.valueOf(row.getCell(2)));
            String str= String.valueOf(row.getCell(3));
            borderRoom.setDirectorId(Integer.valueOf(str.substring(0, str.indexOf('.'))));
            borderRoom.setCreateTime(new Date());
            borderRoom.setIsDelete("0");
            borderRoom.setStatus("0");
            List<BorderRoom> borderRooms=borderRoomService.getBorderRoomByName(String.valueOf(row.getCell(0)));
            if (borderRooms.size()==0){
                borderRoomService.addBorderRoom(borderRoom);
                continue;
            }
            errorLists.add(j);
        }
        return errorLists;
    }
}
