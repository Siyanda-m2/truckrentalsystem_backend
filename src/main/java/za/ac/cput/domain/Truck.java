package za.ac.cput.domain;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * Ayanda Phumzile Khoza (218057172)
 * Date: 23 May 2024
 * */


@Entity
public class Truck {
    @Id
    private String  vin;
    private String model;
    @Lob
    @Column(length = 10485760)
    private byte[] photo;
    private boolean availability;
    private String licensePlate;
    private double currentMileage;


    @ManyToOne
    @JoinColumn(name = "truckTypeId")
    private TruckType truckType;
    @ManyToOne
    @JoinColumn(name = "insuranceID")
    private Insurance insurance;

    protected Truck(Truck truck) {

    }
    // Auto-generate VIN before saving
    @PrePersist
    protected void onCreate() {
        if (this.vin == null) {
            this.vin = generateVin();
        }
    }

    private String generateVin() {
        // Example: Generate a VIN with 18 characters (alphanumeric)
        return UUID.randomUUID().toString().substring(0, 18).toUpperCase();
    }
    private Truck(Builder builder) {
        this.vin = builder.vin;
        this.model = builder.model;
        this.photo=builder.photo;
        this.availability = builder.availability;
        this.licensePlate =builder.licensePlate;
        this.currentMileage = builder.currentMileage;
        this.truckType = builder.truckType;
        this.insurance = builder.insurance;

    }

    protected Truck() {

    }

    public String getVin() {
        return vin;
    }

    public String getModel() {
        return model;
    }
    public byte[] getPhoto() {
        return photo;
    }

    public boolean isAvailability() {
        return availability;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public double getCurrentMileage() {
        return currentMileage;
    }



    public TruckType getTruckType() {
        return truckType;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return availability == truck.availability && Double.compare(currentMileage, truck.currentMileage) == 0 && Objects.equals(vin, truck.vin) && Objects.equals(model, truck.model) && Arrays.equals(photo, truck.photo) && Objects.equals(licensePlate, truck.licensePlate) && Objects.equals(truckType, truck.truckType) && Objects.equals(insurance, truck.insurance);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(vin, model, availability, licensePlate, currentMileage, truckType, insurance);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "vin='" + vin + '\'' +
                ", model='" + model + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", availability=" + availability +
                ", licensePlate='" + licensePlate + '\'' +
                ", currentMileage=" + currentMileage +
                ", truckType=" + truckType +
                ", insurance=" + insurance +
                '}';
    }


    public static class Builder {
        private String vin;
        private String model;
        private byte[] photo;
        private boolean availability;
        private String licensePlate;
        private double currentMileage;
        private TruckType truckType;
        private Insurance insurance;
        public Builder setVin(String vin) {
            this.vin = vin;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }
        public Builder setPhoto(byte[] photo) {
            this.photo = photo;
            return this;
        }
        public Builder setAvailability(boolean availability) {
            this.availability = availability;
            return this;
        }

        public Builder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }


        public Builder setCurrentMileage(double currentMileage) {
            this.currentMileage = currentMileage;
            return this;
        }


        public Builder setTruckType(TruckType truckType) {
            this.truckType = truckType;
            return this;
        }

        public Builder setInsurance(Insurance insurance) {
            this.insurance = insurance;
            return this;
        }

        public Builder copy(Truck truck) {
            this.vin = truck.vin;
            this.model = truck.model;
            this.photo=truck.photo;
            this.availability = truck.availability;
            this.licensePlate = truck.licensePlate;
            this.truckType = truck.truckType;
            this.insurance = truck.insurance;
            return this;
        }

        public Truck build() {
            return new Truck(this);
        }
    }
}


