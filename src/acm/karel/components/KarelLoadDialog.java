package acm.karel.components;

import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;

public class KarelLoadDialog extends FileDialog implements FilenameFilter {

    public KarelLoadDialog() {
        super((Frame) null, "Select World file");
        System.out.println(getLoadDirectory());
        setDirectory(getLoadDirectory());
        setMode(FileDialog.LOAD);
        setVisible(true);
    }

    private String getLoadDirectory() {
        String dir = System.getProperty("user.dir");
        if ((new File(dir, "worlds")).isDirectory()) {
            dir = dir + "/worlds";
        }

        return dir;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(".w");
    }
}
