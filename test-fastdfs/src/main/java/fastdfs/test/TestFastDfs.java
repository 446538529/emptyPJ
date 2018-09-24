package fastdfs.test;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

public class TestFastDfs {
    @Test
    public void test() throws IOException, MyException {
        String path = ClassLoader.getSystemResource("fastdfs/tracker.conf").getPath();
        ClientGlobal.init(path);
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer=null;
        StorageClient storageClient=new StorageClient(trackerServer,storageServer);
        String[] upload_file = storageClient.upload_file("D:\\Pictures\\美女\\man03.jpg", "jpg", null);
        if(upload_file!=null&&upload_file.length>0){
            for (String s : upload_file) {
                System.out.println(s);
                /*
                    group1
                    M00/00/00/wKgMqFucxLSARuQsAAF148DV7S0447.jpg
                 */
            }
        }

    }

}
