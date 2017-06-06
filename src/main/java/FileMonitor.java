import com.google.gson.JsonObject;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * Created by fengxuming on 2017/6/6.
 */
public class FileMonitor {
    FileAlterationMonitor monitor = null;

    public FileMonitor(long interval) throws Exception {
        monitor = new FileAlterationMonitor(interval);
    }

    public void monitor(String path, FileAlterationListener listener) {
        FileAlterationObserver observer = new FileAlterationObserver(new File(path));
        monitor.addObserver(observer);
        observer.addListener(listener);
    }

    public void stop() throws Exception {
        monitor.stop();
    }

    public void start() throws Exception {
        monitor.start();
    }

    public static void main(String[] args) throws Exception {
        JsonObject jsonObject = ReadJson.readJson("upload.json");
        long checkTime = jsonObject.get("checkTime").getAsLong();
        String rootDictionary = jsonObject.get("rootDictionary").getAsString();

        FileMonitor m = new FileMonitor(checkTime);
        m.monitor(rootDictionary, new FileListener());
        m.start();
        System.out.println("程序启动正在监听 "+rootDictionary+"目录下的文件改动");
    }
}
