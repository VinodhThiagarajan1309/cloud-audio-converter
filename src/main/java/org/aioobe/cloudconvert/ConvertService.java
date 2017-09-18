package org.aioobe.cloudconvert;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.apache.commons.io.FileUtils;

/**
 * Created by vthiagarajan on 9/17/17.
 */
public class ConvertService {

    public static void main(String[] args) {
        try {

            URL url = new URL("https://s3.us-east-2.amazonaws.com/jonsnow-vinodh/1505678447966wow.ogg");
            File f = new File(url.getFile());

            // Create service object
            CloudConvertService service = new CloudConvertService("Gb1Wz3RREV7eZKf-ts6xiZtgVHJtlWoXDLDbR_8R1ujr7G5qc_LGIfunAaJkrAmlndJbs8YCCC3f2OALTJL8Lw");

// Create conversion process
            ConvertProcess process = service.startProcess("ogg", "mp3");

// Perform conversion
            process.startConversion(f);

// Wait for result
            ProcessStatus status;
            waitLoop: while (true) {
                status = process.getStatus();

                switch (status.step) {
                    case FINISHED: break waitLoop;
                    case ERROR: throw new RuntimeException(status.message);
                }

                // Be gentle
                Thread.sleep(200);
            }

// Download result
            service.download(status.output.url, new File("/Users/vthiagarajan/Downloads/DONT_DELETE_REPOS/cloudconvert-master/src/main/java/org/aioobe/cloudconvert/aal.mp3"));

// Clean up
            process.delete();
        } catch (Exception e) {
e.printStackTrace();
        }
    }
}
