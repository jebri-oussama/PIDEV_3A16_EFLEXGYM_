package API;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        CalendrierAPI api = new CalendrierAPI();
        api.startServer(8080);
    }
}
