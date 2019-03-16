package converter;

/**
 * This class provides the methods that allow the conversion according to the
 * parameters defined by the user, that are: length unit (millimeter or inch),
 * data format (decimal or fractional) and data type (linear, area or volume).
 */
public class Converter {

    private String m_strInputUnit;
    private String m_strOutputUnit;
    private String m_strInputFormat;
    private String m_strOutputFormat;
    private int m_intDataType;

    public String getM_strInputUnit() {
        return m_strInputUnit;
    }

    public void setM_strInputUnit(String m_strInputUnit) {
        this.m_strInputUnit = m_strInputUnit;
    }

    public String getM_strOutputUnit() {
        return m_strOutputUnit;
    }

    public void setM_strOutputUnit(String m_strOutputUnit) {
        this.m_strOutputUnit = m_strOutputUnit;
    }

    public String getM_strInputFormat() {
        return m_strInputFormat;
    }

    public void setM_strInputFormat(String m_strInputFormat) {
        this.m_strInputFormat = m_strInputFormat;
    }

    public String getM_strOutputFormat() {
        return m_strOutputFormat;
    }

    public void setM_strOutputFormat(String m_strOutputFormat) {
        this.m_strOutputFormat = m_strOutputFormat;
    }

    public int getM_intDataType() {
        return m_intDataType;
    }

    public void setM_intDataType(int m_intDataType) {
        this.m_intDataType = m_intDataType;
    }

    /**
     * This method returns a string that represents a decimal number, which is
     * the result of the conversion of another decimal number.
     *
     * @param strData the string that contains the decimal number to convert
     * @return a string that contains the result of the conversion, or an error
     * message
     */
    public String DecimalConversion(String strData) {
        double dblConvertedData;
        if (this.isDouble(strData)) {
            double dblData = Double.parseDouble(strData);
            dblConvertedData = this.Convert(dblData);
        } else {
            return "Invalid data";
        }
        int intConvertedData = (int) dblConvertedData;
        double dblRemainder = (double) dblConvertedData - intConvertedData;
        if (dblRemainder == 0) {
            return String.valueOf(intConvertedData);
        } else {
            return String.valueOf(dblConvertedData);
        }
    }

    /**
     * This method returns a string that represents a fractional number, which
     * is the result of the conversion of another fractional number.
     *
     * First of all converts the fractional number into a decimal number, then
     * do the conversion and convert the result (a decimal number) into a
     * fractional number.
     *
     * @param strNumber the string that contains the integer part of the number
     * to convert
     * @param strNumerator the string that contains the numerator of the
     * fractional part of the number to convert
     * @param strDenominator the string that contains the denominator of the
     * fractional part of the number to convert
     * @return a string that contains the result of the conversion, or an error
     * message
     */
    public String FractionalConversion(String strNumber, String strNumerator, String strDenominator) {
        String strDecimalData = this.FractionalToDecimal(strNumber, strNumerator, strDenominator);
        String strConvertedData = this.DecimalConversion(strDecimalData);
        String strFractionalData = this.DecimalToFractional(strConvertedData);
        return strFractionalData;
    }

    /**
     * This method converts the decimal number in input into a fractional
     * number.
     *
     * @param strData the string that contains the decimal number to convert
     * @return a string that contains the result of the conversion, or an error
     * message
     */
    public String DecimalToFractional(String strData) {
        String strFractionalData;
        if (strData.equals("0")) {
            return strData;
        } else if (this.isDouble(strData)) {
            double dblData = Double.parseDouble(strData);
            int intDenominator = 256;
            double dblAccuracy = (double) 1 / intDenominator;
            int intNumber = (int) dblData;
            double dblRemainder = (double) dblData - intNumber;
            double dblNumerator = dblRemainder / dblAccuracy;
            int intApproximatedNumerator = (int) Math.round(dblNumerator);
            String strFraction = this.Simplify(intApproximatedNumerator, intDenominator);
            if (intApproximatedNumerator == 0 && intNumber == 0) {
                strFractionalData = "Change the output format";
            } else if (intApproximatedNumerator == intDenominator) {
                strFractionalData = String.valueOf(intNumber + 1);
            } else if (intApproximatedNumerator == 0) {
                strFractionalData = "" + intNumber;
            } else if (intNumber == 0) {
                strFractionalData = "" + strFraction;
            } else {
                strFractionalData = "" + intNumber + "  " + strFraction;
            }
        } else {
            return "Invalid data";
        }
        return strFractionalData;
    }

