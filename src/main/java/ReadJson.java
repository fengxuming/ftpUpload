import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;


/**
 * Created by fengxuming on 2017/6/5.
 */
public class ReadJson {
    public static JsonObject readJson(String dictionary)
    {
        JsonParser jsonParser = new JsonParser();
        try{
            JsonObject json = (JsonObject)jsonParser.parse(new FileReader(dictionary));

            return json;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
