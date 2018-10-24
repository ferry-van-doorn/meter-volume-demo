package metervolumedemo.meterreading.csv;

import com.opencsv.bean.CsvBindByName;

public class MeterReadingCsvDto {

    @CsvBindByName(column = "MeterID", required = true)
    private String meterId;

    @CsvBindByName(column = "Profile", required = true)
    private String profile;

    @CsvBindByName(column = "Month", required = true)
    private String month;

    @CsvBindByName(column = "Meter reading", required = true)
    private Integer meterReading;

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(Integer meterReading) {
        this.meterReading = meterReading;
    }
}
