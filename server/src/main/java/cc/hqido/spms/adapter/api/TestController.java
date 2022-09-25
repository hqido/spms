package cc.hqido.spms.adapter.api;

import cc.hqido.spms.adapter.home.Root;
import cc.hqido.spms.core.ResultBuilderConsts;
import cc.hqido.spms.core.model.User;
import cc.hqido.spms.infra.result.DataResult;
import cc.hqido.spms.infra.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yueli
 * @since 2022/9/18
 */
@RestController
@RequestMapping(TestController.ROOT)
public class TestController {

    protected static final String ROOT = Root.API_ROOT + "/test";

    @GetMapping
    public Result test() {
        return ResultBuilderConsts.SUCCESS.r();
    }

    @PostMapping("/notLogin")
    public Result notLogin() {
        return ResultBuilderConsts.NOT_LOGIN.r();
    }

    @PostMapping("/invalidLogin")
    public Result invalidLogin() {
        return ResultBuilderConsts.INVALID_LOGIN.r();
    }

    @GetMapping("/getCurrentUser")
    public DataResult<User> user() {
        User user = new User();
        user.setId(1L);
        user.setNickName("1231");
        user.setUsername("123123");
        return ResultBuilderConsts.SUCCESS.r(user);
    }

}
