package org.songxueyu.cdgy.fruitsaleproject.Util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class ImageUtil {
    private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

    public final static String uploadImg(MultipartFile multipartFile, String file_path, String name){
        String file_name = "";
        int flag = 0;
        try{
            String timestamp = String.valueOf(System.currentTimeMillis());
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
            file_name = name+timestamp+"."+suffix;
            File file = new File(file_path+file_name);
            multipartFile.transferTo(file);
            flag = 1;
        }catch (Exception e){
            log.info("上传文件出错:{}",e.getMessage());
            e.printStackTrace();
            flag=0;
        }
        if (flag==1) return "/path/"+file_name;
        else return "";

    }
}
