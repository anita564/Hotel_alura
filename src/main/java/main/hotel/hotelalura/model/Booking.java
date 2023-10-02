package main.hotel.hotelalura.model;

import main.hotel.hotelalura.utils.IHotelEntity;

public class Booking implements IHotelEntity {
    private Integer id;
    private String checkInDate;
    private String checkOutDate;
    private Double priceValue;
    private String paymentMethod;


    public Booking(String checkInDate, String checkOutDate, Double priceValue, String paymentMethod) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.priceValue = priceValue;
        this.paymentMethod = paymentMethod;
    }

    public Booking(Integer id, String checkInDate, String checkOutDate, Double priceValue, String paymentMethod) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.priceValue = priceValue;
        this.paymentMethod = paymentMethod;
    }


    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", priceValue=" + priceValue +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Double getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(Double priceValue) {
		this.priceValue = priceValue;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}

