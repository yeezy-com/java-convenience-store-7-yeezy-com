package store;

import store.config.ConvenienceConfig;
import store.controller.Convenience;

public class Application {
    public static void main(String[] args) {
        ConvenienceConfig config = new ConvenienceConfig();
        Convenience convenience = config.convenience();
        convenience.enter();
    }
}
