package top.yhl.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import top.yhl.cloud.entity.User;

public interface UserService extends IService<User>, UserDetailsService {

}
