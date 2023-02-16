package com.mustafa.grad.handywork.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @SequenceGenerator(
            name = "job_sequence",
            sequenceName = "job_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "job_sequence"
    )
    @Column(name = "id", table = "jobs")
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "job_user_foreign_key"), name = "user_id")
    private User owner;

    @Column(name = "field", table = "jobs", nullable = false, length = 50)
    @NotEmpty(message = "cannot be empty")
    private String field;

    @Column(name = "description", table = "jobs", nullable = false, columnDefinition = "TEXT")
    @NotEmpty(message = "cannot be empty")
    private String description;

    @Column(name = "price", table = "jobs")
    @Min(value = 0, message = "cannot be less than 0 ")
    @Max(value = 999999, message = "cannot be higher than 999999")
    @Digits(integer = 6, fraction = 2, message = "max number digits is 6, fraction is 2")
    private int price;

    @Column(name = "publish_date", table = "jobs", nullable = false, columnDefinition = "DATETIME")
    @Past
    private Date publishDate;

    @Column(name = "job_name", table = "jobs", nullable = false, length = 50)
    @NotEmpty(message = "cannot be empty")
    private String jobName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "image_url" , foreignKey = @ForeignKey(name = "job_image_url_foreign_key"))
    @Column(name = "images_urls", table = "jobs")
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "job_id")
    private List<String> imagesUrls;

    @Transient
    private List<MultipartFile> images;


    public Job() {
    }

    public Job(User owner, String field, String description, int price, Date publishDate, List<String> imagesUrls, List<MultipartFile> images) {
        this.owner = owner;
        this.field = field;
        this.description = description;
        this.price = price;
        this.publishDate = publishDate;
        this.imagesUrls = imagesUrls;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public void setUrlsFirstIndex() {
        if (imagesUrls == null || imagesUrls.size() == 0){
            imagesUrls = new ArrayList<>();
            imagesUrls.add("https://res.cloudinary.com/dsgouaw4m/image/upload/v1672680426/pngtree-abstract-studio-background-gradient-silver-gray-wall-in-empty-room-template-image_388912_o36vxb.jpg");
        }
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", owner=" + owner.getId() +
                ", field='" + field + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", publishDate=" + publishDate +
                ", jobName='" + jobName + '\'' +
                ", imagesUrls=" + imagesUrls +
                '}';
    }
}
