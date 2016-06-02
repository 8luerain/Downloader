package test.bluerain.youku.com.downloader.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Project: TestProject.
 * Data: 2016/5/31.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    public static void saveFile(InputStream inputStream, String fileName) {
        File externalDir = Environment.getExternalStorageDirectory();
        String absolutePath = externalDir.getAbsolutePath();
        Log.d(TAG, "saveFile: absolutePath[" + absolutePath + "]");
        String testDoc = absolutePath + "/test/" + fileName;
        File writeFile = new File(testDoc);
        BufferedOutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            if (!writeFile.getParentFile().exists()) {
                writeFile.getParentFile().mkdir();
            }
            if (!writeFile.exists()) {
                writeFile.createNewFile();
            }
            byte[] buf = new byte[1024];
            bufferedInputStream = new BufferedInputStream(inputStream);
            outputStream = new BufferedOutputStream(new FileOutputStream(writeFile));
            while (bufferedInputStream.read(buf) != -1) {
                outputStream.write(buf);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bufferedInputStream) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
