package com.mustafa.grad.handywork.entity;

import com.mustafa.grad.handywork.entityvalidator.UniqueEmail;
import com.mustafa.grad.handywork.entityvalidator.UniquePhoneNumber;
import com.mustafa.grad.handywork.entityvalidator.UniqueUserName;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_name",
                        columnNames = "user_name"
                ),
                @UniqueConstraint(
                        name = "unique_email",
                        columnNames = "email"
                ),
                @UniqueConstraint(
                        name = "unique_phone_number",
                        columnNames = "phone_number"
                )
        }
)
public class User {

    // entity attributes and their annotations

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )

    @Column(name = "id", table = "users")
    private Long id;

    @Column(name = "user_name", table = "users", nullable = false, length = 36)
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 4, max = 36, message = "Must be between 4 and 36")
    @UniqueUserName
    private String userName;

    @Column(name = "password", table = "users", nullable = false)
    @NotEmpty(message = "Cannot be empty")
    @Size(min = 8, max = 36, message = "Must be between 8 and 36")
    private String password;

    @Column(name = "first_name", table = "users", length = 36)
    @Size(min = 3, max = 36, message = "Must be between 3 and 36")
    private String firstName;

    @Column(name = "last_name", table = "users", length = 36)
    @Size(min = 3, max = 36, message = "Must be between 3 and 36")
    private String lastName;

    @Column(name = "email", table = "users", nullable = false)
    @NotEmpty(message = "Cannot be empty")
    @Email(message = "Please enter a valid email")
    @UniqueEmail
    private String email;

    @Column(name = "phone_number", table = "users", length = 10)
    @Pattern(regexp = "^07[789][0-9]{7}$", message = "Please enter a valid phone number")
    @UniquePhoneNumber
    private String phoneNumber;

    @Column(name = "authority", table = "users", nullable = false, length = 50)
    private String authority;

    @Column(name = "profile_picture", table = "users")
    private String profilePicture;

    @Transient
    MultipartFile profilePicFile;

    @OneToMany(targetEntity = Job.class, mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;

    // empty constructor

    public User() {
    }

    // constructor with all attributes except id

    public User(
            String userName,
            String password,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String authority,
            List<Job> jobs,
            String profilePicture,
            MultipartFile profilePicFile
    ) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.authority = authority;
        this.jobs = jobs;
        this.profilePicture = profilePicture;
        this.profilePicFile = profilePicFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public MultipartFile getProfilePicFile() {
        return profilePicFile;
    }

    public void setProfilePicFile(MultipartFile profilePicFile) {
        this.profilePicFile = profilePicFile;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public int findJobIndex(Long id) {

        for (int i = 0; i < this.jobs.size(); i++) {
            if (Objects.equals(this.jobs.get(i).getId(), id)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (Job job: jobs) {
            str.append(job.getId()).append(", ");
        }

        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", authority='" + authority + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", jobs=" + str +
                '}';
    }
}
