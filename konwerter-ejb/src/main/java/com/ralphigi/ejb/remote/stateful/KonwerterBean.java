package com.ralphigi.ejb.remote.stateful;

import com.ralphigi.datatypes.ConversionDirection;

import javax.ejb.Remote;
import javax.ejb.Stateful;

@Stateful(name = "KonwerterEJB")
@Remote(Konwerter.class)
public class KonwerterBean implements Konwerter {

    public KonwerterBean() {
    }

    public double getTemperature(double value, ConversionDirection conversionDirection) {
        return (conversionDirection.equals(ConversionDirection.CTOF)) ? (9.0/5.0)*value+32 : (conversionDirection.equals(ConversionDirection.FTOC)) ? (5.0/9.0)*(value-32) : Integer.MAX_VALUE;
//        if (conversionDirection.equals(ConversionDirection.CTOF)) {
//            System.out.println("Conversion CTOF");
//            result = (9.0/5.0)*value + 32;
//        }
//        else if (conversionDirection.equals(ConversionDirection.FTOC)) {
//            System.out.println("Conversion FTOC");
//            result = (5.0/9.0)*(value-32);
//        }
//        else {
//            throw new RuntimeException("Conversion direction not allowed");
//        }
//        return result;
    }


}
