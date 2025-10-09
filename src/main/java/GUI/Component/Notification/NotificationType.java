/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI.Component.Notification;

import java.awt.Color;

/**
 *
 * @author Admin
 */
public enum NotificationType {
    // Thêm tham số thứ 3 là đường dẫn tới icon
    // Đường dẫn bắt đầu bằng "/" để trỏ tới gốc của thư mục resources
    SUCCESS(new Color(223, 240, 216), new Color(60, 118, 61), "/Images/icons/success.png"),
    INFO(new Color(217, 237, 247), new Color(49, 112, 143), "/Images/icons/info.png"),
    WARNING(new Color(252, 248, 227), new Color(138, 109, 59), "/Images/icons/warning.png"),
    ERROR(new Color(242, 222, 222), new Color(169, 68, 66), "/Images/icons/error.png");

    private final Color backgroundColor;
    private final Color foregroundColor;
    private final String iconPath; // Thêm thuộc tính này

    NotificationType(Color backgroundColor, Color foregroundColor, String iconPath) {
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.iconPath = iconPath;
    }

    public Color getBackgroundColor() { return backgroundColor; }
    public Color getForegroundColor() { return foregroundColor; }
    public String getIconPath() { return iconPath; } // Thêm getter này
}
