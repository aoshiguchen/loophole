package com.aoshiguchen.loophole;

import com.aoshiguchen.loophole.base.BaseLoopholeScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(String... args) {
        for(String name : appContext.getBeanDefinitionNames()){
            if(appContext.getBean(name) instanceof BaseLoopholeScanService){
                BaseLoopholeScanService loopholeScanService = (BaseLoopholeScanService)appContext.getBean(name);
                loopholeScanService.scan();
            }
        }
    }

}
