import java.time.Period;
import java.time.LocalDate;
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
        this.name = name;
        this.note = note;
        this.planted = planted;
        if (watering.isBefore(planted)) {
            throw new PlantException("Datum poslední zálivky nesmí být starší než datum zasazení rostliny.");
        }
        this.watering = watering;
        LocalDate base = LocalDate.now();
        LocalDate target = base.plus(frequencyOfWatering);
        if (target.isBefore(base) || target.isEqual(base)) {
            throw new PlantException("Nelze zadat zápornou nebo nulovou hodnotu frekvence zálivky.");
        }
        this.frequencyOfWatering = frequencyOfWatering;
    }

    public Plant(String name, Period frequencyOfWatering) throws PlantException {
        this(name, "", LocalDate.now(), LocalDate.now(), frequencyOfWatering);
    }

    public Plant(String name) throws PlantException {
        this(name, "", LocalDate.now(), LocalDate.now(), Period.ofDays(7));
    }

    public Plant(String[] parts, int lineNumber) throws PlantException {
        final int EXPECTED_LENGTH = 5;
        if (parts.length != EXPECTED_LENGTH)
            throw new PlantException("Očekává se načtení " + EXPECTED_LENGTH + " položek na řádku: " + lineNumber
                                        + Arrays.toString(parts));
        try {
            this.name = parts[0].trim();
            this.note = parts[1].trim();
            this.planted = LocalDate.parse(parts[4].trim());
            this.watering = LocalDate.parse(parts[3].trim());
            this.frequencyOfWatering = Period.parse(parts[2].trim());
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
        LocalDate base = LocalDate.now();
        if (watering.isBefore(base)) {
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
        return name.compareTo(other.name);
    }

    public String toTextRecord(String delimeter) {
        String result = "";
        result += this.name + delimeter
                + this.note + delimeter
                + this.planted.toString() + delimeter
                + this.watering.toString() + delimeter
                + this.frequencyOfWatering.toString();
        return result;
    }
}
