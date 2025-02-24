package org.songxueyu.cdgy.fruitsaleproject.Controller;

import lombok.extern.slf4j.Slf4j;
import org.songxueyu.cdgy.fruitsaleproject.DTO.FruitDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Fruit;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Response.MyResponse;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.FruitService;
import org.songxueyu.cdgy.fruitsaleproject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@RequestMapping("/fruit")
@Controller
@Slf4j
@CrossOrigin
public class FruitController {
    @Autowired
    private FruitService fruitService;
    @RequestMapping("/getallfruits")
    @ResponseBody
    public MyResponse getAllFruits(@RequestBody QueryInfo queryInfo, HttpServletRequest request) {
        List<FruitDTO> allFruits = fruitService.getAllFruits(queryInfo);
        String token = request.getHeader("Authorization");
        log.info("用户信息为："+JwtUtil.getUserRoleFromToken(token));
        if (JwtUtil.getUserRoleFromToken(token).equals("user")){
            Iterator<FruitDTO> iterator = allFruits.iterator();
            while (iterator.hasNext()) {
                FruitDTO fruitDTO = iterator.next();
                if (fruitDTO.getFruit_dto_status().equals("0")) {
                    iterator.remove();
                }
            }
        }
        if (!allFruits.isEmpty()){
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .info(String.valueOf(allFruits.size()))
                    .object(allFruits)
                    .build();
        }else return MyResponse.builder()
                .code("201")
                .msg("没有找到水果")
                .object(null).build();
    }
    @RequestMapping("/uploadfruitimg")
    @ResponseBody
    public MyResponse uploadFruitImg(@RequestParam("fruit_image") MultipartFile multipartFile, HttpServletResponse response) {
        String fruitImagePath = fruitService.uploadFruitImage(multipartFile);
        return MyResponse.builder()
                .code("200")
                .msg("上传图片成功")
                .info(fruitImagePath)
                .build();
    }
    @RequestMapping("/addfruit")
    @ResponseBody
    public MyResponse addFruit(@RequestBody Fruit fruit,HttpServletResponse response) {
        int i = fruitService.addFruit(fruit);
        if (i!=0) return MyResponse.builder()
                .code("200")
                .msg("添加水果成功")
                .build();
        else return MyResponse.builder()
                .msg("添加水果失败")
                .code("201").build();
    }
    @RequestMapping("/getfruitdetails")
    @ResponseBody
    public MyResponse getFruitDetails(@RequestParam String fruit_id){

        log.info("fruit_id is:"+fruit_id);
        FruitDTO fruit = fruitService.getFruitById(fruit_id);
        log.info("fruit is {}",fruit);
        if (fruit!=null) return MyResponse.builder()
                .code("200")
                .msg("查询成功")
                .object(fruit).build();
        else return MyResponse.builder()
                .code("201")
                .msg("查询失败")
                .build();
    }
    @RequestMapping("/getfruitbyordernum")
    @ResponseBody
    public MyResponse getFruitByOrderNum(@RequestBody QueryInfo queryInfo,HttpServletResponse httpServletResponse){
        List<FruitDTO> allFruits = fruitService.getAllFruitsByOrderNum();

        if (!allFruits.isEmpty()){
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .object(allFruits).build();
        }
        else
            return MyResponse.builder()
                .code("201")
                .msg("查询失败")
                .build();
    }
    @RequestMapping("/getfruitbystar")
    @ResponseBody
    public MyResponse getFruitByStar(@RequestBody QueryInfo queryInfo,HttpServletResponse httpServletResponse){
        List<FruitDTO> allFruitsByStar = fruitService.getAllFruitsByStar();
        if (!allFruitsByStar.isEmpty())
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .object(allFruitsByStar).build();
        else
            return MyResponse.builder()
                .code("201")
                .msg("查询失败").build();
    }

    @RequestMapping("/getfruitbytime")
    @ResponseBody
    public MyResponse getFruitByTime(@RequestBody QueryInfo queryInfo,HttpServletResponse httpServletResponse){
        List<FruitDTO> allFruitsByTime = fruitService.getAllFruitsByTime();
        if (!allFruitsByTime.isEmpty())
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .object(allFruitsByTime).build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("查询失败").build();
    }
    @RequestMapping("/goofffruit")
    @ResponseBody
    public  MyResponse goOffFruit(@RequestParam String fruit_id,@RequestParam String fruit_status){
        int i = fruitService.goOffFruit(fruit_id, fruit_status);
        if (i==1)
            return MyResponse.builder()
                    .code("200")
                    .msg("修改状态成功")
                    .build();
        else if (i==2)
            return MyResponse.builder()
                    .code("201")
                    .msg("该水果还有订单未完成,不能下架!").build();
        else return MyResponse.builder()
                .code("202")
                .msg("修改状态失败")
                .build();
    }
    @RequestMapping("/getallfruitnum")
    @ResponseBody
    public MyResponse getAllFruitNum(){
        try {
            Class<?> aClass = Class.forName("org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo");
            Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            QueryInfo o = (QueryInfo) declaredConstructor.newInstance();
            o.setQuerytext("");
            log.info("水果数量："+fruitService.getAllFruits(o).size());
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .info(String.valueOf(fruitService.getAllFruits(o).size())).build();
        }catch (Exception e){
            e.printStackTrace();
            return MyResponse.builder()
                    .code("200")
                    .msg("系统错误").build();
        }

    }

}
