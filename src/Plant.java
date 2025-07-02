import java.time.Period;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class Plant implements Comparable<Plant> {
    private String name;
    private String note;
    private LocalDate planted;
    private LocalDate watering;
    private Period frequencyOfWatering;

    public Plant(String name, String note, LocalDate planted, LocalDate watering, Period frequencyOfWatering)
    throws PlantException {
        this.setName(name);
        this.setNote(note);
        this.setPlanted(planted);
        this.setWatering(watering);
        this.setFrequencyOfWatering(frequencyOfWatering);
    }

    public Plant(String name, Period frequencyOfWatering) throws PlantException {
        this(name, "", LocalDate.now(), LocalDate.now(), frequencyOfWatering);
    }

    public Plant(String name) throws PlantException {
        this(name, "", LocalDate.now(), LocalDate.now(), Period.ofDays(7));
    }

    static public Plant build(String[] parts, int lineNumber) throws PlantException {
        final int EXPECTED_LENGTH = 5;
        if (parts.length != EXPECTED_LENGTH)
            throw new PlantException("Očekává se načtení " + EXPECTED_LENGTH + " položek na řádku: " + lineNumber
                    + Arrays.toString(parts));
        try {
            String name = parts[0].trim();
            String note = parts[1].trim();
            LocalDate planted = LocalDate.parse(parts[4].trim());
            LocalDate watering = LocalDate.parse(parts[3].trim());
            Period frequencyOfWatering = Period.ofDays(Integer.parseInt(parts[2].trim()));
            return new Plant(name, note, planted, watering, frequencyOfWatering);
        } catch (NumberFormatException e) {
            throw new PlantException("Chyba při načtení číselné hodnoty na řádku " + lineNumber
                    + ": " + e.getMessage());
        } catch (DateTimeParseException e) {
            throw new PlantException("Chyba při načtení hodnoty data na řádku " + lineNumber
                    + ": " + e.getMessage());
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPlanted(LocalDate planted) {
        this.planted = planted;
    }

    public void setWatering(LocalDate watering) throws PlantException {
        if (watering.isBefore(this.planted)) {
            throw new PlantException("Datum poslední zálivky nesmí být starší než datum zasazení rostliny.");
        }
        this.watering = watering;
    }

    public void setFrequencyOfWatering(Period frequencyOfWatering) throws PlantException {
        LocalDate base = LocalDate.now();
        LocalDate target = base.plus(frequencyOfWatering);
        if (target.isBefore(base) || target.isEqual(base)) {
            throw new PlantException("Nelze zadat zápornou nebo nulovou hodnotu frekvence zálivky.");
        }
        this.frequencyOfWatering = frequencyOfWatering;
    }

    public String getName() {
        return this.name;
    }

    public String getNote() {
        return this.note;
    }

    public LocalDate getPlanted() {
        return this.planted;
    }

    public LocalDate getWatering() {
        return this.watering;
    }

    public Period getFrequencyOfWatering() {
        return this.frequencyOfWatering;
    }

    public String getWateringInfo() {
        LocalDate nextWatering = this.watering.plus(this.frequencyOfWatering);
        return "Název květiny: " + this.name +
                ", datum poslední zálivky: " + watering +
                ", datum doporučené další zálivky: " + nextWatering + ".";
    }

    public void doWateringNow() {
        this.watering = LocalDate.now();
    }

    @Override
    public int compareTo(Plant other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        return "Rostlina " + this.name + " = {" +
                "poznámky: " + this.note + ", " +
                "frekvence zálivky [dny]: " + this.frequencyOfWatering.getDays() + ", " +
                "datum poslední zálivky: " + this.watering.format(formatter) + ", " +
                "datum zasazení: " + this.planted.format(formatter) +
                "}";
    }

    public String toTextRecord(String delimeter) {
        String result = "";
        result += this.name + delimeter
                + this.note + delimeter
                + this.frequencyOfWatering.getDays() + delimeter
                + this.watering.toString() + delimeter
                + this.planted.toString();
        return result;
    }
}
