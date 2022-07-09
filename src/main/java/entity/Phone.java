package entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQuery;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(schema = "phonebook_schema", name = "phones")
@Cacheable
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "getAllPhones", query = "from Phone", fetchSize = 10)
@NamedQuery(name = "getPhoneById", query = "from Phone where id = :phoneId")
@NamedNativeQuery(name = "getAllPhonesNative", query = "select * from phonebook_schema.phones", resultClass = Phone.class)
@NamedNativeQuery(name = "getPhoneByIdNative", query = "select * from phonebook_schema.phones p where id = :phoneId", resultClass = Phone.class)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "funds")
    private double funds;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "activation_date")
    private LocalDateTime activationDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "person_id")
    private int personId;

    @Column(name = "country_code_id")
    private int countryCodeId;

    public Phone() {
    }

    public Phone(String phoneNumber, String operatorName, double funds, int personId, int countryCodeId) {
        this.operatorName = operatorName;
        this.funds = funds;
        this.phoneNumber = phoneNumber;
        this.personId = personId;
        this.countryCodeId = countryCodeId;
        this.registrationDate = LocalDateTime.now();
        this.activationDate = LocalDateTime.now();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCountryCode() {
        return countryCodeId;
    }

    public void setCountryCode(int countryCodeId) {
        this.countryCodeId = countryCodeId;
    }

    public int getPerson() {
        return personId;
    }

    public void setPerson(int personId) {
        this.personId = personId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return id == phone.id && Double.compare(phone.funds, funds) == 0 && Objects.equals(countryCodeId, phone.countryCodeId) && Objects.equals(phoneNumber, phone.phoneNumber) && Objects.equals(operatorName, phone.operatorName) && Objects.equals(registrationDate, phone.registrationDate) && Objects.equals(activationDate, phone.activationDate) && Objects.equals(personId, phone.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryCodeId, phoneNumber, operatorName, funds, registrationDate, activationDate, personId);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", areaCode='" + countryCodeId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
