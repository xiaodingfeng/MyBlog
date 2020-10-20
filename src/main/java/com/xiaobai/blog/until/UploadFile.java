package com.xiaobai.blog.until;

import com.xiaobai.blog.staticlass.Consts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Service
public class UploadFile {
    @Value("${file.uploadFolder}")
    private  String uploadFolder;
    @Value("${file.staticAccessPath}")
    private  String staticAccessPath;

    /**
     * 文件上传
     * @param file 文件
     * @param Path 上传路径
     * @return 访问路径
     */
    public  String[] uploadfile(MultipartFile[] file,String Path) throws IOException {

        if (file.length==0) {
            return null;
        }
        String[] data=new String[file.length];
        int i=0;
        for (MultipartFile multipartFile : file) {
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
            if (!(Consts.IMG_TYPE_DMG.equals(suffix.toUpperCase()) ||
                    Consts.IMG_TYPE_GIF.equals(suffix.toUpperCase()) ||
                    Consts.IMG_TYPE_JPEG.equals(suffix.toUpperCase()) ||
                    Consts.IMG_TYPE_JPG.equals(suffix.toUpperCase()) ||
                    Consts.IMG_TYPE_PNG.equals(suffix.toUpperCase()) ||
                    Consts.IMG_TYPE_SVG.equals(suffix.toUpperCase()))) {
            } else {
                UUID uuid = UUID.randomUUID();
                String fileName = uuid + "." + suffix;
                File dest = new File(uploadFolder + Path + fileName);
                if (!dest.exists()) {
                    dest.mkdirs();
                }
                try {
                    multipartFile.transferTo(dest);
                    boolean b = ImageUtils.compressPic(suffix,dest.toString(), dest.toString());
                    String staticAccessPath1 = staticAccessPath.replaceAll("\\*", "");
                    data[i] = (staticAccessPath1 + Path + fileName);
                    i++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;

    }
    public  boolean downloadfile(String file, HttpServletResponse response) throws UnsupportedEncodingException {

        String[] filenames=file.split("/");
        String filename=filenames[filenames.length-1];
        String staticAccessPath1=staticAccessPath.replaceAll("\\*","");
        file=file.replaceFirst(staticAccessPath1,"");
        File files = new File(uploadFolder + "/" + file);
        if(files.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download---" + filename);
            try {
                bis.close();
                fis.close();
                return true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }
}
