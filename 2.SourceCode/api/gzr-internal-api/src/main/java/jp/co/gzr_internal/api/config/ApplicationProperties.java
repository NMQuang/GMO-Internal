/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to management User Api.
 * <p>
 * Properties are configured in the application.yml file. See {@link io.github.jhipster.config.JHipsterProperties} for a
 * good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    /** The folder S3 user root. */
    private String folderS3UserRoot;

    /** The folder ckeditor image. */
    private String folderCkeditorImage;

    /** The server image url. */
    private String serverImageUrl;

    /**
     * Gets the folder S 3 user root.
     *
     * @return the folderS3UserRoot
     */
    public String getFolderS3UserRoot() {
        return folderS3UserRoot;
    }

    /**
     * Sets the folder S 3 user root.
     *
     * @param folderS3UserRoot the folderS3UserRoot to set
     */
    public void setFolderS3UserRoot(String folderS3UserRoot) {
        this.folderS3UserRoot = folderS3UserRoot;
    }

    /**
     * Gets the folder ckeditor image.
     *
     * @return the folder ckeditor image
     */
    public String getFolderCkeditorImage() {
        return folderCkeditorImage;
    }

    /**
     * Sets the folder ckeditor image.
     *
     * @param folderCkeditorImage the new folder ckeditor image
     */
    public void setFolderCkeditorImage(String folderCkeditorImage) {
        this.folderCkeditorImage = folderCkeditorImage;
    }

    /**
     * Gets the server image url.
     *
     * @return the server image url
     */
    public String getServerImageUrl() {
        return serverImageUrl;
    }

    /**
     * Sets the server image url.
     *
     * @param serverImageUrl the new server image url
     */
    public void setServerImageUrl(String serverImageUrl) {
        this.serverImageUrl = serverImageUrl;
    }

}
