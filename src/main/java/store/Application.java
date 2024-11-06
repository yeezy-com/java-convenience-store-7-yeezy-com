package store;

import store.config.ConvenienceConfig;
import store.controller.ConvenienceController;

public class Application {
    public static void main(String[] args) {
        ConvenienceConfig config = new ConvenienceConfig();
        ConvenienceController convenienceController = config.convenienceController();
        convenienceController.run();
    }
}
