/*
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Oct 01, 2019
 */
package jp.co.gzr_internal.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class MailConfiguration.
 */
@Configuration
@ConfigurationProperties("run.mail")
public class MailConfiguration {
    
    private String bm;
    private String qa;
    
    public String getBm() {
        return bm;
    }
    public void setBm(String bm) {
        this.bm = bm;
    }
    public String getQa() {
        return qa;
    }
    public void setQa(String qa) {
        this.qa = qa;
    }
}
