package main.hotel.hotelalura.viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.hotel.hotelalura.controller.ControllerHost;
import main.hotel.hotelalura.controller.ControllerBooking;
import main.hotel.hotelalura.model.Host;
import main.hotel.hotelalura.model.Booking;
import main.hotel.hotelalura.utils.IHotelEntity;
import main.hotel.hotelalura.utils.ScreenTransitionUtil;
import main.hotel.hotelalura.utils.TableDataType;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class InfoTableController implements Initializable {
    public Button btn_host;
    public Button btn_booking;

    public Button btn_back;
    public Button btn_edit;
    public Button btn_remove;

    public TableView<IHotelEntity> table;
    public TextField input_search;
    public Button btn_search;

    private ControllerHost huespedeController;
    private ControllerBooking reservaController;
    private TableDataType currentTableDataType = TableDataType.HOST;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupListeners();
    }

    private void setupListeners() {
        huespedeController = new ControllerHost();
        reservaController = new ControllerBooking();

        createColumns();
        listar();

        btn_host.setOnAction(event -> changeToHost());
        btn_booking.setOnAction(event -> changeToBooking());

        btn_remove.setOnAction(event -> delete());
        btn_edit.setOnAction(event -> edit());
        input_search.setOnKeyTyped(event -> search());
        btn_search.setOnAction(event -> search());

        btn_back.setOnAction(e -> ScreenTransitionUtil.changeScreen(this, "/main/hotel/hotelalura/menu-view.fxml", btn_back));
    }

    private void createColumns() {
        table.getColumns().clear();

        if (currentTableDataType == TableDataType.HOST) {
            TableColumn<IHotelEntity, Integer> idColumn;
            TableColumn<IHotelEntity, String> nombreColumn;
            TableColumn<IHotelEntity, String> apellidoColumn;
            TableColumn<IHotelEntity, String> fechaNacimientoColumn;
            TableColumn<IHotelEntity, String> nacionalidadColumn;
            TableColumn<IHotelEntity, String> telefonoColumn;
            TableColumn<IHotelEntity, Integer> idReservaColumn;

            idColumn = new TableColumn<>("ID");
            nombreColumn = new TableColumn<>("Nombre");
            apellidoColumn = new TableColumn<>("Apellido");
            fechaNacimientoColumn = new TableColumn<>("Fecha de Nacimiento");
            nacionalidadColumn = new TableColumn<>("Nacionalidad");
            telefonoColumn = new TableColumn<>("Telefono");
            idReservaColumn = new TableColumn<>("ID Reserva");

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            apellidoColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
            fechaNacimientoColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
            nacionalidadColumn.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
            telefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            idReservaColumn.setCellValueFactory(new PropertyValueFactory<>("id_reserva"));

            table.getColumns().add(idColumn);
            table.getColumns().add(nombreColumn);
            table.getColumns().add(apellidoColumn);
            table.getColumns().add(fechaNacimientoColumn);
            table.getColumns().add(nacionalidadColumn);
            table.getColumns().add(telefonoColumn);
            table.getColumns().add(idReservaColumn);

        } else if (currentTableDataType == TableDataType.RESERVA) {
            TableColumn<IHotelEntity, Integer> idColumn;
            TableColumn<IHotelEntity, String> fechaEntradaColumn;
            TableColumn<IHotelEntity, String> fechaSalidaColumn;
            TableColumn<IHotelEntity, Double> valorColumn;
            TableColumn<IHotelEntity, String> formaPagoColumn;

            idColumn = new TableColumn<>("ID");
            fechaEntradaColumn = new TableColumn<>("Fecha de Entrada");
            fechaSalidaColumn = new TableColumn<>("Fecha de Salida");
            valorColumn = new TableColumn<>("Valor");
            formaPagoColumn = new TableColumn<>("Forma de Pago");

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            fechaEntradaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_entrada"));
            fechaSalidaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_salida"));
            valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
            formaPagoColumn.setCellValueFactory(new PropertyValueFactory<>("forma_pago"));

            table.getColumns().add(idColumn);
            table.getColumns().add(fechaEntradaColumn);
            table.getColumns().add(fechaSalidaColumn);
            table.getColumns().add(valorColumn);
            table.getColumns().add(formaPagoColumn);
        }
    }

    private void listar() {
        ObservableList<IHotelEntity> huespedeList = FXCollections.observableArrayList();
        ObservableList<IHotelEntity> reservaList = FXCollections.observableArrayList();

        if (currentTableDataType == TableDataType.RESERVA) {
            List<Booking> reservas = reservaController.list();
            reservaList.addAll(reservas);
            table.setItems(reservaList);
        } else if (currentTableDataType == TableDataType.HOST) {
            List<Host> huespedes = huespedeController.list();
            huespedeList.addAll(huespedes);
            table.setItems(huespedeList);
        }
    }

    private void changeToBooking() {
        btn_booking.setStyle("-fx-background-color: #3D7A5D");
        btn_host.setStyle("-fx-background-color: #638A77");
        currentTableDataType = TableDataType.RESERVA;
        createColumns();
        listar();
    }

    private void changeToHost() {
        btn_host.setStyle("-fx-background-color: #3D7A5D");
        btn_booking.setStyle("-fx-background-color: #638A77");
        currentTableDataType = TableDataType.HOST;
        createColumns();
        listar();
    }

    private void delete() {
        if (currentTableDataType == TableDataType.HOST) {
            Host selectedHuespede = (Host) table.getSelectionModel().getSelectedItem();
            if (selectedHuespede != null) {
                Integer id = selectedHuespede.getId();
                if (windowConfirmation("Eliminar Huesped con id: " + id, "¿Estás seguro de que quieres eliminar este registro?")) {
                    huespedeController.delete(id);
                    listar();
                }
            }
        } else if (currentTableDataType == TableDataType.RESERVA) {
            Booking selectedReserva = (Booking) table.getSelectionModel().getSelectedItem();
            if (selectedReserva != null) {
                Integer id = selectedReserva.getId();
                if (reservaController.reservationHasHost(id)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error al eliminar reserva");
                    alert.setHeaderText("No se puede eliminar la reserva");
                    alert.setContentText("La reserva con id: " + id + " tiene un huespede asociado");

                    alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/main/hotel/hotelalura/delete.css")).toExternalForm());

                    alert.showAndWait();
                } else {
                    if (windowConfirmation("Eliminar Reserva con id: " + id, "¿Estás seguro de que quieres eliminar este registro?")) {
                        reservaController.delete(id);
                        listar();
                    }
                }
            }
        }
    }

    public boolean windowConfirmation(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminacion de registro");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);

        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/main/hotel/hotelalura/delete.css")).toExternalForm());

        ButtonType buttonTypeYes = new ButtonType("Sí");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == buttonTypeYes;
    }

    private void edit() {
    if (currentTableDataType == TableDataType.HOST) {
            Host selectedHuespede = (Host) table.getSelectionModel().getSelectedItem();
            if (selectedHuespede != null) {
                ScreenTransitionUtil.showScreenEditHost(this, "/main/hotel/hotelalura/editHost-view.fxml", selectedHuespede, btn_edit);
            }
        } else if (currentTableDataType == TableDataType.RESERVA) {
            Booking selectedReserva = (Booking) table.getSelectionModel().getSelectedItem();
            if (selectedReserva != null) {
                ScreenTransitionUtil.showScreenEditBooking(this, "/main/hotel/hotelalura/editBooking-view.fxml", selectedReserva, btn_edit);
            }
        }
    }

    private void search() {
        if (currentTableDataType == TableDataType.HOST) {
            String lastName = input_search.getText();
            if (lastName != null && !lastName.isEmpty()) {
                List<Host> huespedes = huespedeController.search(lastName.toLowerCase());
                ObservableList<IHotelEntity> huespedeList = FXCollections.observableArrayList();
                huespedeList.addAll(huespedes);
                table.setItems(huespedeList);
            } else {
                listar();
            }
        } else if (currentTableDataType == TableDataType.RESERVA) {
            String idBooking = input_search.getText();
            if (idBooking != null && !idBooking.isEmpty() && idBooking.matches("[0-9]+")){
                List<Booking> reservas = reservaController.search(idBooking);
                ObservableList<IHotelEntity> reservaList = FXCollections.observableArrayList();
                reservaList.addAll(reservas);
                table.setItems(reservaList);
            } else if (idBooking == null || idBooking.isEmpty()) {
                listar();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al buscar reserva");
                alert.setHeaderText("No se puede buscar la reserva");
                alert.setContentText("El id de la reserva debe ser un numero");

                alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/main/hotel/hotelalura/delete.css")).toExternalForm());

                alert.showAndWait();
                input_search.setText("");
                listar();
            }
        }
    }

}
