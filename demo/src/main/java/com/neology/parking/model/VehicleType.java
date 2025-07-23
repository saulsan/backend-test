package com.neology.parking.model;

public enum VehicleType {
    OFFICIAL(0.0),
    RESIDENT(0.05),
    NON_RESIDENT(0.5);

    private final double ratePerMinute;

    VehicleType(double ratePerMinute) {
        this.ratePerMinute = ratePerMinute;
    }

    public double getRatePerMinute() {
        return ratePerMinute;
    }

    public boolean isPayingAtExit() {
        return this == NON_RESIDENT;
    }

    public boolean isResident() {
        return this == RESIDENT;
    }

    public boolean isOfficial() {
        return this == OFFICIAL;
    }
}
