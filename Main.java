public class Main {
    public static void main(String[] args) throws Exception{
        DataManager datamanager = new DataManager();

        datamanager.registerDataProcessor(new Filter());
        datamanager.registerDataProcessor(new Transformator());

        datamanager.loadData("olddata.txt");
        datamanager.processData();
        datamanager.saveData("newdata.txt");
    }
}
