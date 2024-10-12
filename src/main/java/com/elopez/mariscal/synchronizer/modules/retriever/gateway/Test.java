package com.elopez.mariscal.synchronizer.modules.retriever.gateway;

import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.elopez.mariscal.synchronizer.modules.retriever.gateway") 
public class Test {

    public static void main(String[] args) {
        // Inicializa el contexto de Spring
        ApplicationContext context = SpringApplication.run(Test.class, args);
        
        // Obtiene el bean de RetrieveInvoicesJpa desde el contexto de Spring
        RetrieveInvoicesJpa retrieveInvoicesJpa = context.getBean(RetrieveInvoicesJpa.class);
        
        try {
            List<Map<String, Object>> result = retrieveInvoicesJpa.retrieveInvoices();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
