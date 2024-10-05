package com.karmeb.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties(prefix = "workshops")
@Getter
@Setter
public class WorkshopConfig {
    private List<Workshop> workshops;

    public List<Workshop> getEnabledWorkshops() {
        return workshops.stream()
                .filter(Workshop::isEnabled)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class Workshop {
        private String name;
        private String address;
        private List<String> vehicleTypes;
        private boolean enabled;
        private String api;
        private String serviceClass;

    }
}
