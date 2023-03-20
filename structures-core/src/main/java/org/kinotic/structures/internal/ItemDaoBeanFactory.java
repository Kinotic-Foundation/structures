/*
 *
 * Copyright 2008-2021 Kinotic and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kinotic.structures.internal;

import org.kinotic.continuum.core.api.event.CRI;
import org.kinotic.structures.api.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 *
 * Created by Navid Mitchell on 2/12/21.
 */
public class ItemDaoBeanFactory extends AbstractFactoryBean<Object> {

    private final String serviceInterfaceClassName;
    private Class<?> serviceClass;

    @Autowired
    private ItemService itemService;


    public ItemDaoBeanFactory(String serviceInterfaceClassName) {
        this.serviceInterfaceClassName = serviceInterfaceClassName;
        setSingleton(true);
    }

    public ItemDaoBeanFactory(String serviceInterfaceClassName,
                                      CRI cri) {
        this.serviceInterfaceClassName = serviceInterfaceClassName;
        setSingleton(true);
    }

    @Override
    protected synchronized void destroyInstance(Object instance) throws Exception {
//        if(serviceHandle != null){
//            serviceHandle.release();
//        }
    }

    @Override
    public synchronized Class<?> getObjectType() {
        try {
            if(serviceClass == null){
                serviceClass = Class.forName(serviceInterfaceClassName);
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return serviceClass;
    }

    @Override
    protected synchronized Object createInstance() {
//        if(serviceHandle == null){
//            if(cri == null) {
//                serviceHandle = serviceRegistry.serviceProxy(getObjectType());
//            }else{
//                serviceHandle = serviceRegistry.serviceProxy(cri, getObjectType());
//            }
//        }
//        return serviceHandle.getService();
        return null;
    }
}
