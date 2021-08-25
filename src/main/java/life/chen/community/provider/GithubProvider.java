package life.chen.community.provider;

import com.alibaba.fastjson.JSON;
import life.chen.community.dto.AcccessTokenDTO;
import life.chen.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AcccessTokenDTO acccessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(acccessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            // 获取到的字符串格式如下：
            // access_token=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&scope=user&token_type=bearer
            // 需要提取出token
            String[] split = string.split("&");
            String tokenStr = split[0];
            String token = tokenStr.split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public GithubUser getGithubUser(String accessToken) {
//        OkHttpClient client = new OkHttpClient();
////        Request request = new Request.Builder()
////                .url("https://api.github.com/user?access_token=" + accessToken)
////                .build();
//        Request request = new Request.Builder()
//                .url("https://api.github.com/user")
//                .header("Authorization","token "+accessToken)
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            String string = response.body().toString();
//            System.out.println(string);
//            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
//            return githubUser;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public GithubUser getGithubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder() .
                url("https://api.github.com/user?access_token=" + accessToken) .
                header("Authorization","token "+accessToken) .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser; }
        catch (Exception e) {
//            log.error("getUser error,{}", accessToken, e);
         }
        return null;
    }
}
