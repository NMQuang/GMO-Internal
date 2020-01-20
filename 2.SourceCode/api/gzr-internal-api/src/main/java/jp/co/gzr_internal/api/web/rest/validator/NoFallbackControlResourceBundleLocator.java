/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.web.rest.validator;

import java.util.Locale;
import java.util.ResourceBundle;

import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

/**
 * The Class NoFallbackControlResourceBundleLocator.
 * 
 * @author VuDA
 */
public class NoFallbackControlResourceBundleLocator implements ResourceBundleLocator {
    
    
    /* (non-Javadoc)
     * @see org.hibernate.validator.spi.resourceloading.ResourceBundleLocator#getResourceBundle(java.util.Locale)
     */
    @Override
    public ResourceBundle getResourceBundle(Locale locale) {
        
        ResourceBundle.Control control =
                ResourceBundle.Control.getNoFallbackControl(
                        ResourceBundle.Control.FORMAT_DEFAULT);
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", locale, control);
        return bundle;
    }
}
