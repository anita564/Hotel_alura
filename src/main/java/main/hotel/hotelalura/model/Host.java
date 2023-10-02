package main.hotel.hotelalura.model;


import main.hotel.hotelalura.utils.IHotelEntity;

public class Host implements IHotelEntity {
    private Integer id;
    private String name;
    private String lastName;
    private String birthDate;
    private String nationality;
    private String phone;
    private Integer resertavionId;

    public Host(String name, String lastName, String birthDay, String nationality, String number, Integer id_booking) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDay;
        this.nationality = nationality;
        this.phone = number;
        this.resertavionId = id_booking;
    }

    public Host(Integer id, String name, String lastName, String birthDay, String nationality, String number, Integer id_booking) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDay;
        this.nationality = nationality;
        this.phone = number;
        this.resertavionId = id_booking;
    }

    public Host(Integer id, String name, String lastName, String birthDay, String nationality, String number) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDay;
        this.nationality = nationality;
        this.phone = number;
    }

    @Override
    public String toString() {
        return "Huespede{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", nationality='" + nationality + '\'' +
                ", phone='" + phone + '\'' +
                ", resertavionId=" + resertavionId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName(){
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getRerservationId() {
        return resertavionId;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

	public Integer getResertavionId() {
		return resertavionId;
	}

	public void setResertavionId(Integer resertavionId) {
		this.resertavionId = resertavionId;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}

