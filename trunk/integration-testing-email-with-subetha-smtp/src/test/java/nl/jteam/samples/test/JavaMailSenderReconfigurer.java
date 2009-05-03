package nl.jteam.samples.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import static java.lang.String.*;

/**
 * A {@link BeanPostProcessor} that reconfigures all {@link JavaMailSenderImpl JavaMailSenders} found in the Spring
 * Application Context to target the given {@link #hostname} and {@link #port}. Useful for integration testing where
 * testing emails can be directed to a local mail server, like for example the {@link org.subethamail.wiser.Wiser Wiser} mail server.
 */
public class JavaMailSenderReconfigurer implements BeanPostProcessor {

    private final Log logger = LogFactory.getLog(getClass());

    private String hostname;
    private int port;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof JavaMailSenderImpl) {
            reconfigureJavaMailSender((JavaMailSenderImpl) bean, beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private void reconfigureJavaMailSender(JavaMailSenderImpl mailSender, String beanName) {
        logger.info(format("Reconfiguring JavaMailSender '%s' to use mail server '%s:%s'", beanName, hostname, port));
        mailSender.setHost(hostname);
        mailSender.setPort(port);
    }

    public void setHostname(String hostname) {
        Assert.isTrue(StringUtils.hasText(hostname), "Hostname cannot be blank");
        this.hostname = hostname;
    }

    public void setPort(int port) {
        Assert.isTrue(port > 0, "Port must be greater than 0");
        this.port = port;
    }
}
