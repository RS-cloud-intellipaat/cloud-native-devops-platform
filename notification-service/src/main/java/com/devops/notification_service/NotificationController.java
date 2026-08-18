package com.devops.notification_service;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @GetMapping("/health")
    public String health() {
        return "Notification service is running";
    }

    @PostMapping
    public Notification sendNotification(
            @RequestBody NotificationRequest request) {

        return new Notification(
                request.userId(),
                request.message(),
                "SENT"
        );
    }

    record NotificationRequest(
            Long userId,
            String message
    ) {
    }

    record Notification(
            Long userId,
            String message,
            String status
    ) {
    }
}