
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SkyPanelFactory implements ToolWindowFactory, DumbAware {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        SkyPanelContent toolWindowContent = new SkyPanelContent();
        Content content = ContentFactory.getInstance().createContent(toolWindowContent.getContentPanel(), "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private static class SkyPanelContent extends AnAction {

        private final JPanel contentPanel = new JPanel();
        private final JTextArea area = new JTextArea();

        private final JTextField textField2 = new JTextField();
        private final JTextField textField1 = new JTextField();



        public SkyPanelContent() {
            contentPanel.setLayout(new BorderLayout(20, 20));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
            contentPanel.add(valuePanel(),BorderLayout.BEFORE_FIRST_LINE);
            contentPanel.add(createControlsPanel(), BorderLayout.CENTER);
            contentPanel.add(resultPanel(), BorderLayout.AFTER_LAST_LINE);
        }
        @NotNull
        private JPanel valuePanel() {
            JPanel panel = new JPanel();

            JLabel label1= new JLabel("Value 1");
            panel.add(label1);
            panel.add(textField1);
            JLabel label2= new JLabel("Value 2");
            panel.add(label2);
            panel.add(textField2);
            return panel;
        }
        @NotNull
        private JPanel resultPanel() {
            JPanel panel = new JPanel();
            JLabel label1= new JLabel("result");

            panel.add(label1);
            panel.add(area);
            return panel;
        }
        //метод для создания кнопок, тут также имеются Listener's которые срабатывают при нажатии этих самых кнопок
        @NotNull
        private JPanel createControlsPanel() {
            JPanel controlsPanel = new JPanel();
            JButton getValueButton = new JButton("get value");
            getValueButton.addActionListener(e-> displaySum());
            getValueButton.setBackground(Color.blue);
            controlsPanel.add(getValueButton);

            //чтобы получить дерево нужно прожать две кнопки на клавиатуре "["  и  "]" и нажать на кнопку "get tree"
            JButton getTreeButton = new JButton("get tree");
            getTreeButton.addActionListener(e -> {
                StringBuilder sb;
                try {
                    System.out.println(SkyPanelAction.getVirtualPath());
                    sb = displayProjectTree(new File(SkyPanelAction.getVirtualPath()),new StringBuilder(),0);
                }catch (NullPointerException ex){
                    area.setText("please click on the key buttons \"[\" and \"]\" then click on \"get tree\" again\n");
                    return;
                }
                area.setText(sb.toString());

            });
            getTreeButton.setBackground(Color.blue);
            controlsPanel.add(getTreeButton);

            JButton getSocketButton = new JButton("getSocket");
            getSocketButton.addActionListener(e -> {
                area.setText("this button is not working yet");
                System.out.println(area.getText());
            });
            getSocketButton.setBackground(Color.BLUE);
            controlsPanel.add(getSocketButton);
            return controlsPanel;
        }
        private StringBuilder displayProjectTree(File directory, StringBuilder sb, int level) {
            // Добавляем отступы для текущего уровня
            sb.append("-".repeat(Math.max(0, level)));
            sb.append(directory.getName()); // Добавляем имя текущей директории или файла
            sb.append("\n");
            if (directory.isDirectory()) {
                // Если текущий элемент является директорией, рекурсивно вызываем метод для всех файлов и папок внутри нее
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        displayProjectTree(file, sb, level + 1);
                    }
                }
            }
            return sb;
        }

        private void displaySum(){
            double value1;
            double value2;
            try{
                value1 = Double.parseDouble(textField1.getText());
                value2 = Double.parseDouble(textField2.getText());
            }catch (NumberFormatException e){
                area.setText("wrong number format");
                return;
            }
            area.setText(String.valueOf(value1+value2));
            System.out.println(area.getText());
        }
        public JPanel getContentPanel() {
            return contentPanel;
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            System.out.println();
        }
    }
}