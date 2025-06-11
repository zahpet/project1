import java.time.Period;

import java.time.LocalDate;

public class Plant {
    private String name;
    private String note;
    private LocalDate planted;
    private LocalDate watering;
    private Period frequencyOfWatering;

    public Plant(String name, String note, LocalDate planted, LocalDate watering, Period frequencyOfWatering){
        this.name = name;
        this.note = note;
        this.planted = planted;
        this.watering = watering;
        this.frequencyOfWatering = frequencyOfWatering;
    }

    public Plant(String name, Period frequencyOfWatering) {
        this(name, "", LocalDate.now(), LocalDate.now(), frequencyOfWatering);
    }

    public Plant(String name) {
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

    public void setWatering(LocalDate watering) {
        this.watering = watering;
    }

    public void setFrequencyOfWatering(Period frequencyOfWatering) {
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
                ", datum poslední zálivky: " + watering.toString() +
                ", datum doporučené další zálivky: " + nextWatering.toString() + ".";
    }

    public void doWateringNow() {
        this.watering = LocalDate.now();
    }
}
