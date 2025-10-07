package se233.chapter2.view;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import se233.chapter2.model.Currency;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CurrencyParentPane extends FlowPane {
    public CurrencyParentPane(List<Currency> currencies) throws ExecutionException, InterruptedException, InterruptedException {
        this.setPadding(new Insets(10));
        refreshPane(currencies);
    }

    public void refreshPane(List<Currency> currencies) throws ExecutionException, InterruptedException {
        this.getChildren().clear();
        for (int i  =0; i < currencies.size(); i++) {
            CurrencyPane cp = new CurrencyPane(currencies.get(i));
            this.getChildren().add(cp);
        }
    }
}
