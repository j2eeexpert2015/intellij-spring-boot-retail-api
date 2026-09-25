package org.eclipsefeaturesdemo.retail.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "retail")
public record RetailProperties(
        @Min(value = 1, message = "Low stock threshold must be at least 1")
        int lowStockThreshold,

        @NotBlank(message = "Default currency must not be blank")
        String defaultCurrency,

        @Email(message = "Support email must be valid")
        String supportEmail
) {
}
