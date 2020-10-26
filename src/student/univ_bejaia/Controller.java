package student.univ_bejaia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

public class Controller {
    @FXML
    ProgressBar pb;
    @FXML
    TextField exchange_value;
    @FXML
    TextField after_value;
    @FXML
    Label date_label;
    @FXML
    Button convert_button;
    @FXML
    ComboBox combo1;
    @FXML
    ComboBox combo2;


    private static  Currency currency = new Currency();

    @FXML
    public void initialize() {


        String updatedInfo = currency.parsJSON("eur", "date");
        date_label.setText(updatedInfo);
    }

    @FXML
    public void setConvert_button(ActionEvent event){
        pb.setOpacity(0);
        String value1 = (String) combo1.getSelectionModel().getSelectedItem();
        value1 = value1.toLowerCase();

        String value2 = (String) combo2.getSelectionModel().getSelectedItem();
        value2 = value2.toLowerCase();
        if (!exchange_value.getText().isEmpty()){
            double portfolio = Double.parseDouble(exchange_value.getText());
            after_value.setText(String.valueOf(currency.convert(value1, value2, portfolio)));
        }

    }
@FXML
    public void setUpdate_button(ActionEvent event){
        pb.setOpacity(1);
        Runnable r = new Runnable() {
            @Override
            public void run() {

                if (currency.getData()){
                    pb.setProgress(1);
                }

            }
        };
        new Thread(r).start();


}
}
