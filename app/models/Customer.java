package models;

/**
 * Presentation object used for displaying data in a template.
 *
 * Note that it's a good practice to keep the presentation DTO,
 * which are used for reads, distinct from the form processing DTO,
 * which are used for writes.
 */
public class Customer {
    public long id;
    public String name;
    public String duetime;
    public String jointime;

    public Customer(long id, String name, String duetime, String jointime) {
        this.id = id;
        this.name = name;
        this.duetime = duetime;
        this.jointime = jointime;
    }


}
