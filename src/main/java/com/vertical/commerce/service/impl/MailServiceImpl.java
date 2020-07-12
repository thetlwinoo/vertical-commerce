package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.Customers;
import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.domain.User;
import com.vertical.commerce.domain.enumeration.OrderLineStatus;
import com.vertical.commerce.service.MailService;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.springframework.core.io.Resource;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
@Transactional
public class MailServiceImpl implements MailService {

    private final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private static final String USER = "user";

    private static final String CUSTOMER = "customer";

    private static final String ORDERS = "orders";

    private static final String BASE_URL = "baseUrl";

    private static final String AVAILABLE_STATUS = "availableStatus";

    private static final String CANCELLED_STATUS = "cancelledStatus";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    @Value("classpath:/mail-logo.png")
    Resource mailLogoResource;

    public MailServiceImpl(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());

            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom(), "ZeZaWar Myanmar");
            message.setSubject(subject);
            message.setText(content, isHtml);
//            message.addInline("logo.png",mailLogoResource);

            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        }  catch (MailException | MessagingException | UnsupportedEncodingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Override
    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Override
    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Override
    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Override
    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

    @Override
    @Async
    public void sendOrderConfirmationMail(Customers customers, Orders orders) {
        log.debug("Sending password reset email to '{}'", customers.getDeliveryAddress().getContactEmailAddress());
        if (customers.getDeliveryAddress().getContactEmailAddress() == null) {
            log.debug("Email doesn't exist for user '{}'", customers.getName());
            return;
        }

        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable(CUSTOMER, customers);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable(ORDERS, orders);
        context.setVariable(AVAILABLE_STATUS, OrderLineStatus.AVAILABLE);
        context.setVariable(CANCELLED_STATUS, OrderLineStatus.CANCELLED);

        String content = templateEngine.process("mail/orderConfirmationEmail", context);
        String subject = messageSource.getMessage("email.confirmation.title", null, locale);
        subject = "Hey " + customers.getName() + ", " + subject;

        sendEmail(customers.getDeliveryAddress().getContactEmailAddress(), subject, content, false, true);
    }
}
