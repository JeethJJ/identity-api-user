/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.identity.api.user.recovery.commons.factory;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.recovery.services.password.PasswordRecoveryManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This factory bean is used to instantiate PasswordRecoveryManager type object inside the container.
 */
public class PasswordRecoveryManagerOSGIServiceFactory extends AbstractFactoryBean<List<PasswordRecoveryManager>> {

    private List<PasswordRecoveryManager> passwordRecoveryManager;

    @Override
    public Class<?> getObjectType() {

        return Object.class;
    }

    @Override
    protected List<PasswordRecoveryManager> createInstance() throws Exception {

        if (this.passwordRecoveryManager == null) {
            List<Object> passwordRecoveryManagerList = PrivilegedCarbonContext
                    .getThreadLocalCarbonContext().getOSGiServices(PasswordRecoveryManager.class, null);

            List<PasswordRecoveryManager> managers = new ArrayList<>();
            for (Object manager : passwordRecoveryManagerList) {
                if (manager instanceof PasswordRecoveryManager) {
                    managers.add((PasswordRecoveryManager) manager);
                }
            }
            if (!managers.isEmpty()) {
                this.passwordRecoveryManager = managers;
            } else {
                throw new Exception("Unable to retrieve PasswordRecoveryManager service.");
            }
        }
        return this.passwordRecoveryManager;
    }
}
