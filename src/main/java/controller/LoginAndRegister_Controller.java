package controller;

import GUI.Component.FadePanel;
import GUI.Form.Auth.LoginAndRegister_Form;
import app.AppRouter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import model.User;
import repository.interfaceRepo.UserRepository;

import javax.swing.JOptionPane;
import javax.swing.Timer;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginAndRegister_Controller {
	LoginAndRegister_Form DN_DkForm;
	UserRepository userRepository;

	public LoginAndRegister_Controller(LoginAndRegister_Form DN_DkForm, UserRepository userRepository) {
		this.DN_DkForm = DN_DkForm;
		this.userRepository = userRepository;
		init();
	}

	// Gọi riêng listenActioner() sau khi tạo instance
	public void init() {
		listenActioner();
	}

	private void listenActioner() {
		// sự kiện nút bấm ở cái intro
		DN_DkForm.getBtnToggle().addActionListener((e) -> {
			if (DN_DkForm.getAnim() != null && DN_DkForm.getAnim().isRunning())
				return; // ignore khi đang animate
			if (DN_DkForm.isShowingLogin()) {
				slideIntro(true, () -> { // sau trượt xong -> đổi trạng thái & cập nhật text panel intro
					DN_DkForm.showingLogin = false;
					// cập nhật nội dung txt và nút
					DN_DkForm.getTxt().setText(
							"<html><div style='text-align: center; color:white; font-family: Serif; font-size: 13px;'>"
									+ "Chào mừng bạn đến với<br>"
									+ "<span style='font-family: Serif; font-size: 25px; font-weight: plain; color: white;'>Bún bò huế O HÀ</span>"
									+ "</div></html>");
					DN_DkForm.getBtnToggle().setText("ĐĂNG NHẬP");
				});
			} else {
				slideIntro(false, () -> {
					DN_DkForm.showingLogin = true;
					DN_DkForm.getTxt().setText(
							"<html><div style='text-align: center; color:white; font-family: Serif; font-size: 13px;'>"
									+ "Bạn chưa có tài khoản tại<br>"
									+ "<span style='font-family: Serif; font-size: 25px; font-weight: plain; color: white;'>Bún bò huế O HÀ</span>"
									+ "</div></html>");
					DN_DkForm.getBtnToggle().setText("ĐĂNG KÝ");
				});
			}
		});
		
		// sự kiện đăng nhập
		DN_DkForm.getBtnLogin().addActionListener((event)->{
			try {
	            actionLogin();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		});
	}

	private void actionLogin() throws Exception {
		String username = DN_DkForm.getTxtUserLogin().getText().trim();
		String password = new String(DN_DkForm.getTxtPassLogin().getPassword()).trim();

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		User user = userRepository.login(username, password);
		if (user != null) {
			JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
			AppRouter.goToMainForm();
		    // Đóng form đăng nhập
		    DN_DkForm.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu!");
			return;
		}
	}

	// sự kiện trượt cái Intro
	public void slideIntro(boolean toLeft, Runnable onDone) {
		if (DN_DkForm.getAnim() != null && DN_DkForm.getAnim().isRunning())
			DN_DkForm.getAnim().stop();

		final int startX = DN_DkForm.getIntroPanel().getX();
		final int endX = toLeft ? 0 : DN_DkForm.getFORM_W();
		final int duration = 1000; // ms
		final int fps = 60;
		final int delay = 1000 / fps;
		final long startTime = System.currentTimeMillis();

		// lấy contentPanel trong intro (FadePanel)
		FadePanel content = (FadePanel) DN_DkForm.getIntroPanel().getClientProperty("contentPanel");
		if (content == null)
			return;

		// đảm bảo content có bounds ban đầu
		content.setBounds(0, 0, DN_DkForm.getFORM_W(), DN_DkForm.getFRAME_H());
		content.setAlpha(1f);

		DN_DkForm.setAnim(new Timer(delay, null));
		DN_DkForm.getAnim().addActionListener(ev -> {
			float t = (System.currentTimeMillis() - startTime) / (float) duration;
			if (t > 1f)
				t = 1f;

			// easing easeOut (mượt)
			float ease = (float) (1 - Math.pow(1 - t, 3));

			// vị trí X của introPanel
			int deltaX = endX - startX;
			int newX = (int) (startX + deltaX * ease);

			// giãn rộng nhẹ (maxExtraW px)
			int maxExtraW = 70;
			float scaleEase = (float) Math.sin(Math.PI * t);
			int extraW = (int) (maxExtraW * scaleEase);

			// Cập nhật introPanel (di chuyển + mở rộng tạm thời)
			DN_DkForm.getIntroPanel().setBounds(newX, 0, DN_DkForm.getINTRO_W() + extraW, DN_DkForm.getFRAME_H());

			// Parallax: content di chuyển chậm hơn và dịch ngược chiều một chút
			int parallaxFactor = 3;
			int contentOffset = (int) ((deltaX / (float) parallaxFactor) * ease);
			// nếu toLeft thì content di chuyển ngược chiều (nhỏ) để cảm giác parallax
			int contentX = toLeft ? -contentOffset : -contentOffset;
			content.setBounds(contentX, 0, DN_DkForm.getINTRO_W() + extraW, DN_DkForm.getFRAME_H());

			// fade out: content mờ dần khi chuyển
			float alpha = 1f - ease;
			content.setAlpha(alpha);

			DN_DkForm.getLayered().repaint();

			if (t >= 1f) {
				DN_DkForm.getAnim().stop();
				// reset size/position chuẩn
				DN_DkForm.getIntroPanel().setBounds(endX, 0, DN_DkForm.getINTRO_W(), DN_DkForm.getFRAME_H());
				content.setBounds(0, 0, DN_DkForm.getINTRO_W(), DN_DkForm.getFRAME_H());
				content.setAlpha(0f); // ẩn tạm để chuẩn bị fadeIn (onDone sẽ đổi text)
				if (onDone != null)
					onDone.run();
				// sau khi onDone thay text, fade in content
				animateFadeIn(content, toLeft);
			}
		});
		DN_DkForm.getAnim().start();
	}

	// sự kiện của các thành phần trong cái Intro
	public void animateFadeIn(FadePanel content, boolean fromLeft) {
		if (DN_DkForm.getAnim() != null && DN_DkForm.getAnim().isRunning())
			DN_DkForm.getAnim().stop();

		final int duration = 600; // ms
		final int fps = 60;
		final int delay = 1000 / fps;
		final long startTime = System.currentTimeMillis();

		final int startContentX = fromLeft ? -(DN_DkForm.getINTRO_W() / 6) : (DN_DkForm.getINTRO_W() / 6); // bắt đầu
																											// hơi ở bên
		final int endContentX = 0;

		DN_DkForm.setAnim(new Timer(delay, null));
		DN_DkForm.getAnim().addActionListener(ev -> {
			float t = (System.currentTimeMillis() - startTime) / (float) duration;
			if (t > 1f)
				t = 1f;
			// ease in
			float ease = (float) Math.pow(t, 3);

			int newContentX = (int) (startContentX + (endContentX - startContentX) * ease);
			content.setBounds(newContentX, 0, DN_DkForm.getINTRO_W(), DN_DkForm.getFRAME_H());

			float alpha = ease; // từ 0 -> 1
			content.setAlpha(alpha);

			DN_DkForm.getLayered().repaint();

			if (t >= 1f) {
				DN_DkForm.getAnim().stop();
				content.setBounds(0, 0, DN_DkForm.getINTRO_W(), DN_DkForm.getFRAME_H());
				content.setAlpha(1f);
			}
		});
		DN_DkForm.getAnim().start();
	}
}
