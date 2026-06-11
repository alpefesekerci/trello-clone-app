package com.alpefesekerci.trello_clone_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Uygulamanın web katmanı (MVC-Model-View-Controller) ayarlarını özelleştiren yapılandırma sınıfı.
 * Tarayıcıların güvenlik mekanizması olan CORS (Cross-Origin Resource Sharing) krizlerini
 * baştan çözmek ve frontend'in backend API'sine güvenle erişmesini sağlamak için oluşturulmuştur.
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Sadece "/api/" ile başlayan uç noktalar (endpoints) için bu kuralları aktif et.
        registry.addMapping("/api/**")

                // Güvenilen Frontend adresleri:
                // 3000: Standart React (CRA) veya Next.js portu
                // 5173: Modern Vite (React, Vue, Svelte vb.) geliştirme portu
                .allowedOrigins("http://localhost:3000", "http://localhost:5173")

                // İstemcinin yapabileceği HTTP eylemleri.
                // Not: "OPTIONS" metodu, tarayıcıların asıl istekten önce attığı "Pre-flight" (ön uçuş) güvenlik kontrolü için hayati öneme sahiptir.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                // İstemciden gelen tüm HTTP başlıklarına (Örn: Content-Type, Authorization) izin ver.
                .allowedHeaders("*")

                // Güvenlik: İleride eklenecek Login işlemlerinde JWT veya Session ID'nin
                // HttpOnly cookie'ler üzerinden güvenle taşınabilmesi için true olarak ayarlanması zorunludur.
                .allowCredentials(true);
    }
}
