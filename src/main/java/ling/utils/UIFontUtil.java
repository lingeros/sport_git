package ling.utils;

import javax.swing.*;
import java.awt.*;

/**
 * 用来存储UI 字体格式的
 */
public class UIFontUtil {

    public static final String[] fontName = {
            "Label", "CheckBox", "PopupMenu", "MenuItem", "CheckBoxMenuItem", "JRadioButtonMenuItem",
            "ComboBox", "Button", "Tree", "ScrollPane", "TabbedPane", "EditorPane", "TitledBorder", "Menu",
            "TextArea", "OptionPane", "MenuBar", "ToolBar", "ToggleButton", "ToolTip", "ProgressBar", "TableHeader",
            "Panel", "List", "ColorChooser", "PasswordField", "TextField", "Table", "Label", "Viewport",
            "RadioButtonMenuItem", "RadioButton", "DesktopPane", "InternalFrame"};

    /**
     * 设置界面字体格式
     */
    public static void setUIFont(){
        Font f = new Font("微软雅黑", Font.PLAIN, 11);
        for (String item : fontName) {
            UIManager.put(item + ".font", f);
        }
    }

}
