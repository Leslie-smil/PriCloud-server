package edu.scujcc.pircloud;

import edu.scujcc.pircloud.oss.FileList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author FSMG
 */
@SpringBootApplication
public class PircloudApplication {

    public static void main(String[] args) {

        SpringApplication.run(PircloudApplication.class, args);

//        FileSize fileSize =new FileSize();
//        String data = "";
//        fileSize.calculate(data);
        FileList fileList = new FileList();
        fileList.get();

    }

}
