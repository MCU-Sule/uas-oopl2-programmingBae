package UASPBO2.controllers;
/**
 * @author - AbednegoSteven 1972009
 */

import UASPBO2.dao.MemberDaoImpl;
import UASPBO2.dao.PointDaoImpl;
import UASPBO2.dao.TransactionDaoImpl;
import UASPBO2.entities.FeMemberEntity;
import UASPBO2.entities.FePointEntity;
import UASPBO2.entities.FeTransactionEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController implements Initializable {
    @FXML
    public Label totalMember;
    @FXML
    public TextField txtId;
    @FXML
    public Label selectedTrans;
    @FXML
    public Label selectedMember;
    @FXML
    public TextField txtName;
    @FXML
    public TextArea txtAddress;
    @FXML
    public TextField txtPhone;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtUsername;
    @FXML
    public DatePicker birthDatePicker;
    @FXML
    public Button btnSave;
    @FXML
    public TextField txtNominal;
    @FXML
    public DatePicker transDatePicker;
    @FXML
    public Button btnReset;
    @FXML
    public Button btnUpdate;
    public TableView<FeTransactionEntity> tableTrans;
    public TableView<FeMemberEntity> tableMember;
    public TableColumn<FeMemberEntity,String> colCitizenId;
    public TableColumn<FeMemberEntity,String> colName;
    public TableColumn<FeMemberEntity,String> colPhone;
    public TableColumn<FeMemberEntity,String> colBirth;
    public TableColumn<FeTransactionEntity,String> colTransDate;
    public TableColumn<FeTransactionEntity,String> colNominal;
    public TableView<FePointEntity> tablePoint;
    public TableColumn<FePointEntity,String> colIdPoint;
    public TableColumn<FePointEntity,String>  colPoint;
    private LoginController loginController;
    private MemberDaoImpl memberDao = new MemberDaoImpl();
    private TransactionDaoImpl transactionDao = new TransactionDaoImpl();
    private PointDaoImpl pointDao = new PointDaoImpl();
    private ObservableList<FeMemberEntity> memberEntities;
    private ObservableList<FePointEntity> pointEntities;
    private ObservableList<FeTransactionEntity> transactionEntities;
    private FeMemberEntity memberEntity2;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    private Task<List<FeMemberEntity>> asyncMember (){
        Task<List<FeMemberEntity>> task = new Task<List<FeMemberEntity>>() {
            @Override
            protected List<FeMemberEntity> call() throws Exception {
                return memberDao.getAll();
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            memberEntities.clear();
            memberEntities.addAll(task.getValue());
        });
        return task;
    }

    private Task<List<FePointEntity>> asyncPoint (){
        Task<List<FePointEntity>> task = new Task<List<FePointEntity>>() {
            @Override
            protected List<FePointEntity> call() throws Exception {
                return pointDao.getAllPoint(memberEntity2);
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            pointEntities.clear();
            pointEntities.addAll(task.getValue());
        });
        return task;
    }

    private Task<List<FeTransactionEntity>> asyncTransaction (FeMemberEntity memberEntity){
        Task<List<FeTransactionEntity>> task = new Task<List<FeTransactionEntity>>() {
            @Override
            protected List<FeTransactionEntity> call() throws Exception {
                return transactionDao.getAllTransByMember(memberEntity);
            }
        };
        task.setOnSucceeded(workerStateEvent -> {
            transactionEntities.clear();
            transactionEntities.addAll(task.getValue());
        });
        return task;
    }

    private void fetchData(){
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(asyncMember());
        service.execute(asyncPoint());
        service.shutdown();
        totalMember.setText(String.valueOf(memberEntities.size()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(false);
        memberEntities = FXCollections.observableArrayList() ;
        transactionEntities = FXCollections.observableArrayList() ;
        pointEntities =   FXCollections.observableArrayList() ;
        fetchData();
        tableMember.setItems(memberEntities);
        colCitizenId.setCellValueFactory(data ->new SimpleStringProperty(data.getValue().getCitizenId()));
        colName.setCellValueFactory(data-> new SimpleStringProperty(data.getValue().getName()));
        colPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));
        colBirth.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBirthdate().toString()));
        //
        tableTrans.setItems(transactionEntities);
        colTransDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTransDate().toString()));
        colNominal.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNominal())));
        //
        tablePoint.setItems(pointEntities);
        colPoint.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getValue())));
        colIdPoint.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));


    }

    public void saveMemberAction(ActionEvent actionEvent) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() ||
        txtPhone.getText().isEmpty() || txtEmail.getText().isEmpty() || txtUsername.getText().isEmpty() ||
        birthDatePicker.getValue() == null) {
            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
            alertInformation.setContentText("Please fill in all the field");
            alertInformation.show();
        } else {
            FeMemberEntity memberEntity = new FeMemberEntity();
            memberEntity.setCitizenId(String.valueOf(Integer.parseInt(txtId.getText())));
            memberEntity.setName(txtName.getText());
            memberEntity.setAddress(txtAddress.getText());
            memberEntity.setPhone(txtPhone.getText());
            memberEntity.setEmail(txtEmail.getText());
            memberEntity.setUsername(txtUsername.getText());
            memberEntity.setBirthdate(Date.valueOf(birthDatePicker.getValue()));
            memberDao.addData(memberEntity);
            fetchData();
            reset();
        }
    }

    public void resetAction(ActionEvent actionEvent) {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtUsername.setText("");
        birthDatePicker.setValue(null);
    }

    public void reset (){
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtUsername.setText("");
        birthDatePicker.setValue(null);
        txtNominal.setText("");
        transDatePicker.setValue(null);
    }

    public void updateAction(ActionEvent actionEvent) {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() ||
                txtPhone.getText().isEmpty() || txtEmail.getText().isEmpty() || txtUsername.getText().isEmpty() ||
                birthDatePicker.getValue() == null) {
            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
            alertInformation.setContentText("Please fill in all the field");
            alertInformation.show();
        } else {
            try {
                FeMemberEntity memberEntity = memberEntity2;
                memberEntity.setCitizenId(String.valueOf(Integer.parseInt(txtId.getText())));
                memberEntity.setName(txtName.getText());
                memberEntity.setAddress(txtAddress.getText());
                memberEntity.setPhone(txtPhone.getText());
                memberEntity.setEmail(txtEmail.getText());
                memberEntity.setUsername(txtUsername.getText());
                memberEntity.setBirthdate(Date.valueOf(birthDatePicker.getValue()));
                memberDao.updateData(memberEntity);
                fetchData();
                reset();
                btnSave.setDisable(false);
                btnUpdate.setDisable(true);
                txtId.setDisable(false);
            } catch (NumberFormatException ex){
                System.out.println(ex.getMessage());
            }

        }

    }

    public void saveTransAction(ActionEvent actionEvent) {
        if (txtNominal.getText().isEmpty() || transDatePicker.getValue() == null){
            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
            alertInformation.setContentText("Please fill in all the field");
            alertInformation.show();
        } else {
            FeTransactionEntity transactionEntity = new FeTransactionEntity();
            transactionEntity.setNominal(Long.parseLong(txtNominal.getText()));
            transactionEntity.setTransDate(Date.valueOf(transDatePicker.getValue()));
            transactionEntity.setFeMemberByMemberCitizenId(memberEntity2);
            transactionDao.addData(transactionEntity);
            FePointEntity pointEntity = new FePointEntity();
            pointEntity.setFeMemberByMemberCitizenId(memberEntity2);
            pointEntity.setValue(Math.toIntExact(Long.valueOf(String.valueOf(txtNominal)) / 100000));
            pointDao.addData(pointEntity);

            ExecutorService service = Executors.newCachedThreadPool();
            service.execute(asyncTransaction(memberEntity2));
            service.execute(asyncPoint());
            service.shutdown();
            reset();
        }
    }

    public void selectedItem(MouseEvent mouseEvent) {
        if (!tableMember.getSelectionModel().getSelectedCells().isEmpty()) {
            btnUpdate.setDisable(false);
            txtId.setDisable(true);
            btnSave.setDisable(true);
            memberEntity2 = tableMember.getSelectionModel().getSelectedItem();
            FeMemberEntity memberEntity = memberEntity2;
            txtId.setText(String.valueOf(memberEntity2.getCitizenId()));
            txtName.setText(memberEntity2.getName());
            txtAddress.setText(memberEntity2.getAddress());
            txtPhone.setText(memberEntity2.getPhone());
            txtEmail.setText(memberEntity2.getEmail());
            txtUsername.setText(memberEntity2.getUsername());
            birthDatePicker.setValue(memberEntity2.getBirthdate().toLocalDate());
            transactionEntities.clear();
            transactionEntities.addAll(transactionDao.getAllTransByMember(memberEntity));
            ExecutorService service = Executors.newCachedThreadPool();
            service.execute(asyncTransaction(memberEntity));
            pointEntities.clear();
            pointEntities.addAll(pointDao.getAllPoint(memberEntity));
            service.execute(asyncPoint());



        }



    }
}
