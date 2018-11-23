package com.example.ydhy.controller;


import com.example.ydhy.Re.Result;
import com.example.ydhy.Re.ResultGenerator;
import com.example.ydhy.dao.UserMapper;
import com.example.ydhy.dto.UserInfo;
import com.example.ydhy.entity.BorderRoom;
import com.example.ydhy.entity.User;
import com.example.ydhy.service.UserService;
import com.example.ydhy.util.TokenUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;


@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtil tokenUtil;

    private static final String bordRoomExcel="C:\\Users\\YFZX-FZF-1777\\Desktop\\下载文件\\用户示例文件.xls";

    private static final String password=new SimpleHash("md5",
            "A123456", ByteSource.Util.bytes(""), 2).toHex();

    private static Logger LOG = Logger.getLogger(String.valueOf(BorderRoomController.class));

    @PostMapping("getAll")
    public Result<List<User>> getUsers(@ApiParam(value = "第几页",example = "1")@RequestParam Integer pageNo,
                                     @ApiParam(value = "用户token")@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (jsonObject.optString("isSuper").equals("0")){
            return ResultGenerator.genFailResult("权限不足");
        }
        List<User> users=userService.getUsers(5,pageNo);
        return ResultGenerator.genSuccessResult(users);
    }

    @PostMapping("getUserCount")
    public Integer getUserCount(@ApiParam(value = "用户token")@RequestParam String token){
        String str=tokenUtil.checkToken(token);
        JSONObject jsonObject=JSONObject.fromObject(str);
        if (jsonObject.optString("isSuper").equals("0")){
            return 0;
        }
        List<User> users=userService.getUsers(null,null);
        return users.size();
    }

    @PostMapping("select")
    public Result<List<User>> select(@ApiParam("name")@RequestParam String name,
                                     @ApiParam(value = "页面size",example = "1")@RequestParam Integer pageSize,
                                     @ApiParam(value = "第几页",example = "1")@RequestParam Integer pageNo,
                                     @ApiParam(value = "用户token")@RequestParam String token){
        String login_name=tokenUtil.checkToken(token);
        if (login_name.equals("token无效")){
            return ResultGenerator.genFailResult(login_name);
        }
        List<User> users=userService.getUsersByName(name,pageSize,pageNo);
        return ResultGenerator.genSuccessResult(users);
    }

    @PostMapping("/userInfo")
    public Result updateUserInfo(@ApiParam(value = "用户信息")@RequestBody UserInfo userInfo,
                                 @ApiParam(value = "用户token",required = true)@RequestParam String token){
        String login_name=tokenUtil.checkToken(token);
        if (login_name.equals("token无效")){
            return ResultGenerator.genFailResult(login_name);
        }
        JSONObject jsonObject=JSONObject.fromObject(login_name);
        if (!(jsonObject.optString("isSuper").equals("1")||
                Objects.equals(jsonObject.optInt("id"),userInfo.getId()))){
            return ResultGenerator.genFailResult("权限不足。需要本人或者管理员操作。");
        }
        try {
            User user=userService.getByUserId(userInfo.getId());
            if (user==null){
                return ResultGenerator.genFailResult("该账号已被删除，无法更新。请联系管理员。");
            }
            userService.updateUserInfo(userInfo);
        }catch (Exception e){
            LOG.info("更新用户操作失败！"+e.getMessage());
            return ResultGenerator.genFailResult("更新出错");
        }
        return  ResultGenerator.genSuccessResult("更新成功");
    }
    @PostMapping("/deleteUser")
    public Result delete(@ApiParam(value = "用户Id",example = "1")@RequestParam Integer userId,
                         @ApiParam(value = "用户token",required = true)@RequestParam String token){
        String login_name=tokenUtil.checkToken(token);
        if (login_name.equals("token无效")){
            return ResultGenerator.genFailResult(login_name);
        }
        JSONObject jsonObject=JSONObject.fromObject(login_name);
        if (jsonObject.optString("isSuper").equals("0")){
            return ResultGenerator.genFailResult("权限不足");
        }
        if (userService.deleteByUserId(userId)==1){
            return ResultGenerator.genSuccessResult("成功删除用户");
        }
        return ResultGenerator.genSuccessResult("用户不存在");
    }

    @GetMapping("getUserExcelFile")
    public void getUserExcelFile(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String fileName="用户示例文件";
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
    @PostMapping(value = "/importUserList")
    public Result importUsersList(
            @RequestParam(value = "token", required = true) String token,
            @ApiParam(value = "file detail") @RequestPart("file") MultipartFile file,
            HttpServletRequest request, HttpServletResponse response){
        try {
            String login_name=tokenUtil.checkToken(token);
            if (login_name.equals("token无效")){
                return ResultGenerator.genFailResult(login_name);
            }
            JSONObject jsonObject=JSONObject.fromObject(login_name);
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
                    List errorLists=getUsersFromFile(workbook);
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

    @PostMapping("/getMyInfo")
    public Result getMyInfo(@ApiParam(value = "用户token",required = true)@RequestParam String token){
        String login_name=tokenUtil.checkToken(token);
        if (login_name.equals("token无效")){
            return ResultGenerator.genFailResult(login_name);
        }
        JSONObject jsonObject=JSONObject.fromObject(login_name);
        return ResultGenerator.genSuccessResult(jsonObject);
    }

    private List getUsersFromFile(Workbook workbook) {
        List errorLists=new ArrayList();
        Sheet sheet = workbook.getSheetAt(0);
        DecimalFormat decimalFormat=new DecimalFormat("#");
        Row row;
        for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
            row = sheet.getRow(j);
            User user=new User();
            String login_name= String.valueOf(row.getCell(0));
            User checkuser=userService.getByUsername((login_name.substring(0, login_name.indexOf('.'))));
            if (checkuser!=null){
                errorLists.add(j);
                continue;
            }
            user.setLoginName((login_name.substring(0, login_name.indexOf('.'))));
            user.setRealName(String.valueOf(row.getCell(1)));
            user.setPhone(decimalFormat.format(row.getCell(2).getNumericCellValue()));
            user.setEmail(String.valueOf(row.getCell(3)));
            String deptId= String.valueOf(row.getCell(4));
            user.setDeptId(Integer.valueOf(deptId.substring(0, deptId.indexOf('.'))));
            user.setPassword(password);
            user.setPosition(String.valueOf(row.getCell(5)));
            user.setIsSuper("0");
            user.setCreateTime(new Date());
            user.setIsDelete("0");
            user.setStatus("0");
            userService.save(user);
        }
        return errorLists;
    }
}