
import model.PN;
import service.PNService;
import util.Constants;
import util.PNReader;



public class Main {
    private PNReader pnReader;
    private PN pn;
    private PNService pnService;

    public static void main(String[] args) {
        Main main = new Main();
        main.startApp();
    }

    public void startApp() {
        System.out.println(Constants.fileName);
        pnReader = new PNReader(Constants.fileName);
        pn = pnReader.convertJsonToPetriNetwork();
        pnService = new PNService(pn, Constants.outName);
        pnService.startSimulation();
    }
}
