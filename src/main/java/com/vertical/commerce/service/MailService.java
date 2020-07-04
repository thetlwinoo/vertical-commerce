package com.vertical.commerce.service;

import com.vertical.commerce.domain.Customers;
import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.domain.User;

public interface MailService {
    void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    void sendEmailFromTemplate(User user, String templateName, String titleKey);

    void sendActivationEmail(User user);

    void sendCreationEmail(User user);

    void sendPasswordResetMail(User user);

    void sendOrderConfirmationMail(Customers customers, Orders orders);
}
