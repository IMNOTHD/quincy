package com.piggy.quincy.component;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author IMNOTHD
 * @date 2020/7/12 13:00
 */
@Component
public class MessageCheckerComponent {

    public static boolean hasMessage(List<JSONObject> jsonObjectList, String message) {
        for (JSONObject jsonObject : jsonObjectList) {
            if ("Plain".equals(jsonObject.getString("type"))) {
                String text = jsonObject.getString("text");

                // kmp
                char[] p = text.toCharArray();
                int pLen = p.length;
                int[] next = new int[pLen];
                int k = -1;
                int j = 0;
                next[0] = -1;
                while (j < pLen - 1) {
                    if (k == -1 || p[j] == p[k]) {
                        k++;
                        j++;
                        next[j] = k;
                    } else {
                        k = next[k];
                    }
                }
                int i = 0;
                j = 0;
                char[] src = text.toCharArray();
                char[] ptn = message.toCharArray();
                int srcLen = src.length;
                int ptnLen = ptn.length;
                while (i < srcLen && j < pLen) {
                    // 如果j = -1,或者当前字符匹配成功(src[i] = ptn[j]),都让i++,j++
                    if (j == -1 || src[i] == ptn[j]) {
                        i++;
                        j++;
                    } else {
                        // 如果j!=-1且当前字符匹配失败,则令i不变,j=next[j],即让pattern模式串右移j-next[j]个单位
                        j = next[j];
                    }
                }
                if (j == ptnLen) {
                    return true;
                }
            }
        }
        return false;
    }

    public String fullString(List<JSONObject> jsonObjectList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (JSONObject jsonObject : jsonObjectList) {
            String type = jsonObject.getString("type");
            switch (type) {
                case "Source":
                    break;
                case "Quote":
                    break;
                case "At":
                    break;
                case "AtAll":
                    break;
                case "Face":
                    break;
                case "Plain":
                    break;
                case "Image":
                    break;
                case "FlashImage":
                    break;
                case "Xml":
                    break;
                case "Json":
                    break;
                case "App":
                    break;
                case "Poke":
                    break;
                default:
                    break;
            }
        }
        return "";
    }
}
