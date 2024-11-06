package store.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductFileReader implements FileReader {

    private final File file;

    public ProductFileReader(String fileName) {
        this.file = new File(fileName);
    }

    @Override
    public List<String> read() {
        BufferedReader br = connectFile();
        List<String> rawProducts = new ArrayList<>();
        fileRead(br, rawProducts);
        return rawProducts;
    }

    private static void fileRead(BufferedReader br, List<String> rawProducts) {
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

    private BufferedReader connectFile() {
        try {
            return new BufferedReader(new java.io.FileReader(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("[ERROR] 파일을 찾을 수 없습니다.");
        }
    }
}
