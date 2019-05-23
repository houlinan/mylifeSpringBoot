package cn.hgxsp.mylife.Utils;

import com.fasterxml.jackson.databind.util.JSONPObject;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * DESC：根据前端请求获取参数转成json
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/5/23
 * Time : 12:49
 */
public class GetParametersUtile {

    public static void main(String[] args) {
        String path = "C:\\Users\\houli\\Desktop\\appPram.txt" ;
        System.out.println(readTxt(path).toString() );

    }

    public static JSONObject readTxt(String filePath) {

        JSONObject result = new JSONObject();

        try {
            File file = new File(filePath);
            if(file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
//                    System.out.println("当前行的文字为“："+ lineTxt);
                    String[] strs = lineTxt.split(":");
                    if(!StringUtils.isEmpty(strs[0])){
                        String cuttValue = strs[1];
                        if (StringUtils.isEmpty(cuttValue)){
                            result.put(strs[0],"");
                        }else{
                            result.put(strs[0].trim() , strs[1].trim());
                        }
                    }
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }

        return result ;
    }


}
