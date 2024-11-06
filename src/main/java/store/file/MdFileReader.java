package store.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MdFileReader {

    private static final String FILE_PREFIX = "src/main/resources/";
    
    public List<String> read(String fileName) {
        BufferedReader br = connectFile(fileName);
        List<String> rawProducts = new ArrayList<>();
        fileRead(br, rawProducts);
        return rawProducts;
    }

    private void fileRead(BufferedReader br, List<String> rawProducts) {
        try {
            String rawProduct = br.readLine();
            while ((rawProduct = br.readLine()) != null) {
                rawProducts.add(rawProduct);
            }
            br.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 읽는 중 에러가 발생했습니다.");
        }
    }

    private BufferedReader connectFile(String fileName) {
        try {
            return new BufferedReader(new java.io.FileReader(FILE_PREFIX + fileName));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 찾을 수 없습니다.");
        }
    }
}
