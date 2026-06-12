package com.example.pkcn.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MailServiceImpl implements IMailService {

    private final JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    @Override
    public void sendVerificationEmail(String toEmail, String deepLink) {
        try {
            System.out.println("Mail Thread: " + Thread.currentThread().getName());

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Kích hoạt tài khoản mua sắm của bạn");

            String htmlContent = """
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #eee; border-radius: 5px;">
                        <h2 style="color: #333; text-align: center;">Chào mừng bạn đến với App Thương Mại!</h2>
                        <p style="color: #666; font-size: 16px; line-height: 1.5;">
                            Cảm ơn bạn đã đăng ký tài khoản. Vui lòng nhấn vào nút bên dưới để tiến hành kích hoạt tài khoản của mình:
                        </p>
                        <div style="text-align: center; margin: 30px 0;">
                            <a href="%s" style="background-color: #4CAF50; color: white; padding: 12px 25px; text-decoration: none; font-size: 16px; font-weight: bold; border-radius: 5px; display: inline-block;">
                                Kích Hoạt Tài Khoản
                            </a>
                        </div>
                        <p style="color: #999; font-size: 12px; text-align: center;">
                            Nếu nút bấm trên không hoạt động, bạn có thể copy link này vào trình duyệt (hoặc ghi chú): <br/>
                            <span style="color: #0066cc;">%s</span>
                        </p>
                        <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;" />
                        <p style="color: #999; font-size: 12px; text-align: center;">Đây là email tự động, vui lòng không phản hồi email này.</p>
                    </div>
                    """.formatted(deepLink, deepLink);

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.err.println("Lỗi khi gửi email SMTP tới " + toEmail + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Async
    @Override
    public void sendResetPasswordEmail(String toEmail, String deepLink) {
        try {
            System.out.println("Mail Thread (Reset Pwd): " + Thread.currentThread().getName());

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Yêu cầu khôi phục mật khẩu tài khoản của bạn");

            String htmlContent = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #eee; border-radius: 5px;">
                    <h2 style="color: #333; text-align: center;">Yêu Cầu Thay Đổi Mật Khẩu</h2>
                    <p style="color: #666; font-size: 16px; line-height: 1.5;">
                        Chúng tôi nhận được yêu cầu khôi phục mật khẩu cho tài khoản của bạn. Vui lòng nhấn vào nút bên dưới để tiến hành đặt lại mật khẩu mới:
                    </p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" style="background-color: #0056b3; color: white; padding: 12px 25px; text-decoration: none; font-size: 16px; font-weight: bold; border-radius: 5px; display: inline-block;">
                            Đặt Lại Mật Khẩu
                        </a>
                    </div>
                    <p style="color: #d9534f; font-size: 14px; font-weight: bold; text-align: center; margin: 15px 0;">
                        ⚠️ Lưu ý: Đường liên kết này chỉ có hiệu lực trong vòng 10 phút.
                    </p>
                    <p style="color: #999; font-size: 12px; text-align: center;">
                        Nếu nút bấm trên không hoạt động, bạn có thể copy link này vào trình duyệt (hoặc ghi chú): <br/>
                        <span style="color: #0066cc; word-break: break-all;">%s</span>
                    </p>
                    <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;" />
                    <p style="color: #666; font-size: 13px; background-color: #f9f9f9; padding: 10px; border-left: 3px solid #ccc;">
                        Nếu bạn không đưa ra yêu cầu này, vui lòng bỏ qua email này. Tài khoản của bạn vẫn an toàn và mật khẩu sẽ không bị thay đổi.
                    </p>
                    <p style="color: #999; font-size: 12px; text-align: center; margin-top: 20px;">Đây là email tự động, vui lòng không phản hồi email này.</p>
                </div>
                """.formatted(deepLink, deepLink);

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.err.println("Lỗi khi gửi email SMTP Khôi phục mật khẩu tới " + toEmail + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}