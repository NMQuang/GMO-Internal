/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class MailConfiguration.
 */
@Configuration
@ConfigurationProperties("cf.setting")
public class SettingConfiguration {

    /** The path image. */
    private String pathImage;

    /**
     * Gets the path image.
     *
     * @return the path image
     */
    public String getPathImage() {
        return pathImage;
    }

    /**
     * Sets the path image.
     *
     * @param pathImage the new path image
     */
    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

}
