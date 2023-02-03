package com.ayushsingh.bloggingapplication.util.ImageUtil;



import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("azure.myblob")
@Component
public class AzureBlobProperties {
    private String connectionstring;
    private String container;
}
