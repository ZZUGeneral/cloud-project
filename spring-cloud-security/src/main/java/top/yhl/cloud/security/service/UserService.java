package top.yhl.cloud.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.yhl.cloud.security.entity.User;

public interface UserService extends IService<User>, UserDetailsService {

}
