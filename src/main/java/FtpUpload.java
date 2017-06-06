import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * Created by fengxuming on 2017/6/5.
 */
public class FtpUpload {


    /**
     * Description: 向FTP服务器上传文件
     *
     * @param url      FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param path     FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     *
     */
    public  static boolean uploadFile(String url, int port, String username, String password, String path, String filename, InputStream input) {
        boolean success = false;
        FTPClient ftp = new FTPClient();




        try {
            String fileName_iso88591= new String(filename.getBytes("UTF8"),"iso-8859-1");

            int reply;
            ftp.connect(url, port);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);//登录
            System.out.println("已连接服务器");
            System.out.println("正在上传...");
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }


            String[] directories = path.split("/");
            for(String dir: directories)
            {
                if(ftp.changeWorkingDirectory(dir))
                {

                }
                else
                {
                    ftp.makeDirectory(dir);
                    System.out.println("新建"+dir+"文件夹");
                    ftp.changeWorkingDirectory(dir);
                }
            }
            //如果没有文件指定的文件夹就新建这些文件夹
            ftp.storeFile(fileName_iso88591,input);
            System.out.println(filename+"上传完毕");


            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    public boolean uploadByJson(File file) throws FileNotFoundException {

        JsonObject jsonObject = ReadJson.readJson("upload.json");
        System.out.println("正在读取upload.json文件");
        String rootDictionary = jsonObject.get("rootDictionary").getAsString();
        //解析json文件，获取需要监听的文件目录

        String filePath = file.getPath().replace(rootDictionary,"");
        filePath = filePath.replace(file.getName(),"");
        //本地文件目录替换成上传的文件目录

        FileInputStream input=new FileInputStream(file);


        JsonArray servers = jsonObject.getAsJsonArray("servers");
        //获取需要上传的ftp服务器的列表
        for(int i=0;i<servers.size();i++)
        {
            JsonObject server = (JsonObject)servers.get(i);
            String host = server.get("host").getAsString();
            System.out.println("正准备向"+host+"服务器上传文件");
            String path = server.get("path").getAsString()+filePath;
            int port = 21;
            String username = server.get("username").getAsString();
            String password = server.get("password").getAsString();


            if(!uploadFile(host,port,username,password,path,file.getName(),input))
            {
                return false;
            }

        }
        //遍历所有的服务器，分别向其上传文件
        return true;

    }

}
