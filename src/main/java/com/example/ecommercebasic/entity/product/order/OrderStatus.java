package com.example.ecommercebasic.entity.product.order;

public enum OrderStatus {
    PENDING,    // Sipariş oluşturuldu, ödeme bekleniyor.
    CONFIRMED,  // Ödeme alındı, sipariş hazırlanıyor.
    PROCESSING,     //  Sipariş paketleniyor veya üretim aşamasında.
    SHIPPED,       // Sipariş kargo firmasına teslim edildi.
    DELIVERED,    // Sipariş müşteri tarafından teslim alındı.
    CANCELLED,     // Müşteri veya satıcı tarafından iptal edildi.

    RESHIPPED,     // İade Kargoyu Müşteri kargoya geri verdi
    REDELIVERED    // İade Kargo ulaşıldı

}
