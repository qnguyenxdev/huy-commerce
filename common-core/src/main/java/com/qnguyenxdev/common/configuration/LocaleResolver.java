package com.qnguyenxdev.common.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    @Override
    public @NonNull Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader("Accept-Language");
        return StringUtils.hasLength(language) ?
                Locale.lookup(Locale.LanguageRange.parse(language),
                        List.of(
                                Locale.of("en"),
                                Locale.of("de"),
                                Locale.of("fr")
                        )) :
                Locale.getDefault();
    }


}
