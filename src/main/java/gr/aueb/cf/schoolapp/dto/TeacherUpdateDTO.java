package gr.aueb.cf.schoolapp.dto;

public class TeacherUpdateDTO extends BaseTeacherDTO {
    private Integer id;

    public TeacherUpdateDTO() {
    }

    public TeacherUpdateDTO(String firstname, String lastname, String vat, String fatherName,
                            String phoneNum, String email, String street, String streetNum,
                            String zipCode, Integer cityId, Integer id) {
        super(firstname, lastname, vat, fatherName, phoneNum, email, street, streetNum, zipCode, cityId);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TeacherUpdateDTO{" +
                "id=" + id +
                '}';
    }
}
