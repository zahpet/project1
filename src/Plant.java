import java.time.Period;

import java.time.LocalDate;

public class Plant {
    private String name;
    private String note;
    private LocalDate planted;
    private LocalDate watering;
    private Period frequencyOfWatering;

    public Plant(String name, String note, LocalDate planted, LocalDate watering, Period frequencyOfWatering)
    throws PlantException {
        LocalDate base = LocalDate.now();
        this.name = name;
        this.note = note;
        this.planted = planted;
        if (watering.isBefore(base)) {
            throw new PlantException("Datum poslední zálivky nesmí být starší než datum zasazení rostliny.");
        }
        this.watering = watering;
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
}
