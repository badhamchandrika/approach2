package com.aem.airmiles.web.core.models.onetrust.cacs;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Annotation for configuring OneTrust CAC (Context Aware Configurations). This annotation provides properties related
 * to OneTrust settings.
 *
 * @author pabpalac.
 */
@Configuration(name = "OTCAC", label="OneTrust Configurations", description="CA Configs for OneTrust.")
public @interface OneTrustCAC {

    /**
     * Specifies the SDK Stub Script.
     *
     * @return SDK Stub Script URL.
     */
    @Property(label="SDK Stub Script")
    String sdkStubScript() default EMPTY;

    /**
     * Specifies the OneTrust Consent id.
     *
     * @return OneTrust Consent id.
     */
    @Property(label="OneTrust Consent id")
    String consentId() default EMPTY;
}