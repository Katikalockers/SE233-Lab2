package se233.chapter2.controller;

import javafx.scene.control.Alert;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import se233.chapter2.model.CurrencyEntity;
import se233.chapter2.view.TopPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class FetchData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd");

    public static List<CurrencyEntity> fetchRange(String symbol, int N, String baseCode) {
        String dateEnd = LocalDate.now().format(formatter);
        String dateStart = LocalDate.now().minusDays(N).format(formatter);
        if (baseCode == null) {
            baseCode = "THB";
        }
        if (baseCode.equals(symbol)) {
            throw new IllegalArgumentException("Base currency and symbol cannot be the same.");
        }
        String urlStr = String.format("https://cmu.to/SE233currencyapi?base=%s&symbol=%s&start_date=%s&end_date=%s", baseCode, symbol, dateStart, dateEnd);
        List<CurrencyEntity> histList = new ArrayList<>();
        try {
            String retrievedJson = IOUtils.toString(new URL(urlStr), Charset.defaultCharset());
            JSONObject jsonOBJ = new JSONObject(retrievedJson).getJSONObject("rates");
            Iterator keysToCopyIterator = jsonOBJ.keys();
            while (keysToCopyIterator.hasNext()) {
                String key = (String) keysToCopyIterator.next();
                Double rate = Double.parseDouble(jsonOBJ.get(key).toString());
                histList.add(new CurrencyEntity(rate, key));
            }
            histList.sort(new Comparator<CurrencyEntity>() {
                @Override
                public int compare(CurrencyEntity o1, CurrencyEntity o2) {
                    return o1.getTimeStamp().compareTo(o2.getTimeStamp());
                }
            });
        } catch (MalformedURLException e) {
            System.err.println("Encounter a Malformed URL Exception.");
        } catch (IOException e) {
            System.err.println("Encounter an IO Exception.");
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        } catch (JSONException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid currency code (e.g. USD, JPY, THB).");
            alert.show();
            System.err.println(e.getMessage());
        }
        return histList;
    }
}
