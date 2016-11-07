/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.example.jfinal.services;
import net.example.jfinal.domain.UserDO;
import net.hasor.core.Inject;
import net.hasor.core.Singleton;
import net.hasor.db.Transactional;
import org.more.util.BeanUtils;

import java.sql.SQLException;
import java.util.List;

import static net.hasor.db.transaction.Propagation.REQUIRED;
/**
 * 使用 JFinal 的方式查询列表
 * @version : 2016年1月10日
 * @author 赵永春(zyc@hasor.net)
 */
@Singleton
public class UserManager {
    @Inject
    private EnvironmentConfig environmentConfig;
    //
    /** JFinal 方式查询列表 */
    public List<UserDO> queryList() {
        List<UserDO> userDOs = new UserDO().find("select * from TEST_USER_INFO");
        for (UserDO info : userDOs)
            info.recover();
        return userDOs;
    }
    //
    /** JFinal 方式，添加用户，使用 Hasor 提供的数据库事务 */
    @Transactional(propagation = REQUIRED)
    public void addUser(UserDO userDO) throws SQLException {
        UserDO dataUser = new UserDO();
        BeanUtils.copyProperties(dataUser, userDO);
        boolean save = dataUser.setupAll().save();
        if (!save) {
            throw new SQLException("保存失败。");
        }
    }
}