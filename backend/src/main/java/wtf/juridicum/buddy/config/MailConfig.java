package wtf.juridicum.buddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

  /**
   * Sets up a JavaMailSenderImpl with the values given in the application.yml file.
   * @return a JavaMailSenderImpl Bean
   */
  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    Properties props = javaMailSender.getJavaMailProperties();
    javaMailSender.setHost(props.getProperty("mail.host", "127.0.0.1"));
    javaMailSender.setPort(Integer.parseInt(props.getProperty("mail.port", "1025")));
    return javaMailSender;
  }
}
