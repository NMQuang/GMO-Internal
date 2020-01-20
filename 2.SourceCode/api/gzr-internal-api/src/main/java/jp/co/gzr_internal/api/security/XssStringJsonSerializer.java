/*	
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *	
 * Author: VuDA	
 * Creation Date : Apr 11, 2019	
 */
package jp.co.gzr_internal.api.security;

import java.io.IOException;
import java.util.regex.Pattern;

import org.owasp.encoder.Encode;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * The Class XssStringJsonSerializer.
 */
public class XssStringJsonSerializer extends JsonSerializer<String> {

    @Override
    public Class<String> handledType() {
        return String.class;
    }

    /** The Constant POLICY_DEFINITION. */
    public static final PolicyFactory
    POLICY_DEFINITION = new HtmlPolicyBuilder()
        .allowStandardUrlProtocols()
        // Allow title="..." on any element.
        .allowAttributes("title").globally()
        // Allow href="..." on <a> elements.
        .allowAttributes("href").onElements("a")
        // Defeat link spammers.
        .requireRelNofollowOnLinks()
        // Allow lang= with an alphabetic value on any element.
        .allowAttributes("lang").matching(Pattern.compile("[a-zA-Z]{2,20}"))
            .globally()
        // The align attribute on <p> elements can have any value below.
        .allowAttributes("align")
            .matching(true, "center", "left", "right", "justify", "char")
            .onElements("p")
        // These elements are allowed.
        .allowElements(
            "a", "p", "div", "i", "b", "em", "blockquote", "tt", "strong",
            "br", "ul", "ol", "li")
        // Custom slashdot tags.
        // These could be rewritten in the sanitizer using an ElementPolicy.
        .allowElements("quote", "ecode")
        .toFactory();

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        // Escapse html before write response
        if (value != null) {
            jsonGenerator.writeString(Encode.forHtmlContent(value));
        }
    }

}
