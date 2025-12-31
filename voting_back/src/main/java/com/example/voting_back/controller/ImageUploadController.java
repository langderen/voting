package com.example.voting_back.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@SaCheckLogin
@RequestMapping("/image")
@RestController
public class ImageUploadController {

    @Value("${upload.path}")
    private String uploadPath;
    @SaIgnore
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
<<<<<<< HEAD
            String fileName = file.getOriginalFilename();
            File filePath = new File(uploadPath + "/" + fileName);
            file.transferTo(filePath);
=======
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 指定保存路径
            String filePath = uploadPath + "/" + fileName;
            // 保存文件到本地
            file.transferTo(new File(filePath));
>>>>>>> fa984f56b7b38810be06736461290f51e4e4e0eb
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }
    /*大
    *获取图片
    *@param imageName 图片名
    *@param response 返回结
    *@throws IOException io异常
    */
    @GetMapping
    public void getImage(
            @RequestParam("imageName")String imagename,
            HttpServletResponse responce
    ) throws IOException {
<<<<<<< HEAD
=======
        File fileDir = new File(uploadPath);
>>>>>>> fa984f56b7b38810be06736461290f51e4e4e0eb
        File image = new File(uploadPath + "/"+imagename);
        if (image.exists()) {
            FileInputStream fis = new FileInputStream(image);
            byte[] bytes=new byte[fis.available()];
            if(fis.read(bytes)>0){
                OutputStream os = responce.getOutputStream();
                os.write(bytes);
                os.close();
            }
            fis.close();
        }
    }


}
