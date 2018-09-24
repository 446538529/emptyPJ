package com.pyg.shop.controller;

        import com.pyg.common.util.FastDFSClient;
        import com.pyg.vo.Result;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RestController;
        import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @PostMapping("upload")
    public Result upload(MultipartFile file){
        try {
            String file_ext=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            FastDFSClient fastDFSClient=new FastDFSClient("classpath:fastdfs/tracker.conf");
            String url = fastDFSClient.uploadFile(file.getBytes(), file_ext);
            return Result.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("上传失败");
        }
    }
}
