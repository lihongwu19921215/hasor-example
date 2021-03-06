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
package net.example.hasor.core;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.hasor.core.ApiBinder;
import net.hasor.core.Environment;
import net.hasor.core.Module;
import net.hasor.db.DBModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 数据库链接 & DAO
 * @version : 2016年1月10日
 * @author 赵永春(zyc@hasor.net)
 */
public class DataSourceModule implements Module {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        //
        // .数据库配置
        Environment env = apiBinder.getEnvironment();
        String driverString = env.evalString("%jdbc.driver%");
        String urlString = env.evalString("%jdbc.url%");
        String userString = env.evalString("%jdbc.user%");
        String pwdString = env.evalString("%jdbc.password%");
        //
        // .数据源
        int poolMaxSize = 40;
        logger.info("C3p0 Pool Info maxSize is ‘{}’ driver is ‘{}’ jdbcUrl is‘{}’", poolMaxSize, driverString, urlString);
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverString);
        dataSource.setJdbcUrl(urlString);
        dataSource.setUser(userString);
        dataSource.setPassword(pwdString);
        dataSource.setMaxPoolSize(poolMaxSize);
        dataSource.setInitialPoolSize(3);
        dataSource.setIdleConnectionTestPeriod(18000);
        dataSource.setCheckoutTimeout(3000);
        dataSource.setTestConnectionOnCheckin(true);
        dataSource.setAcquireRetryDelay(1000);
        dataSource.setAcquireRetryAttempts(30);
        dataSource.setAcquireIncrement(1);
        dataSource.setMaxIdleTime(25000);
        //
        // .数据库框架
        apiBinder.installModule(new DBModule(dataSource));
    }
}