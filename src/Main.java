import java.time.LocalDate;
import java.time.Period;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String FOLDER_PATH = System.getProperty("user.dir");
    private static final String FILE_NAME1 = "kvetiny-spatne-datum.txt";
    private static final String FILE_NAME2 = "kvetiny-spatne-frekvence.txt";
    private static final String FILE_NAME3 = "kvetiny.txt";
    private static final String OUTPUT_FILE = "output.txt";
    private static final String DELIMETER = "\t";

    public static void main(String[] args) throws PlantException {

        ListOfPlants plants1 = new ListOfPlants();
        try {
            plants1.readFromFile(FOLDER_PATH + "\\" + FILE_NAME1, DELIMETER);
        } catch (PlantException e) {
            System.err.println("Chyba při načtení souboru " + FILE_NAME1 + ": " + e.getMessage());
        }

        ListOfPlants plants2 = new ListOfPlants();
        try {
            plants2.readFromFile(FOLDER_PATH + "\\" + FILE_NAME2, DELIMETER);
        } catch (PlantException e) {
            System.err.println("Chyba při načtení souboru " + FILE_NAME2 + ": " + e.getMessage());
        }

        ListOfPlants plants3 = new ListOfPlants();
        try {
            plants3.readFromFile(FOLDER_PATH + "\\" + FILE_NAME3, DELIMETER);
        } catch (PlantException e) {
            System.err.println("Chyba při načtení souboru " + FILE_NAME3 + ": " + e.getMessage());
        }


        System.out.println("Květina" + "\t" + "zalitá dne");
        for (Plant plant : plants3.getListOfPlants()) {
            System.out.println(plant.getName() + "\t" + plant.getWatering().toString());
        }

        plants3.addPlant(new Plant("Levandule", "nerozkvetlá sazenice",
                LocalDate.of(2022, 8, 11),
                LocalDate.of(2022, 9, 20),
                Period.ofDays(30)));
        System.out.println();
        System.out.println(plants3.getPlant(2).getName());
        System.out.println(plants3.getPlant(2).getFrequencyOfWatering().getDays());
        System.out.println(plants3.getPlant(2).getWatering().toString());

        int i;
        for (i = 1; i <= 10; i++) {
            plants3.addPlant(new Plant("Tulipán na prodej " + i));
            plants3.getPlant(plants3.getListOfPlants().size() - 1).setFrequencyOfWatering(Period.ofDays(14));
        }

        plants3.removePlant(3);

        plants3.writeToTextFile(FOLDER_PATH + "\\" + OUTPUT_FILE, DELIMETER);

        ListOfPlants plants4 = new ListOfPlants();
        plants4.readFromFile(FOLDER_PATH + "\\" + OUTPUT_FILE, DELIMETER);

        System.out.println();
        System.out.println("Řazení seznamu podle názvu:");
        plants4.sort();
        plants4.getListOfPlants().forEach(System.out::println);

        System.out.println();
        System.out.println("Řazení seznamu podle poslední zálivky:");
        plants4.sortByWatering();
        plants4.getListOfPlants().forEach(System.out::println);

    }
}