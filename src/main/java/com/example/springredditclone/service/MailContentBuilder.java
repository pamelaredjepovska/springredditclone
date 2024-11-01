package com.example.springredditclone.service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

// Fill email Thymeleaf template with specific data
public class MailContentBuilder {
    private final TemplateEngine templateEngine = new TemplateEngine();

    public String build(String message) {
        Context context = new Context();
        // Set the message variable in the contect
        context.setVariable("message", message);

        // Look for a mailTemplate file and replace any 'message' placeholders
        // with the actual message in the context
        return templateEngine.process("mailTemplate", context);
    }
}
