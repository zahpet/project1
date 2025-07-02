import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ListOfPlants {
    private List<Plant> listOfPlants;

    public ListOfPlants(List<Plant> list) {
        this.listOfPlants = new ArrayList<>(list);
    }

    public ListOfPlants() {
        this.listOfPlants = new ArrayList<>();
    }

    public List<Plant> getListOfPlants() {
        return new ArrayList<>(listOfPlants);
    }

    public void setListOfPlants(List<Plant> listOfPlants) {
        this.listOfPlants = new ArrayList<>(listOfPlants);
    }

    public void addPlant(Plant plant) {
        listOfPlants.add(plant);
    }

    public Plant getPlant(int index) {
        return listOfPlants.get(index);
    }

    public void removePlant(int index) {
        listOfPlants.remove(index - 1);
    }

    public void removePlant(Plant plant) {
        listOfPlants.remove(plant);
    }

    public ListOfPlants coppyListOfPlants(ListOfPlants list) {
        return new ListOfPlants(list.getListOfPlants());
    }

    public ListOfPlants plantsToWater() {
        LocalDate base = LocalDate.now();
        ListOfPlants plants = new ListOfPlants();
        for (Plant plant : listOfPlants) {
            if (base.isAfter(plant.getWatering().plus(plant.getFrequencyOfWatering()))) plants.addPlant(plant);
        }
        return plants;
    }

    public void sort() {
        listOfPlants.sort(Plant::compareTo);
    }

    public void sortByWatering() {
        listOfPlants.sort(Comparator.comparing(Plant::getWatering).reversed());
    }

    public void readFromFile(String filePath, String delimeter) throws PlantException {
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                String record = scanner.nextLine();
                lineNumber++;
                String[] parts = record.split(delimeter);
                listOfPlants.add(Plant.build(parts, lineNumber));
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + filePath + " nelze otevřít: " + e.getLocalizedMessage());
        }
    }

    public void writeToTextFile(String filePath, String delimeter) throws PlantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (Plant plant : listOfPlants) writer.println(plant.toTextRecord(delimeter));
        } catch (IOException e) {
            throw new PlantException(" " + e.getMessage());
        }
    }
}
