import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class SkyPanelAction extends AnAction {
    //здесь будет сохранятся экземпляр проекта
    static private VirtualFile virtualFile;

    static public String getVirtualPath() {
        return virtualFile.getPath();
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        virtualFile = e.getProject().getBaseDir();
    }


    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
    }


}
