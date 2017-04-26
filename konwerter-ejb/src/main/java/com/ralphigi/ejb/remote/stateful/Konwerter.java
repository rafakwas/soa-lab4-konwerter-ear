package com.ralphigi.ejb.remote.stateful;

import com.ralphigi.datatypes.ConversionDirection;

public interface Konwerter {
    double getTemperature(double value, ConversionDirection conversionDirection);
}
