package controller;

import view.ModelTable;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.*;
import java.sql.*;

public class SearchContactFormController extends StageList {

    public JFXTextField txtSearch;

    @FXML
    private TableView <ModelTable> contactTable;

    @FXML
    private TableColumn<ModelTable, String> colFirstName;

    @FXML
    private TableColumn<ModelTable, String> colLastName;

    @FXML
    private TableColumn<ModelTable, String> colAddress;

    @FXML
    private TableColumn<ModelTable, String> colContact;

    @FXML
    private TableColumn<ModelTable, String> colEmail;


    ObservableList<ModelTable> row= FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        contactTable.getItems().clear();
    }

    public void searchOnAction(MouseEvent mouseEvent) {
        String searchFirstName = txtSearch.getText();
        String SQl = "select * from contactdetails where firstName=? order by firstName asc,lastName asc";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/numberbook", "root", "1234");
            PreparedStatement stm = connection.prepareStatement(SQl);
            stm.setObject(1,searchFirstName);
            ResultSet rst = stm.executeQuery();
                while (rst.next()){
                    row.add(new ModelTable(rst.getString("firstName"),
                            rst.getString("lastName"),
                            rst.getString("address"),
                            rst.getString("contact"),
                            rst.getString("email")));
                }

        }catch (ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null,"Driver Not Found");
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Duplicate Entry","",JOptionPane.WARNING_MESSAGE);
        }
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactTable.setItems(row);
    }

    public void clearTextFieldOnAction(MouseEvent mouseEvent) {
        txtSearch.setText(null);
        initialize();
        contactTable.setItems(null);
    }

    public void clearTextFieldOnKeyTyped(KeyEvent keyEvent) {
        contactTable.getItems().clear();
    }

    public void cancelOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void backOnAction(MouseEvent mouseEvent) {
        mainMenuStage.show();
        searchContactStage.close();
    }

}
