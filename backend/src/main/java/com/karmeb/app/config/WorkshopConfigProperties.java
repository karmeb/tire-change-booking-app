package com.karmeb.app.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties(prefix = "spring.config.booking")
@Getter
@Setter
@ToString
public class WorkshopConfigProperties {
    private List<Workshop> workshops;

    public List<Workshop> getEnabledWorkshops() {
        return workshops.stream()
                .filter(Workshop::isEnabled)
                .collect(Collectors.toList());
    }

    public Workshop getEnabledWorkshopByName(String name) {
        return getEnabledWorkshops().stream()
                .filter(workshop -> workshop.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Getter
    @Setter
    @ToString
    public static class Workshop {
        private String name;
        private String address;
        private List<String> vehicleTypes;
        private boolean enabled;
        private String api;
        private String serviceClass;

    }
}
