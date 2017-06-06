import com.google.gson.JsonObject;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by fengxuming on 2017/6/6.
 */
public class FileListener implements FileAlterationListener {
    FileMonitor monitor = null;
    public void onStart(FileAlterationObserver fileAlterationObserver) {

    }

    public void onDirectoryCreate(File file) {

    }

    public void onDirectoryChange(File file) {

    }

    public void onDirectoryDelete(File file) {

    }

    public void onFileCreate(File file) {
        System.out.println("新增了"+file.getPath()+"文件");
        JsonObject jsonObject = ReadJson.readJson("upload.json");
        String ignoreDictionary = jsonObject.get("ignoreDictionary").getAsString();
        if(file.getPath().contains(ignoreDictionary))
        {
            System.out.println("已忽略"+file.getPath()+"文件");
        }
        else
        {
            FtpUpload  upload = new FtpUpload();
            try {
                upload.uploadByJson(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    public void onFileChange(File file) {

    }

    public void onFileDelete(File file) {

    }

    public void onStop(FileAlterationObserver fileAlterationObserver) {

    }
}
