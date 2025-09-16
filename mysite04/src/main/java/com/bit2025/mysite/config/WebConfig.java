package com.bit2025.mysite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bit2025.mysite.config.web.FileuploadConfig;
import com.bit2025.mysite.config.web.LocaleConfig;

@Configuration
@Import({LocaleConfig.class, FileuploadConfig.class})
public class WebConfig {

}
