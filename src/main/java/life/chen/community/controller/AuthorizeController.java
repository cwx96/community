package life.chen.community.controller;

import life.chen.community.dto.AcccessTokenDTO;
import life.chen.community.dto.GithubUser;
import life.chen.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Value("{github.redirect.uri}")
    private String redirectUri;;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state) {
        AcccessTokenDTO acccessTokenDTO = new AcccessTokenDTO();
        acccessTokenDTO.setClient_id(clientId);
        acccessTokenDTO.setClient_secret(clientSecret);
        acccessTokenDTO.setCode(code);
        acccessTokenDTO.setState(state);
        acccessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(acccessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
