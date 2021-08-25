package life.chen.community.controller;

import life.chen.community.dto.AcccessTokenDTO;
import life.chen.community.dto.GithubUser;
import life.chen.community.mapper.UserMapper;
import life.chen.community.model.User;
import life.chen.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author chenw
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request) {
        AcccessTokenDTO acccessTokenDTO = new AcccessTokenDTO();
        acccessTokenDTO.setClient_id(clientId);
        acccessTokenDTO.setClient_secret(clientSecret);
        acccessTokenDTO.setCode(code);
        acccessTokenDTO.setState(state);
        acccessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(acccessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if (githubUser != null) {
            // 登录成功，写cookie，session
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user", githubUser);
            // 注意这里用了thymeleaf
            return "redirect:/";
            //return "redirect::index";
        } else {
            // 登录失败重新登录
            return "redirect:/";
        }
    }
}
