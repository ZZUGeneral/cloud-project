package top.yhl.cloud.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.yhl.cloud.oauth.entity.User;

public interface UserService extends IService<User>, UserDetailsService {

}
