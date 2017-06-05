import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by fengxuming on 2017/6/5.
 */
public class FtpUpload {


    public boolean upload(File file,String host,String username,String password,String path)
    {

        FTPClient ftpClient = new FTPClient();
        FileInputStream fis = null;

        try {
            ftpClient.connect(host);
            ftpClient.login(username, password);
            fis = new FileInputStream(file);
            //设置上传目录
            ftpClient.changeWorkingDirectory(path);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF8");
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(path, fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fis);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }


    }

}
