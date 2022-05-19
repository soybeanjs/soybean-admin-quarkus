package com.soybean.uaa.domain.converts;

import com.soybean.framework.db.page.BasePageConverts;
import com.soybean.uaa.domain.dto.UserUpdateDTO;
import com.soybean.uaa.domain.entity.baseinfo.User;
import com.soybean.uaa.domain.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @author wenxina
 */
@Slf4j
public class UserConverts {


    public static final User2VoConverts USER_2_VO_CONVERTS = new User2VoConverts();
    public static final UserDto2PoConverts USER_DTO_2_PO_CONVERTS = new UserDto2PoConverts();

    public static class User2VoConverts implements BasePageConverts<User, UserVO> {

        @Override
        public UserVO convert(User source) {
            if (source == null) {
                return null;
            }
            UserVO target = new UserVO();
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }

    public static class UserDto2PoConverts implements BasePageConverts<UserUpdateDTO, User> {

        @Override
        public User convert(UserUpdateDTO source, Long id) {
            if (source == null) {
                return null;
            }
            User target = new User();
            BeanUtils.copyProperties(source, target);
            target.setId(id);
            return target;
        }
    }
}
