package praxsoft.SrvHTTP02.services;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class local001 {

    private int id;
    private String name;
    private long price;

    public local001(){
        // no argument constructor required by JAXB
    }

    public local001(int id, String name, long price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public long getPrice() {
        return price;
    }

}