    /**
     * This method converts the fractional number in input into a decimal
     * number.
     *
     * @param strNumber the string that contains the integer part of the number
     * to convert
     * @param strNumerator the string that contains the numerator of the
     * fractional part of the number to convert
     * @param strDenominator the string that contains the denominator of the
     * fractional part of the number to convert
     * @return a string that contains the result of the conversion, or an error
     * message
     */
    public String FractionalToDecimal(String strNumber, String strNumerator, String strDenominator) {
        String strDecimalData;
        int intData;
        if (this.isInteger(strNumber)) {
            intData = Integer.parseInt(strNumber);
        } else if (strNumber.isEmpty()) {
            intData = 0;
        } else {
            return "Invalid data";
        }
        if (this.isInteger(strNumerator)) {
            if (this.isInteger(strDenominator)) {
                int intNumerator = Integer.parseInt(strNumerator);
                int intDenominator = Integer.parseInt(strDenominator);
                if (intNumerator < 0) {
                    strDecimalData = "Invalid numerator";
                } else if (intDenominator <= 0) {
                    strDecimalData = "Invalid denominator";
                } else if (intNumerator == intDenominator) {
                    intData++;
                    strDecimalData = String.valueOf(intData);
                } else if (intNumerator == 0) {
                    strDecimalData = String.valueOf(intData);
                } else if (intDenominator == 1 && intNumerator > intDenominator) {
                    intData += intNumerator;
                    strDecimalData = String.valueOf(intData);
                } else {
                    double dblFraction = (double) intNumerator / intDenominator;
                    double dblDecimalData = (double) intData + dblFraction;
                    strDecimalData = String.valueOf(dblDecimalData);
                }
            } else {
                return "Invalid denominator";
            }
        } else {
            return "Invalid numerator";
        }
        return strDecimalData;
    }

    /**
     * This method do the conversion from millimeter to inch and the reverse
     * conversion.
     *
     * @param dblData the data to convert
     * @return the converted data
     */
    private double Convert(double dblData) {
        double dblConvertedData = 0;
        switch (this.m_strInputUnit) {
            case "mm":
                double dblEquivalence = (double) 5 / 127;
                dblConvertedData = dblData * Math.pow(dblEquivalence, this.m_intDataType);
                break;
            case "in":
                dblConvertedData = dblData * Math.pow(25.4, this.m_intDataType);
                break;
        }
        return dblConvertedData;
    }

    /**
     * This method controls if a string contains a double.
     *
     * @param strStringToCheck the string to check
     * @return true if contains a double, otherwise false
     */
    private boolean isDouble(String strStringToCheck) {
        try {
            Double.parseDouble(strStringToCheck);
            return true;
        } catch (Exception exc) {
            return false;
        }
    }

    /**
     * This method controls if a string contains an integer.
     *
     * @param strStringToCheck the string to check
     * @return true if contains an integer, otherwise false
     */
    private boolean isInteger(String strStringToCheck) {
        try {
            Integer.parseInt(strStringToCheck);
            return true;
        } catch (Exception exc) {
            return false;
        }
    }

    /**
     * This method simplifies a fraction and returns a string that represents
     * the result.
     *
     * @param intNumerator the numerator of the fraction
     * @param intDenominator the denominator of the fraction
     * @return a string that contains the simplified fraction
     */
    private String Simplify(int intNumerator, int intDenominator) {
        int intGcd = this.Gcd(intNumerator, intDenominator);
        intNumerator /= intGcd;
        intDenominator /= intGcd;
        String strFraction = "" + intNumerator + "/" + intDenominator;
        return strFraction;
    }

    /**
     * This method calculates the G.C.D (greatest common divisor) of two integer
     * numbers.
     *
     * @param intA the first integer number
     * @param intB the second integer number
     * @return an integer number that represents the G.C.D
     */
    private int Gcd(int intA, int intB) {
        int intGcd = intB;
        while (intA % intB != 0) {
            intGcd = intA % intB;
            if (intGcd == 0) {
                intGcd = intB;
            } else {
                intA = intB;
                intB = intGcd;
            }
        }
        return intGcd;
    }

}
